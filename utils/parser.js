const fs = require('fs');

fs.readFile('./keysResponse.json', 'utf8', (err, data) => {
    if (err) {
        console.error(err);
        return;
    }

    let keys = "";
    const cucumberResponse = JSON.parse(data)
    for (const response of cucumberResponse.updatedOrCreatedTests) {
        keys+=`${response.key};`
    }
    keys = keys.substring(0, keys.length-1)

    fs.writeFile('./keys.txt', keys, err => {
        if (err) {
          console.error(err);
        }
    });
});