package src.code.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

public class SgaFormController extends AbstractController {
    
    @FXML
    private ChoiceBox<Integer> yearsList;
    
    @FXML
    private CheckBox wantPrev;
    
    public final static String DB_URL = "db\\YJFCBudgetTool.db";
    
    @FXML
    protected void populateForm() {
        Connection connect = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connect = DriverManager.getConnection("jdbc:sqlite:"+DB_URL);
            
            Statement stmt = connect.createStatement();
            String strSelect = "SELECT DISTINCT Year FROM BudgetItem;";
            
            ResultSet rset = stmt.executeQuery(strSelect);
            ObservableList<Integer> row = FXCollections.observableArrayList();
            while(rset.next()) {
                row.add(rset.getInt("Year"));
                System.out.println(rset.getInt("Year"));
            }
            yearsList.setItems(row);
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }
    
    @FXML
    protected void submitSga(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../../res/screens/sgaResults.fxml"));
            Parent root = (Parent)fxmlLoader.load();
            
            SgaResultsController controller = fxmlLoader.<SgaResultsController>getController();
            fxmlLoader.setController(controller);
            controller.populateResults(yearsList.getValue(), wantPrev.isSelected());
            
            showScene(event, new Scene(root, 600, 400));
            
        } catch (Exception e) {
            System.out.println("Something went wrong with loading the scene...: " + e);
        }
    }
    
    @FXML
    protected void goBack(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../../res/screens/menu.fxml"));
            MenuController controller = fxmlLoader.<MenuController>getController();
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

}
