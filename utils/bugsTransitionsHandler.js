const fs = require('fs');
const { exit } = require('process');
let fetch, FormData;
let apiURL, auth, projectKey;
let testExecutionDoneID, testCaseDoneID, bugDoneID;
let testExecutionDoingID, testCaseDoingID, bugDoingID;

const browserURL = "https://pichincha.atlassian.net/browse/"
const testPlanSummaryPrefix = "TP-Pruebas Funcionales Automatizadas"

const headers = () => {
    return {
        'Authorization': `Basic ${auth}`,
        'Accept': 'application/json',
        'Content-Type': 'application/json'
    }
};

const issueFormat = (projectKey, assigneeId, sprintId, issuetype = "Bug de Desarrollo") => {
    const formatData = {
        "update": {},
        "fields": {
            "summary": "",
            "issuetype": {
                "name": issuetype
            },
            "project": {
                "key": projectKey
            },
            "assignee": {
                "id": assigneeId
            },
            "description": {
                "type": "doc",
                "version": 1,
                "content": [
                    {
                        "type": "paragraph",
                        "content": [
                            {
                                "text": "",
                                "type": "text"
                            }
                        ]
                    }
                ]
            },
            "customfield_10021": sprintId
        }
    };
    if (issuetype == "Bug de Desarrollo") {
        formatData.fields.priority = {
            "name": "High"
        }
    }
    return formatData
};

const issueUpdateFormat = (newAssigneeId, description) => {
    return {
        "fields": {
            "assignee": {
                "id": newAssigneeId
            },
            "description": {
                "type": "doc",
                "version": 1,
                "content": [
                    {
                        "type": "paragraph",
                        "content": [
                            {
                                "text": description,
                                "type": "text"
                            }
                        ]
                    }
                ]
            }
        }
    }
};

const issueLinkDataFormat = {
    "outwardIssue": {
        "key": ""
    },
    "inwardIssue": {
        "key": ""
    },
    "type": {
        "name": "Defect"
    }
};

const bodyComment = () => {
    return {
        "body": {
            "type": "doc",
            "version": 1,
            "content": [
                {
                    "type": "paragraph",
                    "content": [
                        {
                            "text": "",
                            "type": "text"
                        }
                    ]
                }
            ]
        }
    }
};

const getRequestParameters = (method, bodyData = null, customHeaders = null, dataIsJSON = true) => {
    let basicHeaders = headers()
    if (customHeaders)
        basicHeaders = customHeaders
    const parameters = {
        method,
        headers: basicHeaders
    }
    if (method.toLowerCase() != "get") {
        if (dataIsJSON) {
            parameters.body = JSON.stringify(bodyData)
        } else {
            parameters.body = bodyData
        }
    }
    return parameters
}

const request = async function (url, method, bodyData = null, customHeaders = null, dataIsJSON = true) {
    const requestParameters = getRequestParameters(method, bodyData, customHeaders, dataIsJSON)
    return await fetch(url, requestParameters)
        .then(response => {
            return response.text();
        })
        .then(text => {
            return text
        })
        .catch(err => console.error(err))
}

const getCurrentYear = () =>{
    const today = new Date();
    return today.getFullYear();
}

/*const getIssueByKey = async function (issueIdOrKey) {
    const url = `${apiURL}/api/3/issue/${issueIdOrKey}`
    const response = await request(url, "GET")
    return response
}*/

const updateIssueByKey = async function (issueIdOrKey, newAssigneeId, description) {
    const url = `${apiURL}/api/3/issue/${issueIdOrKey}`
    const updateData = issueUpdateFormat(newAssigneeId, description)
    await request(url, "PUT", updateData)
}

const getValidTransitionData = (typeTransition, typeIssue) => {
    let validTransitionId;
    if (typeIssue.toLowerCase() == "testcase") {
        validTransitionId = typeTransition.toLowerCase() == "done" ? testCaseDoneID : testCaseDoingID
    } else if (typeIssue.toLowerCase() == "testexecution") {
        validTransitionId = typeTransition.toLowerCase() == "done" ? testExecutionDoneID : testExecutionDoingID
    } else if (typeIssue.toLowerCase() == "bug") {
        validTransitionId = typeTransition.toLowerCase() == "done" ? bugDoneID : bugDoingID
    } else {
        validTransitionId = typeTransition.toLowerCase() == "done" ? testCaseDoneID : testCaseDoingID
    }
    return {
        transition: {
            id: validTransitionId
        }
    }
}

