package src.code.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import src.code.model.LoginStore;

public class LoginController extends AbstractController {

    @FXML
    private Text actiontarget;

    @FXML
    private TextField gtid;

    @FXML
    private PasswordField pw;

    @FXML
    protected void enterAction(ActionEvent event) {
        String userType = validateLogin(gtid.getText(), pw.getText());
        if (userType.equals(INVALID_TAG)) {
            actiontarget
                    .setText("Username or password is invalid. Try again or contact your sysadmin.");
        } else {
            LoginStore.logIn(gtid.getText(), userType);
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                        "../../../res/screens/menu.fxml"));
                Parent root = (Parent) fxmlLoader.load();

                MenuController controller = fxmlLoader
                        .<MenuController> getController();
                fxmlLoader.setController(controller);
                controller.setLoginDisplay(gtid.getText());

                showScene(event, new Scene(root, 600, 400));

            } catch (Exception e) {
                System.out
                        .println("Something went wrong with loading the scene...: "
                                + e);
            }
        }
    }

    public final static String DB_URL = "db\\YJFCBudgetTool.db";
    public final static String INVALID_TAG = "INVALID";

    public static String validateLogin(String username, String password) {
        Connection connect = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connect = DriverManager.getConnection("jdbc:sqlite:" + DB_URL);

            Statement stmt = connect.createStatement();
            String strSelect = "SELECT UserType FROM Users WHERE GTID='"
                    + username + "' AND Password='" + password + "';";
            ResultSet rset = stmt.executeQuery(strSelect);
            if (rset.next()) {
                return rset.getString("UserType");
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return INVALID_TAG;
    }
}
