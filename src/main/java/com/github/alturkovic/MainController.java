package com.github.alturkovic;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import lombok.Data;

import java.io.*;

@Data
public class MainController {

    private LifeGridPane lifeGridPane;

    @FXML
    private BorderPane root;

    @FXML
    public TextField step;

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
    public void importGrid() {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Grid (*.grid)", "*.grid"));
        final File file = fileChooser.showOpenDialog(root.getScene().getWindow());

        if(file != null){
            boolean[][] importedGrid = null;

            try {
                try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                    int row = 0;
                    String line;
                    while ((line = br.readLine()) != null) {
                        char[] chars = line.toCharArray();

                        if (importedGrid == null) {
                            importedGrid = new boolean[chars.length][chars.length];
                        }

                        for (int col = 0; col < chars.length; col++) {
                            final char c = chars[col];
                            if (c == '1') {
                                importedGrid[col][row] = true;
                            } else if (c == '0') {
                                importedGrid[col][row] = false;
                            } else {
                                throw new IllegalArgumentException("Unexpected character '" + c + "' in file: " + file);
                            }
                        }
                        row++;
                    }
                }
            } catch (final IOException e) {
                throw new UncheckedIOException(e);
            }

            lifeGridPane.reset();
            lifeGridPane.setNewState(importedGrid);
        }
    }

    @FXML
    public void exportGrid() {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Grid (*.grid)", "*.grid"));
        final File file = fileChooser.showSaveDialog(root.getScene().getWindow());

        if(file != null){
            try {
                final String currentGrid = lifeGridPane.toString();
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write(currentGrid);
                fileWriter.close();
            } catch (final IOException e) {
                throw new UncheckedIOException(e);
            }
        }
    }
}
