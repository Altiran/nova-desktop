package com.altiran.novadesktop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Objects;

public class Main extends Application {
    private double xOffset = 0;
    private double yOffset = 0;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the FXML file and set the controller
        FXMLLoader loader = new FXMLLoader(getClass().getResource("window.fxml"));
        loader.setController(new Controller());
        Parent root = loader.load();

        // Set up the primary stage
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setTitle("NovaDesktop");

        // Create the scene and set it on the stage
        Scene scene = new Scene(root, 800, 640);
        primaryStage.setScene(scene);

        // Load external stylesheet
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("window.css")).toExternalForm());

        // Show the primary stage
        primaryStage.show();

        // Implement window dragging functionality
        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        root.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - xOffset);
            primaryStage.setY(event.getScreenY() - yOffset);
        });
    }
}
