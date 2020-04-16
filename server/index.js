const express = require('express')
const path = require('path')
const bodyParser = require('body-parser')

const app = express()
app.use(bodyParser.json())

const MEAL_SOUP = 0
const MEAL_MAIN = 1

const version = 0

//λ .\ngrok authtoken 1aTvufvbLVvntu3DjMLe1LDbdnP_74QV2cbhyC9NFvb9Nt1RT

app.get('/daily-menu', (req, res) => {
    res.send([1, 2, 3])
})

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
    ])
})

app.post('/verify-user', (req, res) => {
    const obj = { success: false }
    if(req.body.email === 'admin' && req.body.password === 'admin')
        obj.success = true
    res.send(obj)
})

const orders = []

app.post('/store-order', (req, res) => {
    const obj = { success: false }

    if(Object.keys(req.body).length !== 0) {
        orders.push(req.body)
        obj.success = true
    }

    res.send(obj)
})

app.get('/get-all-orders', (req, res) => {
    res.send(orders)
})

app.get('/debug', (req, res) => {
    console.log(orders)
    res.send(orders)
})

app.listen(process.env.PORT || 4000, () => console.log('listening'))
