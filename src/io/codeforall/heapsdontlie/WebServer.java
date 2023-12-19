package io.codeforall.heapsdontlie;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {

    public static void main(String[] args) {
        int portNum = 8080;

        try {
            ServerSocket webserver = new ServerSocket(portNum);
            System.out.println("Server waiting at port " + portNum + "!");

            while (true) {
                Socket clientSocket = webserver.accept();

                Thread thread = new Thread(new Client(clientSocket));
                thread.start();
                BufferedReader inFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter outToClient = new PrintWriter(clientSocket.getOutputStream(), true);

                String clientRequest = inFromClient.readLine();

                if (clientRequest != null && clientRequest.startsWith("GET")) {
                    File indexFile = new File("resources/index.html");
                    long fileSizeInBytes = indexFile.length();

                    if (clientRequest.contains("index")) {
                        outToClient.write("HTTP/1.0 200 Document Follows\r\n" +
                                "Content-Type: text/html; charset=UTF-8\r\n" +
                                "Content-Length: " + fileSizeInBytes + " \r\n" + "\r\n");
                        BufferedReader fileReader = new BufferedReader(new FileReader(indexFile));
                        String fileLine;

                        while ((fileLine = fileReader.readLine()) != null) {
                            outToClient.write(fileLine + "\n");
                            outToClient.flush();
                        }
                    }
                }
                File imageFile = new File("resources/elmo.png");
                long imgSizeInBytes = imageFile.length();

                if (clientRequest.contains("imagefile")) {

                    String imgHeader = "HTTP/1.0 200 Document Follows\r\n" +
                            "Content-Type: image/png \r\n" +
                            "Content-Length: " + imgSizeInBytes + " \r\n" +
                            "\r\n";

                    outToClient.write(imgHeader);
                    outToClient.flush();

                    FileInputStream inputStreamImg = new FileInputStream("resources/elmo.png");
                    byte[] bufferImg = new byte[inputStreamImg.available()];
                    OutputStream imgOutToClient = clientSocket.getOutputStream();
                    inputStreamImg.read(bufferImg);
                    imgOutToClient.write(bufferImg);
                    imgOutToClient.flush();

                } else {

                    File notFoundFile = new File("resources/fileNotFound.html");
                    long notFoundFileSize = notFoundFile.length();
                    String notFound = "HTTP/1.0 404 Not Found\n" + "Content-Type: text/html; charset=UTF-8\r\n" +
                            "Content-Length: " + notFoundFileSize + "\r\n" + "\r\n";
                    outToClient.write(notFound);
                    outToClient.flush();
                    BufferedReader notFoundFileReader = new BufferedReader((new FileReader(notFoundFile)));
                    String notFoundLine;

                    while ((notFoundLine = notFoundFileReader.readLine()) != null) {
                        outToClient.write(notFoundLine + "\n");
                        outToClient.flush();
                    }
                    notFoundFileReader.close();
                }
                outToClient.close();
                inFromClient.close();
                clientSocket.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}