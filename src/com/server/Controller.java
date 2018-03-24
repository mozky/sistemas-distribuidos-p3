package com.server;

import com.socketfx.Constants;
import com.socketfx.FxSocketClient;
import com.socketfx.SocketListener;
import com.utils.CustomClock;
import com.utils.FxTimer;
import com.utils.Timer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private boolean configured;
    private boolean edited;

    @FXML
    private Label clockLabel;

    private FxSocketClient socket;
    private CustomClock clock = new CustomClock();
    private Timer timer;

    public Controller() {
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configured = false;
        edited = true;
        this.connect();
    }

    class FxSocketListener implements SocketListener {
        @Override
        public void onMessage(String line) {
            if (line != null && !line.equals("")) {
                System.out.println("Server: " + line);
                if (!configured) {
                    configured = true;
                    timer = FxTimer.runPeriodically(Duration.ofMillis(350), () -> {
                        socket.sendMessage("getTime");
                    });
                } else {
                    String newTimeLabel = clock.setTime(line).getTime().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
                    clockLabel.setText(newTimeLabel);
                }
            } else {
                clockLabel.setText(clock.getTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
            }
        }

        @Override
        public void onClosedStatus(boolean isClosed) {
        }
    }

    private void connect() {
        socket = new FxSocketClient(new FxSocketListener(),
                "localhost",
                6666,
                Constants.instance().DEBUG_NONE);
        socket.connect();
    }

    private String generateProtocolTimeMessage() {
        return "setTime-" + getClockTime();
    }

    private String getClockTime() {
        return String.valueOf(clock.getTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
    }
    @FXML
    private void sendTime(ActionEvent event) {
        socket.sendMessage(generateProtocolTimeMessage());
    }

    @FXML
    private void handlePlusHour() {
        clock.plusHour();
    }

    @FXML
    private void handleMinusHour() {
        clock.minusHour();
    }

    @FXML
    private void handlePlusMinute() {
        clock.plusMinute();
    }

    @FXML
    private void handleMinusMinute() {
        clock.minusMinute();
    }

}
