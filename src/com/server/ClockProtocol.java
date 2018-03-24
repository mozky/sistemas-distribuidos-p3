package com.server;

import com.utils.FxTimer;

import java.time.Duration;

public class ClockProtocol {

    private String latestTime;
    private boolean newLeader = false;
    private int newLeaderAlertTimes = 0;

    ClockProtocol() {}

    public String processInput(String theInput) {
        String theOutput = null;
        if (theInput == null) {
            theOutput = "Welcome to the clock network...";
        } else if (theInput.equalsIgnoreCase("bye")) {
            theOutput = "Bye";
        } else {
            String operation;
            if (theInput.lastIndexOf("-") > 0) {
                operation = theInput.substring(0, theInput.lastIndexOf("-"));
            } else {
                operation = theInput;
            }
            switch (operation) {
                case "setTime":
                    String newTime = theInput.substring(theInput.lastIndexOf("-") + 1);
                    latestTime = newTime;
                    newLeader = true;
                    theOutput = "";
                    break;
                case "getTime":
                    if (newLeader && newLeaderAlertTimes < 8) {
                        newLeaderAlertTimes++;
                        theOutput = latestTime;
                    } else {
                        newLeader = false;
                        newLeaderAlertTimes = 0;
                        theOutput = "";
                    }
                    break;
                default:
                    theOutput = "WHAT?";
            }
        }
        return theOutput;
    }
}

