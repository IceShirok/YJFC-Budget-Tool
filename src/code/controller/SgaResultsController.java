package src.code.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class SgaResultsController extends AbstractController {
    
    public final static String DB_URL = "db\\YJFCBudgetTool.db";
    
    @FXML
    private TableView<String> table;
    
    @FXML
    public void populateResults(int year, boolean wantPrev) {
        Connection connect = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connect = DriverManager.getConnection("jdbc:sqlite:"+DB_URL);
            
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
