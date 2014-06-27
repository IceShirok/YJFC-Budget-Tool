package src.code.main;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoadScenes {

    private static LoadScenes loader;
    private Scene mainScreen;
    
    private final static int WIDTH = 600;
    private final static int HEIGHT = 400;

    private LoadScenes() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../../../res/screens/login.fxml"));
        mainScreen = new Scene(root, WIDTH, HEIGHT);
    }
    
    public static LoadScenes getInstance() throws Exception {
        if(loader==null) {
            loader = new LoadScenes();
        }
        return loader;
    }
    
    public Stage makeScene(ActionEvent event) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        return stage;
    }
    
    public void loadScene(Stage stage, String sceneName) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../../../res/screens/"+sceneName));
        mainScreen = new Scene(root, WIDTH, HEIGHT);
        stage.setScene(mainScreen);
        stage.show();
    }
}