const makeTransition = async function (issueIdOrKey, typeTransition, typeIssue) {
    const url = `${apiURL}/api/3/issue/${issueIdOrKey}/transitions`
    const bodyData = getValidTransitionData(typeTransition, typeIssue);
    await request(url, "POST", bodyData)
};

const closePreviousTestExecutions = async function () {
    const url = `${apiURL}/api/3/search?jql=project="${projectKey}"%20AND%20type="Test Execution"%20AND%20status="PRUEBAS DOING"%20AND%20summary~"TE-E2E"&fields=summary`
    const response = await request(url, "GET")
    const issues = JSON.parse(response).issues
    for (const issue of issues) {
        await makeTransition(issue.key, "DONE", "TESTEXECUTION")
    }
}

const createIssueLink = async function (issueLinkData) {
    const url = `${apiURL}/api/3/issueLink`
    await request(url, "POST", issueLinkData)
}

const addAttachmentToIssue = async function (issueIdOrKey, attachmentPath) {
    if (attachmentPath) {
        const form = new FormData();
        const stats = fs.statSync(attachmentPath);
        const fileSizeInBytes = stats.size;
        const fileStream = fs.createReadStream(attachmentPath);

        form.append('file', fileStream, { knownLength: fileSizeInBytes });

        const url = `${apiURL}/api/3/issue/${issueIdOrKey}/attachments`

        const customHeaders = {
            'Authorization': `Basic ${auth}`,
            'X-Atlassian-Token': 'no-check'
        }
        await request(url, "POST", bodyData = form, customHeaders, dataIsJSON = false)
    }
}

const makeComment = async function (issueIdOrKey, message) {
    const url = `${apiURL}/api/3/issue/${issueIdOrKey}/comment`
    const commentData = bodyComment()
    commentData.body.content[0].content[0].text = message
    await request(url, "POST", commentData)
}

const createBugIssue = async function (bodyData, parentsKeys, message, attachmentPath) {
    const url = `${apiURL}/api/3/issue`
    const response = await request(url, "POST", bodyData)
    const bugKey = JSON.parse(response).key
    for (parentKey of Object.values(parentsKeys)) {
        const issueLinkData = JSON.parse(JSON.stringify(issueLinkDataFormat));
        issueLinkData.outwardIssue.key = bugKey
        issueLinkData.inwardIssue.key = parentKey
        await createIssueLink(issueLinkData)
    }
    await addAttachmentToIssue(bugKey, attachmentPath)
    await makeComment(bugKey, message)
    await makeTransition(bugKey, "PRUEBAS DOING", "BUG")
    await makeTransition(parentsKeys.testCaseKey, "PRUEBAS DOING", "TESTCASE")
}

const closePreviousBugs = async function (testCaseKey) {
    const url = `${apiURL}/api/3/issue/${testCaseKey}?fields=issuelinks`
    const response = await request(url, "GET")
    const issueLinks = JSON.parse(response).fields.issuelinks
    for (bugLink of issueLinks) {
        if (bugLink.type.name == "Defect") {
            await makeTransition(bugLink.outwardIssue.key, "DONE", "BUG")
        }
    }
}

const getJiraAttachmentInIssue = async function (issueIdOrKey) {
    const url = `${apiURL}/api/3/issue/${issueIdOrKey}?fields=attachment`
    const response = await request(url, "GET")
    return JSON.parse(response)
}

const deleteAttachment = async function (attachmentId) {
    const url = `${apiURL}/api/3/attachment/${attachmentId}`
    await request(url, "DELETE")
}

const deletePreviousAttachmentsIssue = async function (issueIdOrKey) {
    const attachmentsIssues = await getJiraAttachmentInIssue(issueIdOrKey);
    for (const attachment of attachmentsIssues.fields.attachment) {
        await deleteAttachment(attachment.id)
    }
}

const getPreviousBugs = async function (sprintIds, testCaseKey) {
    const url = `${apiURL}/api/3/search?jql=project="${projectKey}"%20AND%20issueCreatedBy=${testCaseKey}%20AND%20issuetype="Bug de Desarrollo"%20AND%20Sprint%20in%20(${sprintIds.join(",")})&fields=summary,customfield_10021,resolution`
    const response = await request(url, "GET")
    return JSON.parse(response).issues
}

