package com.github.alturkovic;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import lombok.Data;

import java.util.Timer;
import java.util.TimerTask;

@Data
public class MainController {

    private Timer updateGridTimer;
    private LifeGridPane lifeGridPane;

    @FXML
    public TextField step;

    @FXML
    private BorderPane root;

    @FXML
    private Button toggleAutoUpdate;

    public void postInit() {
        lifeGridPane = (LifeGridPane) root.getCenter();
        updateGridTimer = new Timer();
    }

    @FXML
    public void toggle() {
        if (toggleAutoUpdate.getText().equalsIgnoreCase("start")) {
            updateGridTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    lifeGridPane.update();
                }
            }, 0, Integer.valueOf(step.getText()));
            toggleAutoUpdate.setText("Stop");
        } else {
            updateGridTimer.cancel();
            updateGridTimer = new Timer();
            toggleAutoUpdate.setText("Start");
        }
    }

    @FXML
    public void singleStep() {
        lifeGridPane.update();
    }

    @FXML
    public void reset() {
        lifeGridPane.reset();
    }
}
