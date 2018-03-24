package com.client;

import com.utils.FxTimer;
import com.utils.Timer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.util.Duration;
import java.net.URL;
import java.util.ResourceBundle;

import com.socketfx.*;

public class Controller implements Initializable {
    private boolean configured;
    private boolean connected;

    @FXML
    private Label clockLabel;

    @FXML
    private ComboBox clockSelector;

    private FxSocketClient socket;
    private Timer timer;

    public Controller() {
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.connect();
        configured = false;
    }

    private void connect() {
        socket = new FxSocketClient(new FxSocketListener(),
                "localhost",
                6666,
                Constants.instance().DEBUG_NONE);
        socket.connect();
    }

    class FxSocketListener implements SocketListener {

        @Override
        public void onMessage(String line) {
            if (line != null && !line.equals("")) {
                System.out.println("Recibí de masters: " + line);
                if (!configured) {
                    socket.sendMessage("client");
                    configured = true;
                } else if (!connected) {
                    initializeSelector(clockSelector, line);
                    connected = true;
                } else {
                    clockLabel.setText(line);
                }
            }
        }

        @Override
        public void onClosedStatus(boolean isClosed) {
        }
    }

    @FXML
    private void refreshClocks() {
    }

    private void initializeSelector(ComboBox selector, String servers) {
        selector.getItems().addAll(servers.substring(1, servers.length() - 1).replaceAll("\\s+","").split(","));

        selector.setOnAction((event) -> {
            if (timer != null) {
                timer.stop();
            }
            timer = FxTimer.runPeriodically(java.time.Duration.ofMillis(500), () -> {
                sendFetchMessage(String.valueOf(selector.getSelectionModel().getSelectedItem()));
            });
        });
    }

    private void sendFetchMessage(String selectedServer) {
        socket.sendMessage("fetchTime-" + selectedServer);
    }

}
