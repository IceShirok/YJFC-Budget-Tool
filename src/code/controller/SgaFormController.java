package src.code.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import src.code.model.DatabaseInfo;
import src.code.model.SgaPOJO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

public class SgaFormController extends AbstractController {
    
    @FXML
    private ChoiceBox<Integer> yearsList;
    
    @FXML
    private CheckBox wantPrev;
    
    @FXML
    private TableView<SgaPOJO> sgaTable;
    
    @FXML
    private TableColumn<SgaPOJO, String> colName;
    @FXML
    private TableColumn<SgaPOJO, String> colCat;
    @FXML
    private TableColumn<SgaPOJO, Double> colPyReq;
    @FXML
    private TableColumn<SgaPOJO, Double> colPyApp;
    @FXML
    private TableColumn<SgaPOJO, Double> colCyReq;
    @FXML
    private TableColumn<SgaPOJO, Double> colCyApp;
    
    @FXML
    private Text message;
    
    @Override
    protected void submit(ActionEvent event) {
        populateResults(yearsList.getValue(), wantPrev.isSelected());
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
            }
            yearsList.setItems(row);
            
            connect.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }
    
    protected void populateResults(int year, boolean wantPrev) {   
        
        colName.setCellValueFactory(new PropertyValueFactory<SgaPOJO,String>("Name"));
        colCat.setCellValueFactory(new PropertyValueFactory<SgaPOJO,String>("Category"));
        colPyReq.setCellValueFactory(new PropertyValueFactory<SgaPOJO,Double>("PrevYearReq"));
        colPyApp.setCellValueFactory(new PropertyValueFactory<SgaPOJO,Double>("PrevYearApp"));
        colCyReq.setCellValueFactory(new PropertyValueFactory<SgaPOJO,Double>("CurrYearReq"));
        colCyApp.setCellValueFactory(new PropertyValueFactory<SgaPOJO,Double>("CurrYearApp"));
        
        Connection connect = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connect = DriverManager.getConnection(DatabaseInfo.DB_URL);
            
            Statement stmt = connect.createStatement();
            
            stmt.executeUpdate("DROP VIEW IF EXISTS CurrentYear");
            stmt.executeUpdate("DROP VIEW IF EXISTS PreviousYear");
            stmt.executeUpdate("DROP VIEW IF EXISTS RetroactiveYears");
            stmt.executeUpdate("DROP VIEW IF EXISTS ProactiveYears");
            stmt.executeUpdate("DROP VIEW IF EXISTS SgaAllocationResults");
            stmt.executeUpdate("CREATE VIEW CurrentYear AS SELECT * FROM SgaAllocation WHERE Year="+year+";");
            
            ResultSet rset = stmt.executeQuery("SELECT * FROM CurrentYear;");
            
            boolean barrier = wantPrev;
            if(barrier) {
                ResultSet check = stmt.executeQuery("SELECT * FROM SgaAllocation WHERE Year="+(year-1)+";");
                barrier = barrier && check.next();
            }
            
            if(barrier) {
                stmt.executeUpdate("CREATE VIEW PreviousYear AS SELECT * FROM SgaAllocation WHERE Year="+(year-1)+";");
                stmt.executeUpdate("CREATE VIEW ProactiveYears AS"
                    + " SELECT CY.Name, CY.Category,"
                            + " PY.Requested AS PrevYearReq, PY.Approved AS PrevYearApp,"
                            + " CY.Requested AS CurrYearReq, CY.Approved AS CurrYearApp"
                        + " FROM CurrentYear AS CY LEFT OUTER JOIN PreviousYear AS PY"
                            + " ON CY.Name=PY.Name AND CY.Category=PY.Category;");
                stmt.executeUpdate("CREATE VIEW RetroactiveYears AS"
                        + " SELECT PY.Name, PY.Category,"
                                + " PY.Requested AS PrevYearReq, PY.Approved AS PrevYearApp,"
                                + " CY.Requested AS CurrYearReq, CY.Approved AS CurrYearApp"
                            + " FROM PreviousYear AS PY LEFT OUTER JOIN CurrentYear AS CY"
                                + " ON CY.Name=PY.Name AND CY.Category=PY.Category;");
                rset = stmt.executeQuery("SELECT * FROM ProactiveYears UNION SELECT * FROM RetroactiveYears;");
            } else {
                rset = stmt.executeQuery("SELECT * FROM CurrentYear;");
            }
            
            ObservableList<SgaPOJO> csvData = FXCollections.observableArrayList();
            while(rset.next()) {
                String name = rset.getString("Name");
                String category = rset.getString("Category");
                double prevYearReq = 0;
                double prevYearApp = 0;
                double currYearReq = 0;
                double currYearApp = 0;
                if(barrier) {
                    prevYearReq = rset.getInt("PrevYearReq");
                    prevYearApp = rset.getInt("PrevYearApp");
                    currYearReq = rset.getInt("CurrYearReq");
                    currYearApp = rset.getInt("CurrYearApp");
                } else {
                    currYearReq = rset.getInt("Requested");
                    currYearApp = rset.getInt("Approved");
                }
                csvData.add(new SgaPOJO(name, category, prevYearReq, prevYearApp, currYearReq, currYearApp));
            }
            sgaTable.setItems(csvData);
            
            connect.close();
        } catch (Exception e) {
            System.out.println(e);
            message.setText(e.getMessage() + " Please contact the database admin for help.");
        }
    }

}
