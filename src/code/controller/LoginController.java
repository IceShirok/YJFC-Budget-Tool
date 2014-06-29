package src.code.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import src.code.model.DatabaseInfo;
import src.code.model.LoginStore;

public class LoginController extends AbstractController {
    
    @FXML
    private Text message;

    @FXML
    private TextField gtid;

    @FXML
    private PasswordField pw;
    
    @Override
    protected void submit(ActionEvent event) {
        String userType = validateLogin(gtid.getText(), pw.getText());
        if (userType.equals(DatabaseInfo.INVALID_TAG)) {
            message.setText("Username or password is invalid. Try again or contact your sysadmin.");
        } else {
            LoginStore.logIn(gtid.getText(), userType);
            transition(event, "menu");
        }
    }

    @Override
    protected void goBack(ActionEvent event) {
        // not needed - it's the login screen
    }

    @Override
    protected void populate(ActionEvent event) {
        // not needed
    }

    private static String validateLogin(String username, String password) {
        Connection connect = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connect = DriverManager.getConnection(DatabaseInfo.DB_URL);

            Statement stmt = connect.createStatement();
            String strSelect = "SELECT UserType FROM Users WHERE GTID='" + username + "' AND Password='" + password + "';";
            ResultSet rset = stmt.executeQuery(strSelect);
            if (rset.next()) {
                return rset.getString("UserType");
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return DatabaseInfo.INVALID_TAG;
    }
}
