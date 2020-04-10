const express = require('express');
const app = express();
const path = require('path');

const DISH_SOUP = 0
const DISH_MAIN = 1

const version = 0;

app.get('/daily-menu', (req, res) => {
    res.send([1, 2, 3]);
});

app.get('/version', (req, res) => {
    res.send({version});
});

app.get('/:id', (req, res) => {
    res.sendFile(path.join(__dirname, '0.jpg'));
});

app.get('/update-db', (req, res) => {
    res.send([
        {
            "id": 0,
            "dish_name": "kapustnica",
            "dish_type": DISH_SOUP,
            "price": 20
        },
        {
            "id": 0,
            "dish_name": "hamburger",
            "dish_type": DISH_MAIN,
            "price": 20
        },
    ]);
});

app.listen(4000, () => console.log('listening'));