const getLastSprints = async function (boardId, startAt = 0, maxResults = 50) {
    const url = `${apiURL}/agile/1.0/board/${boardId}/sprint?state=active,closed&maxResults=${maxResults}&startAt=${startAt}`
    let response = await request(url, "GET")
    response = JSON.parse(response)
    if (response.isLast) {
        const originBoardValues = response.values.filter((sprint) => sprint.originBoardId == boardId)
        let sliceLength = 3
        if (originBoardValues.length < 3)
            sliceLength = originBoardValues.length
        return originBoardValues.slice(originBoardValues.length - sliceLength)
    }
    startAt = startAt + 47
    return await getLastsSprints(boardId, startAt, maxResults)
}

const getActiveSprint = async function (boardId) {
    const url = `${apiURL}/agile/1.0/board/${boardId}/sprint?state=active`
    const response = await request(url, "GET")
    return JSON.parse(response)
}

const generateScreenshot = (attachment) => {
    const attachmentFolder = `${__dirname}/attachment`
    if (!fs.existsSync(attachmentFolder)) {
        fs.mkdirSync(attachmentFolder, true)
    }
    const directory = fs.readdirSync(attachmentFolder)
    const imageName = `imagen-${directory.length + 1}.${attachment.mime_type.split("/").pop()}`
    const imagenData = attachment.data;
    const attachmentPath = attachmentFolder + "/" + imageName
    const data = imagenData.replace(/^data:image\/\w+;base64,/, "");
    const buffer = Buffer.from(data, 'base64');
    fs.writeFileSync(attachmentPath, buffer, 'utf-8');
    return attachmentPath
}

const closeTestExecutions = async function (currentTEKey) {
    await closePreviousTestExecutions()
    await makeTransition(currentTEKey, "DONE", "TESTEXECUTION")
}

const validateArguments = (arguments, mandatoryArgs) => {
    Object.keys(arguments).forEach((argument) => {
        const indexArg = mandatoryArgs.indexOf(argument);
        if (indexArg !== -1) {
            mandatoryArgs.splice(indexArg, 1);
        } else {
            console.error(`Invalid argument: '${argument}' in the command.`);
            exit(0)
        }
    })
    if (mandatoryArgs.length != 0) {
        console.error(`Sorry, some arguments are missing: '${mandatoryArgs}' in the command.`);
        exit(0)
    }
}

const reOpenIssue = async function (issueIdOrKey, attachmentPath, assigneeId, descriptionMessage, comment) {
    await deletePreviousAttachmentsIssue(issueIdOrKey)
    await addAttachmentToIssue(issueIdOrKey, attachmentPath)
    await updateIssueByKey(issueIdOrKey, assigneeId, descriptionMessage)
    await makeComment(issueIdOrKey, comment)
}

//Make a single method
const createIssue = async function (bodyData) {
    const url = `${apiURL}/api/3/issue`
    const response = await request(url, "POST", bodyData)
    return JSON.parse(response)
}

const testPlanBySprint = async function (sprintId) {
    const url = `${apiURL}/api/3/search?jql=project="${projectKey}"%20AND%20issuetype="Test Plan"%20AND%20Sprint=${sprintId}%20AND%20summary~"${testPlanSummaryPrefix}*"&fields=summary`
    const response = await request(url, "GET")
    return JSON.parse(response)
}

const testPlanQuery = (issuesIds) => {
    return {
        query: `{
            getTests(jql: "project = '${projectKey}'", issueIds:[${issuesIds.map(x => "\"" + x + "\"")}], limit: 100) {
                total
                start
                limit
                results {
                    issueId
                    testPlans(limit: 10) {
                        total
                        start
                        limit
                        results {
                            issueId
                            jira(fields: ["summary", "key"])
                        }
                    }
                    jira(fields: ["summary"])
                }
            }
        }`,
        variables: {}
    }
}

const testToTestPlanQuery = (testPlanId, testCasesIds) => {
    return {
        query: `
            mutation {
                addTestsToTestPlan(
                    issueId: "${testPlanId}",
                    testIssueIds: [${testCasesIds.map(x => "\"" + x + "\"")}]
                ) {
                    addedTests
                    warning
                }
            }`,
        variables: {}
    }
}

