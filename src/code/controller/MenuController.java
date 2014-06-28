package src.code.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import src.code.model.LoginStore;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MenuController extends AbstractController {

    @FXML
    private Text loginInfo;

    protected void setLoginDisplay(String username) {
        loginInfo.setText("Logged in as " + username);
    }

    public final static String DB_URL = "db\\YJFCBudgetTool.db";

    @FXML
    protected void viewSga(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                    "../../../res/screens/sgaForm.fxml"));
            Parent root = (Parent) fxmlLoader.load();

            SgaFormController controller = fxmlLoader
                    .<SgaFormController> getController();
            fxmlLoader.setController(controller);
            controller.populateForm();

            Scene scene = new Scene(root, 600, 400);

            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            System.out
                    .println("Something went wrong with loading the scene...: "
                            + e);
        }
    }

    @FXML
    protected void logout(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                    "../../../res/screens/login.fxml"));
            Parent root = (Parent) fxmlLoader.load();

            LoginController controller = fxmlLoader
                    .<LoginController> getController();
            fxmlLoader.setController(controller);
            LoginStore.logOut();

            showScene(event, new Scene(root, 600, 400));

        } catch (Exception e) {
            System.out
                    .println("Something went wrong with loading the scene...: "
                            + e);
        }
    }

    public static void validateLogin() {
        Connection connect = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connect = DriverManager.getConnection("jdbc:sqlite:" + DB_URL);

            Statement stmt = connect.createStatement();
            String strSelect = "SELECT * FROM ProactiveYears UNION SELECT * FROM RetroactiveYears;";

            ResultSet rset = stmt.executeQuery(strSelect);
            while (rset.next()) {

            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

}
