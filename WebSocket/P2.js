/*
<!--
Real-Time Group Chat using WebSockets:
----------------------------------

You are required to build a real-time group chat application using WebSockets, 
where multiple users (clients) can join a chatroom and exchange messages in 
real-time. The application must consist of a WebSocket server and a browser-based
client. All messages sent by any client must be visible to all other connected 
clients, including the sender — similar to a WhatsApp group.

Functional Requirements:
=========================
1. WebSocket Server (Node.js):
------------------------------
You must implement a WebSocket server with the following behavior:
	- Accept connections from multiple clients.
	- Maintain a list of all connected clients.
	- When a message is received from any client:
		- Broadcast that message to all connected clients.
	- Handle client disconnections and remove them from the active list.

Use the ws npm package to create the server. The server should run on ws://<ip-address>:8080.

2. Web-based Client (HTML + JavaScript):
----------------------------------------
You must create a basic client interface with the following requirements:
	- Connect to the WebSocket server at ws://localhost:8080.
	- The page should have:
		- A <div> with id="chat" that shows all chat messages.
		- An <input> box with id="msg" to type the message.
		- A <button> that, when clicked, sends the message.
	- When a message is received from the server:
		- It must be displayed as a new paragraph <p> inside the #chat area.
	- When the user sends a message:
		- It should be sent to the server using WebSocket.
		- The input box should be cleared after sending.
		
========================================================
EXAMPLE URL value=>   http://10.11.xx.xx:5500/index.html

-->
<config>
    <url value=""></url>
</config>

*/