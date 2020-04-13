const express = require('express');
const app = express();
const path = require('path');

const MEAL_SOUP = 0
const MEAL_MAIN = 1

const version = 0;

//λ .\ngrok authtoken 1aTvufvbLVvntu3DjMLe1LDbdnP_74QV2cbhyC9NFvb9Nt1RT

app.get('/daily-menu', (req, res) => {
    console.log("jej")
    res.send([1, 2, 3]);
});

app.get('/version', (req, res) => {
    res.send({version});
});

app.get('/update-db', (req, res) => {
    res.send([
        {
            "id": 0,
            "meal_name": "kapustnica",
            "meal_type": MEAL_SOUP,
            "price": 20
        },
        {
            "id": 1,
            "meal_name": "hamburger",
            "meal_type": MEAL_MAIN,
            "price": 20
        },
    ]);
});


app.get('/:id', (req, res) => {
    res.sendFile(path.join(__dirname, '0.jpg'));
});

app.listen(4000, () => console.log('listening'));
