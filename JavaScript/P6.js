/*
<!--
Order Management - using JSON Object
Objective: Implement the CRUD operations.

Requirements:
------------------------------------------------
Method	    Endpoint	    Description
------------------------------------------------
POST	   /orders	    	Create a new order
GET	       /orders	    	Get all orders
GET	       /orders/:id	    Get a order by ID
PUT	       /orders/:id		Update a order by ID
DELETE	   /orders/:id		Delete a order by ID
------------------------------------------------

Reference JSON format for Object:
---------------------------------
{ 
	id: 1,
	customerName: "Azar",
	totalPrice: 1	50.0
}

NOTE: id value starts with 1, and increments by 1, for every new entry.

2. Implementation Requirements:
-------------------------------
Create a JSON Object (local)
Implement proper error handling
Add data validation


3. API Response Format:
-----------------------	
Method: POST
Path: /orders

Response:
    If successful:
      res.status(201).send(order);
      
====================================

Method: GET
Path: /orders

Response:
    If successful:
      res.status(200).send(orders);

=====================================
Method: GET
Path: /orders/:id

NOTE: pass (id value as URI params)

Response:
    If successful:
		res.status(200).send(order);
    
    If not found:
        res.status(404).send();

====================================
Method: PUT
Path: /orders/:id

NOTE: pass (id value as URI params)

Response:
    If successful:
		res.status(200).send(order);
    
    If not found:
        res.status(404).send();

===================================

Method: DELETE
Path: /orders/:id

NOTE: pass (id value as URI params)

Response:
    If successful:
        res.status(200).send();

-->

<config>
    <url value="http://10.11.7.119:3000"></url>
</config>
*/

const express = require('express');
const app = express();
const port = 3000;

app.use(express.json());

let orders = [];
let currentId = 1;

app.post('/orders', (req, res) => {
    const { customerName, totalPrice } = req.body;
    if (!customerName) {
        return res.status(400).send({ error: 'Invalid data' });
    }
    const order = { id: currentId++, customerName, totalPrice };
    orders.push(order);
    res.status(201).send(order);
});

app.get('/orders', (req, res) => {
    res.status(200).send(orders);
});

app.get('/orders/:id', (req, res) => {
    const order = orders.find(o => o.id === parseInt(req.params.id));
    if (!order) {
        return res.status(404).send();
    }
    res.status(200).send(order);
});

app.put('/orders/:id', (req, res) => {
    const order = orders.find(o => o.id === parseInt(req.params.id));
    if (!order) {
        return res.status(404).send();
    }
    const { customerName, totalPrice } = req.body;
    if (!customerName) {
        return res.status(400).send({ error: 'Invalid data' });
    }
    order.customerName = customerName;
    order.totalPrice = totalPrice;
    res.status(200).send(order);
});

app.delete('/orders/:id', (req, res) => {
    const index = orders.findIndex(o => o.id === parseInt(req.params.id));
    if (index === -1) {
        return res.status(404).send();
    }
    orders.splice(index, 1);
    res.status(200).send();
});

app.listen(port, () => {
    console.log(`Server running on port ${port}`);
});