const fs = require('fs');
const path = require('path');

const featureTestMap = {}
const feauturesJiraPath = './src/test/resources/featuresJira'
const featureOriginalesPath = './src/test/resources/features'

processFilesFromDir(feauturesJiraPath, 'get');
processFilesFromDir(featureOriginalesPath, 'override')

function processFilesFromDir(dirPath, method = 'get') {
    fs.readdirSync(dirPath).forEach(file => {
        const fileInfo = fs.openSync(`${dirPath}/${file}`, 'r')
        if (fs.fstatSync(fileInfo).isDirectory()) {
            const newDirPath = `${dirPath}/${file}`
            fs.readdirSync(newDirPath).forEach(fileNested => {
                processFile(newDirPath, fileNested, method)
            });
        }
        else {
            processFile(dirPath, file, method)
        }
    });
}

function processFile(dirPath, fileName, method) {
    if (fileName.includes('.feature')) {
        if (method == 'get') {
            getDataFromFile(`${dirPath}/${fileName}`);
        } else {
            updateTagInFeature(`${dirPath}/${fileName}`);
        }
    }
}

function getDataFromFile(filePath) {
    fs.readFileSync(path.resolve('', filePath), 'utf-8').split(/\r?\n/).forEach((featureLine) => {
        if (featureLine.trim().startsWith('@TEST_')) {
            const tagsList = featureLine.trim().split(" ")
            const jiraKey = tagsList[0]
            const comparatorList = featureLine.trim().split(/@TEST_[a-zA-Z]*-\d*/).pop().trim()
            const sortComparator = comparatorList.replace(/\s+/g, " ").split(" ").sort().join(" ")
            featureTestMap[sortComparator] = jiraKey;
        }
    });
}

function updateTagInFeature(filePath) {
    let featureData = "";
    fs.readFileSync(path.resolve('', filePath), 'utf-8').split(/\r?\n/).forEach((featureLine) => {

        if (featureLine.trim().startsWith('@id:')) {
            const comparator = featureLine.split(/@id:\d*/).pop().trim();
            const sortComparator = comparator.replace(/\s+/g, " ").split(" ").sort().join(" ");
            if (sortComparator in featureTestMap) {
                featureLine = `${featureTestMap[sortComparator]} ${sortComparator}`
            }
        }
        featureData += featureLine + "\n";
    });
    fs.writeFileSync(path.resolve('', filePath), featureData, 'utf-8');
}