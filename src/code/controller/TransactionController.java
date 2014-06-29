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

public class TransactionController extends AbstractController {
    
    @FXML
    private ChoiceBox<String> category;
    
    @FXML
    private ChoiceBox<String> nameCat;

    @Override
    protected void submit(ActionEvent event) {
        // TODO Auto-generated method stub

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
            
            ResultSet rset = stmt.executeQuery("SELECT DISTINCT Category FROM BudgetItem;");
            ObservableList<String> row = FXCollections.observableArrayList();
            while(rset.next()) {
                row.add(rset.getString("Category"));
            }
            category.setItems(row);
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }
    
    @FXML
    protected void fillName(ActionEvent event) {
        Connection connect = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connect = DriverManager.getConnection(DatabaseInfo.DB_URL);
            Statement stmt = connect.createStatement();
            
            String foo = "SELECT DISTINCT Name FROM BudgetItem WHERE Category=\""+category.getValue()+"\";";
            System.out.println(foo);
            ResultSet rset = stmt.executeQuery(foo);
            ObservableList<String> row = FXCollections.observableArrayList();
            while(rset.next()) {
                row.add(rset.getString("Name"));
            }
            nameCat.setItems(row);
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

}
