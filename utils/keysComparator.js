const fs = require('fs');
const path = require('path');

const featureTestMap = {}
const feauturesJiraPath = './src/test/resources/featuresJira'
const featureOriginalesPath = './src/test/resources/features'

processFilesFromDir(feauturesJiraPath, 'get');
processFilesFromDir(featureOriginalesPath, 'override')

function processFilesFromDir(dirPath, method='get'){
    fs.readdirSync(dirPath).forEach(file => {
        if (file.includes('.feature')){
            if (method=='get'){
                getDataFromFile(`${dirPath}/${file}`);
            } else{
                updateTagInFeature(`${dirPath}/${file}`);
            }
        }
    });
}

function getDataFromFile(filePath){
    fs.readFileSync(path.resolve('',filePath), 'utf-8').split(/\r?\n/).forEach((featureLine) => {
        if(featureLine.trim().startsWith('@TEST_')){
            const tagsList = featureLine.trim().split(" ")
            const jiraKey = tagsList[0]
            const comparatorList = featureLine.trim().split(/@TEST_[a-zA-Z]*-\d*/).pop().trim()
            featureTestMap[comparatorList]=jiraKey;           
        }
    });
}

function updateTagInFeature(filePath){
    let featureData="";
    fs.readFileSync(path.resolve('',filePath), 'utf-8').split(/\r?\n/).forEach((featureLine) => {
        
        if(featureLine.trim().startsWith('@id:')){
            const comparator = featureLine.split(/@id:\d*/).pop().trim();
            featureLine = `${featureTestMap[comparator]} ${comparator}`
        }
        featureData+=featureLine+"\n";
    });
    fs.writeFileSync(path.resolve('', filePath), featureData, 'utf-8');
}