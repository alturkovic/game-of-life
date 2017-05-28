package com.github.alturkovic;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import lombok.Data;

@Data
public class MainController {

    private LifeGridPane lifeGridPane;

    @FXML
    public TextField step;

    @FXML
    private BorderPane root;

    @FXML
    private Button toggleAutoUpdate;

    public void postInit() {
        lifeGridPane = (LifeGridPane) root.getCenter();
    }

    @FXML
    public void toggle() {
        if (toggleAutoUpdate.getText().equalsIgnoreCase("start")) {
            lifeGridPane.start(Integer.valueOf(step.getText()));
            toggleAutoUpdate.setText("Stop");
        } else {
            lifeGridPane.stop();
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

    @FXML
    public void createGlider() {
        if (lifeGridPane.getCount() >= 3) {
            lifeGridPane.setNewState(Presets.loadPreset("glider.cgol", 3));
        }
    }

    @FXML
    public void createToad() {
        if (lifeGridPane.getCount() >= 4) {
            lifeGridPane.setNewState(Presets.loadPreset("toad.cgol", 4));
        }
    }

    @FXML
    public void createPulsar() {
        if (lifeGridPane.getCount() >= 15) {
            lifeGridPane.setNewState(Presets.loadPreset("pulsar.cgol", 15));
        }
    }
}
