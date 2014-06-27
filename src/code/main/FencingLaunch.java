package src.code.main;

import javafx.application.Application;
import javafx.stage.Stage;

public class FencingLaunch extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        LoadScenes sceneMaker = LoadScenes.getInstance();
        primaryStage.setTitle("YJFC Budget Tool - v0.0.00 By: Susanna Dong");
        sceneMaker.loadScene(primaryStage, "login.fxml");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
