package src.code.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import src.code.model.DatabaseInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;

public class SgaFormController extends AbstractController {
    
    @FXML
    private ChoiceBox<Integer> yearsList;
    
    @FXML
    private CheckBox wantPrev;
    
    @Override
    protected void submit(ActionEvent event) {
        transition(event, "sgaResults");
    }
    
    @Override
    protected void goBack(ActionEvent event) {
        transition(event, "menu");
    }

    @Override
    protected void populate(ActionEvent event) {
        Connection connect = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connect = DriverManager.getConnection(DatabaseInfo.DB_URL);
            
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

}
