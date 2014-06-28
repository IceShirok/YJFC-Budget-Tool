package src.code.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FencingLaunch extends Application {
    
    private final static int WIDTH = 600;
    private final static int HEIGHT = 400;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../../../res/screens/login.fxml"));
        Scene mainScreen = new Scene(root, WIDTH, HEIGHT);
        primaryStage.setTitle("YJFC Budget Tool - v0.0.00 By: Susanna Dong");
        primaryStage.setScene(mainScreen);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
