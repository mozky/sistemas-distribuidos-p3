package com.server;

import java.net.*;
import java.io.*;


public class Server {
    public static void main(String[] args) {
        int portNumber = 6666;
        boolean listening = true;
        ClockProtocol protocol = new ClockProtocol();

        //We start listening for socket connections
        System.out.println("Server started... listening for connections");
        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            while (listening) {
                new ServerThread(serverSocket.accept(), protocol).start();
            }
        } catch (IOException e) {
            System.err.println("Could not listen on port " + portNumber);
            System.exit(-1);
        }
    }
}
