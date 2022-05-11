const fs = require('fs');

fs.readFile('./sprintInfo.json', 'utf8', (err, data) => {
    if (err) {
        console.error(err);
        return;
    }

    let id;
    const sprintResponse = JSON.parse(data)
    for (const sprint of sprintResponse.values) {
        if (sprint.state == 'active')
            id=`${sprint.id}`;
    }
    
    fs.writeFile('./sprintId.txt', id, err => {
        if (err) {
          console.error(err);
        }
    });
});