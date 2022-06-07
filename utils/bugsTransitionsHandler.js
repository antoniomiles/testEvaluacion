const fs = require('fs');
const { exit } = require('process');
let fetch;

let apiURL, auth, projectKey;

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

const issueFormat = (projectKey, assigneeId, sprintId) => {
  return {
    "update": {},
    "fields": {
      "summary": "",
      "issuetype": {
        "name": "Bug de Desarrollo"
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
  }
};

function closeTestExecutions(currentTEKey) {
  closePreviousTestExecutions()
  makeTransition(currentTEKey, "DONE", "TESTEXECUTION")
}

function closePreviousTestExecutions() {
  fetch(`${apiURL}/search?jql=project="${projectKey}"%20AND%20type="Test Execution"%20AND%20status="PRUEBAS DOING"%20AND%20summary~"TE"&fields=summary`, {
    method: 'GET',
    headers: {
      'Authorization': `Basic ${auth}`,
      'Accept': 'application/json'
    }
  })
    .then(response => {
      console.log(
        `Response: ${response.status} ${response.statusText}`
      );
      return response.text();
    })
    .then(text => {
      const previousTestExecutionsData = JSON.parse(text)
      for (const issue of previousTestExecutionsData.issues) {
        makeTransition(issue.key, "DONE", "TESTEXECUTION")
      }
    })
    .catch(err => console.error(err));
}

function createBugInJira(bodyData, parentsKeys) {
  fetch(`${apiURL}/issue`, {
    method: 'POST',
    headers: {
      'Authorization': `Basic ${auth}`,
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(bodyData)
  }).then(response => {
    return response.text();
  })
    .then(responseData => {
      console.log(`Respuesta Creacion de Bug: ${responseData}`)
      const bugJiraResponse = JSON.parse(responseData)
      const bugKey = bugJiraResponse.key
      for (parentKey of Object.values(parentsKeys)) {
        const issueLinkData = JSON.parse(JSON.stringify(issueLinkDataFormat));
        issueLinkData.outwardIssue.key = bugKey
        issueLinkData.inwardIssue.key = parentKey
        createIssueLink(issueLinkData)
      }
      makeTransition(bugKey, "PRUEBAS DOING", "BUG")
      makeTransition(parentsKeys.testCaseKey, "PRUEBAS DOING", "TESTCASE")
    })
    .catch(err => console.error(err));
}

function createIssueLink(issueLinkData) {
  fetch(`${apiURL}/issueLink`, {
    method: 'POST',
    headers: {
      'Authorization': `Basic ${auth}`,
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(issueLinkData)
  })
    .then(response => {
      console.log(
        `Response: ${response.status} ${response.statusText}`
      );
      return response.text();
    })
    .then(text => console.log(`Respuesta Creacion de IssueLink: ${text}`))
    .catch(err => console.error(err));
}

function closePreviousBugs(testCaseKey) {
  fetch(`${apiURL}/issue/${testCaseKey}?fields=issuelinks`, {
    method: 'GET',
    headers: {
      'Authorization': `Basic ${auth}`,
      'Accept': 'application/json'
    }
  })
    .then(response => {
      console.log(
        `Response: ${response.status} ${response.statusText}`
      );
      return response.text();
    })
    .then(response => {
      const issueLinks = JSON.parse(response).fields.issuelinks
      for (bugLink of issueLinks) {
        if (bugLink.type.name == "Defect") {
          makeTransition(bugLink.outwardIssue.key, "DONE", "BUG")
        }
      }
    })
    .catch(err => console.error(err));
}

function makeTransition(issueIdOrKey, typeTransition, typeIssue) {
  const bodyData = getValidTransitionData(typeTransition, typeIssue);
  fetch(`${apiURL}/issue/${issueIdOrKey}/transitions`, {
    method: 'POST',
    headers: {
      'Authorization': `Basic ${auth}`,
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(bodyData)
  })
    .then(response => {
      console.log(
        `Response: ${response.status} ${response.statusText}`
      );
      return response.text();
    })
    .then(text => console.log(text))
    .catch(err => console.error(err));
}
const getValidTransitionData = (typeTransition, typeIssue) => {
  let validTransitionId;
  if (typeIssue.toLowerCase() == "testcase" || typeIssue.toLowerCase() == "testexecution") {
    validTransitionId = typeTransition.toLowerCase() == "done" ? 91 : 71
  } else if (typeIssue.toLowerCase() == "bug") {
    validTransitionId = typeTransition.toLowerCase() == "done" ? 31 : 21
  }
  return {
    transition: {
      id: validTransitionId
    }
  }
}

module.exports.processJiraScenarios = function (arguments) {
  let reportPath;
  const mandatoryArgs = ['reportPath']

  Object.keys(arguments).forEach((argument) => {
    const indexArg = mandatoryArgs.indexOf(argument);
    if (indexArg !== -1) {
      mandatoryArgs.splice(indexArg, 1);
    } else {
      console.error(`Invalid argument: '${argument}' in the command.`);
      exit(0)
    }
  })
  reportPath = arguments.reportPath;

  if (mandatoryArgs.length != 0) {
    console.error(`Sorry, some arguments are missing: '${mandatoryArgs}' in the command.`);
    exit(0)
  }

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
    for (const scenario of feature.elements) {
      let testCaseKey;
      for (const step of scenario.steps) {
        if (step.result.status == "failed") {
          const parents = {}
          for (const tag of scenario.tags) {
            if (tag.name.includes("REQ"))
              parents.userHistoryKey = tag.name.split("REQ_").pop()
            if (tag.name.includes("TEST"))
              parents.testCaseKey = tag.name.split("TEST_").pop()
          }
          failedScenarios[scenario.name] = {
            errorMessage: step.result.error_message,
            parentLinks: parents
          }
        }
        else if (step.result.status == "passed") {
          for (const tag of scenario.tags) {
            if (tag.name.includes("TEST"))
              testCaseKey = tag.name.split("TEST_").pop()
          }
          passedScenarios.scenarios[scenario.name] = {
            testCaseKey: testCaseKey
          }
        }
      }
    }
  }

  for (const [key, value] of Object.entries(failedScenarios)) {
    if (key in passedScenarios.scenarios) {
      delete passedScenarios.scenarios[key]
    }
  }

  if (Object.keys(failedScenarios).length == 0) {
    passedScenarios.currentTEStatus = 'passed'
    //closeTestExecutions()      
  } else {
    passedScenarios.currentTEStatus = 'failed'
  }
  const scenariosReportFolder = `${__dirname}/reports`
  if (!fs.existsSync(scenariosReportFolder)){
    fs.mkdirSync(scenariosReportFolder,true)
  }
  fs.writeFileSync(scenariosReportFolder+"/passedScenarios.json", JSON.stringify(passedScenarios), 'utf-8');
  fs.writeFileSync(scenariosReportFolder+"/failedScenarios.json", JSON.stringify(failedScenarios), 'utf-8');
};

module.exports.closePassedScenarios = function (arguments) {
  fetch = require('node-fetch');
  let passedScenariosPath, currentTEKey;
  const mandatoryArgs = ['apiURL', 'auth', 'passedScenariosPath', 'projectKey', 'currentTEKey']

  Object.keys(arguments).forEach((argument) => {
    const indexArg = mandatoryArgs.indexOf(argument);
    if (indexArg !== -1) {
      mandatoryArgs.splice(indexArg, 1);
    } else {
      console.error(`Invalid argument: '${argument}' in the command.`);
      exit(0)
    }
  })
  apiURL = arguments.apiURL
  auth = Buffer.from(arguments.auth).toString('base64');
  projectKey = arguments.projectKey
  currentTEKey = arguments.currentTEKey
  passedScenariosPath = arguments.passedScenariosPath;

  if (mandatoryArgs.length != 0) {
    console.error(`Sorry, some arguments are missing: '${mandatoryArgs}' in the command.`);
    exit(0)
  }

  if (!fs.existsSync(passedScenariosPath)) {
    console.error(`File not found in the following path: '${passedScenariosPath}'`)
    exit(0)
  }

  const passedScenarios = JSON.parse(fs.readFileSync(passedScenariosPath, { encoding: 'utf8', flag: 'r' }))
  for (const [scenarioName, testCaseData] of Object.entries(passedScenarios.scenarios)) {
    closePreviousBugs(testCaseData.testCaseKey)
    makeTransition(testCaseData.testCaseKey, "DONE","TESTCASE")
  }

  if (passedScenarios.currentTEStatus != "failed") {
    closeTestExecutions(currentTEKey)      
  }
};

module.exports.createBugsInJira = function (arguments) {
  fetch = require('node-fetch');
  let failedScenariosPath, assigneeId, sprintId;
  const mandatoryArgs = ['apiURL', 'auth', 'failedScenariosPath', 'projectKey','assigneeId','sprintId']

  Object.keys(arguments).forEach((argument) => {
    const indexArg = mandatoryArgs.indexOf(argument);
    if (indexArg !== -1) {
      mandatoryArgs.splice(indexArg, 1);
    } else {
      console.error(`Invalid argument: '${argument}' in the command.`);
      exit(0)
    }
  })
  apiURL = arguments.apiURL
  auth = Buffer.from(arguments.auth).toString('base64');
  projectKey = arguments.projectKey
  failedScenariosPath = arguments.failedScenariosPath;
  assigneeId = arguments.assigneeId 
  sprintId = arguments.sprintId

  if (mandatoryArgs.length != 0) {
    console.error(`Sorry, some arguments are missing: '${mandatoryArgs}' in the command.`);
    exit(0)
  }

  if (!fs.existsSync(failedScenariosPath)) {
    console.error(`File not found in the following path: '${failedScenariosPath}'`)
    exit(0)
  }

  const failedScenarios = JSON.parse(fs.readFileSync(failedScenariosPath, { encoding: 'utf8', flag: 'r' }))
  for (const [key, value] of Object.entries(failedScenarios)){
    const bugData = JSON.parse(JSON.stringify(issueFormat(projectKey, assigneeId, sprintId)));
    bugData.fields.summary=`BUG - Prueba E2E: ${key}` 
    bugData.fields.description.content[0].content[0].text = failedScenarios[key].errorMessage
    createBugInJira(bugData, failedScenarios[key].parentLinks);
  }
};
