package src.code.main;

import src.code.controller.AbstractController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FencingLaunch extends Application {
    
    public final static int WIDTH = 600;
    public final static int HEIGHT = 400;

    @Override
    public void start(Stage primaryStage) throws Exception {
        String foo = AbstractController.SCREEN_URL+"login"+AbstractController.FXML_EXT;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(foo));
        Parent root = (Parent) loader.load();
        Scene mainScreen = new Scene(root, WIDTH, HEIGHT);
        primaryStage.setTitle("YJFC Budget Tool - v0.0.00 By: Susanna Dong");
        primaryStage.setScene(mainScreen);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
