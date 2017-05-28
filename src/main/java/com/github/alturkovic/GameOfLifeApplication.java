package com.github.alturkovic;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class GameOfLifeApplication extends Application {

    private static final int WIDTH = 600;
    private static final int HEIGHT = WIDTH + 35; // buttons
    private static final int COUNT = 100;

    private MainController mainController;

    public static void main(final String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(final Stage primaryStage) throws Exception {
        final FXMLLoader fxmlLoader = new FXMLLoader();
        final BorderPane root = fxmlLoader.load(getClass().getResource("/main.fxml").openStream());
        mainController = fxmlLoader.getController();
        final LifeGridPane lifeGridPane = new LifeGridPane(WIDTH / COUNT, COUNT);

        root.setCenter(lifeGridPane);
        mainController.postInit();

        final Scene scene = new Scene(root, WIDTH, HEIGHT);
        primaryStage.setTitle("Conway's Game of Life");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        mainController.getUpdateGridTimer().cancel();
        super.stop();
    }
}
