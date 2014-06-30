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
import javafx.scene.control.ChoiceBox;

public class CatSumController extends AbstractController {

    @FXML
    private ChoiceBox<String> catList;
    
    @Override
    protected void submit(ActionEvent event) {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void goBack(ActionEvent event) {
        transition(event, "seeSum");
    }

    @Override
    protected void populate(ActionEvent event) {
        Connection connect = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connect = DriverManager.getConnection(DatabaseInfo.DB_URL);
            
            Statement stmt = connect.createStatement();
            String strSelect = "SELECT CatName FROM Category;";
            
            ResultSet rset = stmt.executeQuery(strSelect);
            ObservableList<String> row = FXCollections.observableArrayList();
            while(rset.next()) {
                row.add(rset.getString("CatName"));
            }
            catList.setItems(row);
            
            connect.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

}
