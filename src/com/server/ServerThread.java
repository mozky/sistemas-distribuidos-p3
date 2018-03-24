package com.server;

import java.net.*;
import java.io.*;


public class ServerThread extends Thread {
    private Socket socket;
    private ClockProtocol cp;

    public ServerThread(Socket socket, ClockProtocol protocol) {
        super("ClockThread");
        this.socket = socket;
        this.cp = protocol;
    }

    public void run() {

        try (
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(
                                socket.getInputStream()));
        ) {
            String inputLine, outputLine;
            outputLine = cp.processInput(null);
            out.println(outputLine);

            while ((inputLine = in.readLine()) != null) {
                outputLine = cp.processInput(inputLine);
                out.println(outputLine);

                if (outputLine.equals("Bye"))
                    break;
            }

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
