package src.code.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import src.code.model.LoginStore;
import src.code.model.LoginValidator;

public class LoginController {
    
    @FXML
    private Text actiontarget;
    
    @FXML
    private TextField gtid;
    
    @FXML
    private PasswordField pw;

    @FXML
    protected void enterAction(ActionEvent event) {
        String userType = LoginValidator.validateLogin(gtid.getText(), pw.getText());
        if(userType.equals(LoginValidator.INVALID_TAG)) {
            actiontarget.setText("Username or password is invalid. Try again or contact your sysadmin.");
        } else {
            LoginStore.logIn(gtid.getText(), userType);
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../../res/screens/menu.fxml"));
                Parent root = (Parent)fxmlLoader.load();
                
                MenuController controller = fxmlLoader.<MenuController>getController();
                fxmlLoader.setController(controller);
                controller.setLoginDisplay(gtid.getText());
                
                Scene scene = new Scene(root, 600, 400);
                
                Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                stage.setScene(scene);
                stage.show();
                
            } catch (Exception e) {
                System.out.println("Something went wrong with loading the scene...: " + e);
            }
        }
    }
}
