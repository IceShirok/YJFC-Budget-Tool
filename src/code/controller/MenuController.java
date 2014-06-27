package src.code.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MenuController {
    
    @FXML
    private Text loginInfo;
    
    protected void setLoginDisplay(String username) {
        loginInfo.setText("Logged in as " + username);
    }
    
    
    public final static String DB_URL = "db\\YJFCBudgetTool.db";
    
    @FXML
    protected void viewSga(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../../res/screens/sgaResults.fxml"));
            SgaResultsController controller = fxmlLoader.<SgaResultsController>getController();
            fxmlLoader.setController(controller);
            
            Parent root = (Parent)fxmlLoader.load();
            Scene scene = new Scene(root, 600, 400);
            
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
            
        } catch (Exception e) {
            System.out.println("Something went wrong with loading the scene...: " + e);
        }
    }
    
    public static void validateLogin() {
        Connection connect = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connect = DriverManager.getConnection("jdbc:sqlite:"+DB_URL);
            
            Statement stmt = connect.createStatement();
            String strSelect = "SELECT * FROM ProactiveYears UNION SELECT * FROM RetroactiveYears;";
            
            ResultSet rset = stmt.executeQuery(strSelect);
            while(rset.next()) {

            }
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

}
