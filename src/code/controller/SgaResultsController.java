package src.code.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import src.code.model.DatabaseInfo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

public class SgaResultsController extends AbstractController {
    
    @FXML
    private TableView<String> table;

    @Override
    protected void submit(ActionEvent event) {
        // not needed
    }

    @Override
    protected void goBack(ActionEvent event) {
        transition(event, "sgaForm");
    }
    
    @Override
    protected void populate(ActionEvent event) {
        //populateResults(yearsList.getValue(), wantPrev.isSelected());
    }
    
    private void populateResults(int year, boolean wantPrev) {
        Connection connect = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connect = DriverManager.getConnection(DatabaseInfo.DB_URL);
            
            Statement stmt = connect.createStatement();
            String strSelect = null;
            
            if(wantPrev) {
                strSelect = "SELECT * FROM ProactiveYears UNION SELECT * FROM RetroactiveYears;";
            } else {
                strSelect = "SELECT * FROM SgaAllocation WHERE Year="+year+";";
            }
            
            ResultSet rset = stmt.executeQuery(strSelect);
            
            //ObservableList<ObservableList<String>> csvData = FXCollections.observableArrayList(); 
            while(rset.next()) {
                //row.add(rset.getInt("Year"));
                System.out.println(rset.getString("Name"));
                System.out.println(rset.getString("Category"));
                System.out.println(rset.getInt("Year"));
            }
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

}
