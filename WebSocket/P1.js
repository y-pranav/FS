const WebSocket = require('ws');
const wss = new WebSocket.Server({ port: 8081 });

let employees = [];
let idCounter = 1;

wss.on('connection', (ws) => {
    console.log('Client connected');
    
    ws.on('message', (message) => {
        console.log(`Received: ${message}`);
        const parts = message.toString().split(' ');
        const command = parts[0].toUpperCase();

        if (command === 'INSERT' && parts.length === 3) {
            const name = parts[1];
            const salary = parseInt(parts[2]);
            
            if (!isNaN(salary)) {
                employees.push({ id: idCounter++, name, salary });
                ws.send('Employee inserted successfully.');
            } else {
                ws.send('Invalid salary.');
            }
        } else if (command === 'RETRIEVE') {
            if (employees.length === 0) {
                ws.send('No employees found.');
            } else {
                employees.forEach(emp => ws.send(`ID: ${emp.id}, Name: ${emp.name}, Salary: ${emp.salary}`));
            }
        } else {
            ws.send('Invalid command.');
        }
    });

    ws.on('close', () => {
        console.log('Client disconnected');
    });
});

console.log('WebSocket server is running on ws://localhost:8081');