const testExecutionsToPlanQuery = (testPlanId, testExecutionsIds) => {
    return {
        query: `
            mutation {
                addTestExecutionsToTestPlan(
                issueId: "${testPlanId}",
                testExecIssueIds: [${testExecutionsIds.map(x => "\"" + x + "\"")}]) {
                    addedTestExecutions
                    warning
                }
            }`,
        variables: {}
    }
}

const graphQLRequest = async function (token, testPlanQuery) {
    const url = `https://xray.cloud.getxray.app/api/v1/graphql`
    const response = await fetch(url, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`
        },
        body: JSON.stringify(testPlanQuery),
    })
        .then(response => {
            return response.text();
        })
        .then(text => {
            return text
        })
        .catch(err => console.error(err))
    return JSON.parse(response)
}

const getSprintInfoByID = async function(sprintId) {
    const url = `${apiURL}/agile/1.0/sprint/${sprintId}`
    const response = await request(url, "GET")
    return JSON.parse(response)
}

const getTestPlanId = async function (sprintId) {
    let testPlanId;
    const sprintInfo = await getSprintInfoByID(sprintId)
    const testPlanInSprint = await testPlanBySprint(sprintId)
    if (testPlanInSprint.issues.length == 0) {
        const issueData = JSON.parse(JSON.stringify(issueFormat(projectKey, assigneeId, sprintId, "Test Plan")));
        issueData.fields.summary = `${testPlanSummaryPrefix}-${getCurrentYear()}-${sprintInfo.name}`
        const testPlanResponse = await createIssue(issueData)
        testPlanId = testPlanResponse.id
    } else {
        testPlanId = testPlanInSprint.issues[0].id
    }
    return testPlanId
}
//

module.exports.processJiraScenarios = function (arguments) {
    let reportPath;
    const mandatoryArgs = ['reportPath']
    validateArguments(arguments, mandatoryArgs)
    reportPath = arguments.reportPath;

    if (!fs.existsSync(reportPath)) {
        console.error(`File not found in the following path: '${reportPath}'`)
        exit(0)
    }

    const failedScenarios = {}
    const passedScenarios = {
        scenarios: {}
    }
    const readData = fs.readFileSync(reportPath, { encoding: 'utf8', flag: 'r' });
    const cucumberReport = JSON.parse(readData)
    for (const feature of cucumberReport) {
        const scenariosStatus = {};
        const filterScenarios = feature.elements.filter((element)=> element.type == "scenario")
        for (const scenario of filterScenarios) {
            let testCaseKey;
            if (scenariosStatus[scenario.name] == undefined) {
                scenariosStatus[scenario.name] = true;
            }
            for (const step of scenario.steps) {
                for (const tag of scenario.tags) {
                    if (tag.name.includes("TEST"))
                        testCaseKey = tag.name.split("TEST_").pop()
                }
                if (!passedScenarios.scenarios[scenario.name]) {
                    passedScenarios.scenarios[scenario.name] = {
                        testCaseKey: testCaseKey,
                        passedSteps: []
                    }
                }
                if (!failedScenarios[scenario.name]) {
                    failedScenarios[scenario.name] = {}
                }
                if (step.result.status == "failed") {
                    scenariosStatus[scenario.name] = false;
                    const parents = {}
                    for (const tag of scenario.tags) {
                        if (tag.name.includes("REQ"))
                            parents.userHistoryKey = tag.name.split("REQ_").pop()
                        if (tag.name.includes("TEST"))
                            parents.testCaseKey = tag.name.split("TEST_").pop()
                    }

                    step.keyword = step.keyword.trim()
                    failedScenarios[scenario.name].failedStep = `${step.keyword} ${step.name}`
                    failedScenarios[scenario.name].errorMessage = step.result.error_message
                    failedScenarios[scenario.name].parentLinks = parents
                    if (step.embeddings) {
                        failedScenarios[scenario.name].embeddings = step.embeddings[0]
                    } else if (step.after) {
                        if (step.after[0].embeddings) {
                            failedScenarios[scenario.name].embeddings = step.after[0].embeddings[0]
                        }
                    }
                }
                else if (step.result.status == "passed") {
                    step.keyword = step.keyword.trim()
                    passedScenarios.scenarios[scenario.name].passedSteps.push(`${step.keyword} ${step.name}`)
                }
            }
            if (!failedScenarios[scenario.name].failedStep) {
                delete failedScenarios[scenario.name]
            }
        }
        for (const [scenarioName, scenarioStatus] of Object.entries(scenariosStatus)) {
            passedScenarios.scenarios[scenarioName].isComplete = scenarioStatus;
            if (scenarioStatus) {
                delete passedScenarios.scenarios[scenarioName].passedSteps
            }
        }
    }

    if (Object.keys(failedScenarios).length == 0) {
        passedScenarios.currentTEStatus = 'passed'
    } else {
        passedScenarios.currentTEStatus = 'failed'
    }
    const scenariosReportFolder = `${__dirname}/reports`
    if (!fs.existsSync(scenariosReportFolder)) {
        fs.mkdirSync(scenariosReportFolder, true)
    }
    fs.writeFileSync(scenariosReportFolder + "/passedScenarios.json", JSON.stringify(passedScenarios), 'utf-8');
    fs.writeFileSync(scenariosReportFolder + "/failedScenarios.json", JSON.stringify(failedScenarios), 'utf-8');
};


module.exports.closePassedScenarios = async function (arguments) {
    fetch = require('node-fetch');
    let passedScenariosPath, currentTEKey, boardId;
    const mandatoryArgs = ['apiURL', 'auth', 'passedScenariosPath', 'projectKey', 'currentTEKey', 'testCaseDoneID', 'bugDoneID', 'testExecutionDoneID', 'boardId']
    validateArguments(arguments, mandatoryArgs)

    apiURL = arguments.apiURL
    projectKey = arguments.projectKey
    auth = Buffer.from(arguments.auth).toString('base64');

    boardId = arguments.boardId
    currentTEKey = arguments.currentTEKey
    passedScenariosPath = arguments.passedScenariosPath;

    testCaseDoneID = arguments.testCaseDoneID;
    bugDoneID = arguments.bugDoneID;
    testExecutionDoneID = arguments.testExecutionDoneID;

    if (!fs.existsSync(passedScenariosPath)) {
        console.error(`File not found in the following path: '${passedScenariosPath}'`)
        exit(0)
    }
    const activeSprintResponse = await getActiveSprint(boardId)
    const activeSprintID = [activeSprintResponse.values[0].id]
    const passedScenarios = JSON.parse(fs.readFileSync(passedScenariosPath, { encoding: 'utf8', flag: 'r' }))
    for (const [scenarioName, testCaseResult] of Object.entries(passedScenarios.scenarios)) {
        if (testCaseResult.isComplete) {
            await closePreviousBugs(testCaseResult.testCaseKey)
            await makeTransition(testCaseResult.testCaseKey, "DONE", "TESTCASE")
        } else {
            
            const previousBugsResponse = await getPreviousBugs(activeSprintID, testCaseResult.testCaseKey)
            const previousBugsFields = previousBugsResponse.map(({ key, fields }) => ({ key, fields }))
            for (const passedStep of testCaseResult.passedSteps) {
                const bugComparator = previousBugsFields.filter((bug) => bug.fields.summary == `BUG-${scenarioName} - ${passedStep}`)
                if (bugComparator.length != 0) {
                    await makeTransition(bugComparator.pop().key, "DONE", "BUG")
                }
            }
        }
    }

    if (passedScenarios.currentTEStatus != "failed") {
        await closeTestExecutions(currentTEKey)
        const testPlanId = await getTestPlanId(activeSprintID)
        makeTransition(testPlanId, "DONE" , "TESTPLAN")
    }
};


module.exports.createBugsInJira = async function (arguments) {
    fetch = require('node-fetch');
    FormData = require('form-data');
    let failedScenariosPath, assigneeId, boardId;
    const mandatoryArgs = ['apiURL', 'auth', 'failedScenariosPath', 'projectKey', 'assigneeId', 'boardId', 'testCaseDoingID', 'bugDoingID']
    validateArguments(arguments, mandatoryArgs)
    apiURL = arguments.apiURL
    auth = Buffer.from(arguments.auth).toString('base64');
    projectKey = arguments.projectKey

    failedScenariosPath = arguments.failedScenariosPath;
    assigneeId = arguments.assigneeId
    boardId = arguments.boardId

    bugDoingID = arguments.bugDoingID
    testCaseDoingID = arguments.testCaseDoingID

    if (!fs.existsSync(failedScenariosPath)) {
        console.error(`File not found in the following path: '${failedScenariosPath}'`)
        exit(0)
    }

    const failedScenarios = JSON.parse(fs.readFileSync(failedScenariosPath, { encoding: 'utf8', flag: 'r' }))
    const sprintsResponse = await getLastSprints(boardId)

    const sprintIds = sprintsResponse.map((value) => value.id)
    const actualSprint = sprintsResponse.filter((sprint) => sprint.state == "active").pop()

    for (const [key, value] of Object.entries(failedScenarios)) {
        let isOldBug = false;
        let isInActualSprint = false;
        let issueInPreviousSprint, issueInActualSprint;
        let attachmentPath;
        const previousBugsByTestcaseId = await getPreviousBugs(sprintIds, failedScenarios[key].parentLinks.testCaseKey)
        const summary = `BUG-${key} - ${value.failedStep}`
        if (value.embeddings) {
            attachmentPath = generateScreenshot(value.embeddings);
        }
        let oldSprint;
        for (const issue of previousBugsByTestcaseId) {
            if (issue.fields.summary == summary) {
                isOldBug = true
                if (issue.fields.customfield_10021[0].id == actualSprint.id) {
                    isInActualSprint = true
                    issueInActualSprint = issue
                } else {
                    oldSprint = sprintsResponse.filter((sprint) => sprint.id == issue.fields.customfield_10021[0].id).pop()
                }
                issueInPreviousSprint = issue
            }
        }
        const bugData = JSON.parse(JSON.stringify(issueFormat(projectKey, assigneeId, actualSprint.id)));
        bugData.fields.summary = summary
        bugData.fields.description.content[0].content[0].text = failedScenarios[key].errorMessage
        if (!isOldBug) {
            const message = `Se creo un nuevo bug, con descripción:\n${failedScenarios[key].errorMessage}`
            await createBugIssue(bugData, failedScenarios[key].parentLinks, message, attachmentPath)
        }
        else if (isInActualSprint) {
            let message = `Se mantiene el bug ${issueInActualSprint.key}, debido a una falla con descripción:\n${failedScenarios[key].errorMessage}`
            if (issueInActualSprint.fields.resolution) {
                message = `Se reabrió el bug ${issueInActualSprint.key}, debido a una falla con descripción:\n${failedScenarios[key].errorMessage}`
                await makeTransition(issueInActualSprint.key, "PRUEBAS DOING", "BUG")
            }
            await reOpenIssue(issueInActualSprint.key, attachmentPath, assigneeId, failedScenarios[key].errorMessage, message)
        } else {
            const message = `Este bug existió en el sprint: '${oldSprint.name}', bug: (${issueInPreviousSprint.key + ": " + browserURL + issueInPreviousSprint.key} ). \nSe creó un nuevo bug con descripción:\n${failedScenarios[key].errorMessage}`;
            await createBugIssue(bugData, failedScenarios[key].parentLinks, message, attachmentPath)
        }
    }
    if (Object.keys(failedScenarios).length != 0) {
        const testPlanId = await getTestPlanId(actualSprint.id)
        makeTransition(testPlanId, "PRUEBAS DOING" , "TESTPLAN")
    }
};

module.exports.testCasesToPlan = async function (arguments) {
    fetch = require('node-fetch');
    const mandatoryArgs = ['apiURL', 'auth', 'projectKey', 'assigneeId', 'sprintId', "testCasesIds", "token"]
    validateArguments(arguments, mandatoryArgs);
    ({ apiURL, projectKey, assigneeId, sprintId, testCasesIds, token } = arguments);
    auth = Buffer.from(arguments.auth).toString('base64');
    const testPlanId = await getTestPlanId(sprintId);
    const testsGraphQLResponse = await graphQLRequest(token, testPlanQuery(testCasesIds))
    for (const testCase of testsGraphQLResponse.data.getTests.results) {
        if (testCase.testPlans.results.length == 0) {
            await graphQLRequest(token, testToTestPlanQuery(testPlanId, [testCase.issueId]))
        }
    }
};

module.exports.testExecutionToPlan = async function (arguments) {
    fetch = require('node-fetch');
    const mandatoryArgs = ['apiURL', 'auth', 'projectKey', 'token', "testExecutionId", "sprintId"]
    validateArguments(arguments, mandatoryArgs);
    ({ apiURL, projectKey, token, testExecutionId, sprintId } = arguments);
    auth = Buffer.from(arguments.auth).toString('base64');
    const testPlanId = await getTestPlanId(sprintId);
    await graphQLRequest(token, testExecutionsToPlanQuery(testPlanId, [testExecutionId]))
}
