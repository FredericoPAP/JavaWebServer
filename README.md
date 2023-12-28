JavaWebServer (6th week of Bootcamp)

Overview 
This is a simple Java-based web server that demonstrates the basic functionalities of handling HTTP requests using sockets, BufferedReader, PrintWriter and FileInputStreams.

Features
HTTP Server: Handles basic HTTP GET requests. 
Sockets: Utilizes Java's Socket class for communication between the server and clients. 
File I/O: Uses FileInputStream to read and serve static files.

Access the Server: Open a web browser and navigate to localhost:8080 to access the server. 
If you navigate to localhost:8080/index will show an index.html page and localhost:8080/imagefile will show an image. 
It will return a 404 with any other request.

Configuration
Port: By default, the server runs on port 8080. You can change this by modifying the portNum variable in the WebServer class.

File Structure
Client.java: Class that implements Runnable. 
WebServer.java: Main server implementation.
resources/: Directory containing static web content (HTML) and one image (.png).
