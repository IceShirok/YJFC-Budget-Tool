package src.code.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class AbstractController {
    
    public final static String SCREEN_URL = "../../../res/screens/";
    public final static String FXML_EXT = ".fxml";
    
    @FXML
    protected abstract void submit(ActionEvent event);
    
    @FXML
    protected abstract void goBack(ActionEvent event);
    
    @FXML
    protected void transition(ActionEvent event, String filename) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(SCREEN_URL+filename+FXML_EXT));
            Parent root = (Parent) fxmlLoader.load();

            AbstractController controller = fxmlLoader.getController();
            fxmlLoader.setController(controller);
            controller.populate(event);

            showScene(event, new Scene(root, 600, 400));

        } catch (Exception e) {
            System.out.println("Something went wrong with loading the scene...: " + e);
        }
    }
    
    @FXML
    protected abstract void populate(ActionEvent event);
    
    public void showScene(ActionEvent event, Scene scene) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

}
