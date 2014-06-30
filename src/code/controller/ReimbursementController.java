package src.code.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import src.code.model.DatabaseInfo;
import src.code.model.ReimbPOJO;

public class ReimbursementController extends AbstractController {
    
    @FXML
    private TableView<ReimbPOJO> reimbTable;
    
    @FXML
    private Text message;
    
    @FXML
    private TableColumn<ReimbPOJO, String> colName;
    @FXML
    private TableColumn<ReimbPOJO, String> colCat;
    @FXML
    private TableColumn<ReimbPOJO, Double> colAmt;
    @FXML
    private TableColumn<ReimbPOJO, String> colReq;

    @Override
    protected void submit(ActionEvent event) {
        ReimbPOJO person = (ReimbPOJO)reimbTable.getSelectionModel().getSelectedItem();
        System.out.println(person.getName());
        
        Connection connect = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connect = DriverManager.getConnection(DatabaseInfo.DB_URL);
            Statement stmt = connect.createStatement();
            
            String foo = "UPDATE Reimbursement SET DateRec=\""+DatabaseInfo.getTimeStamp()+"\""
                            + " WHERE Name=\""+person.getName()+"\""
                            + " AND Category=\""+person.getCategory()+"\""
                            + " AND Year="+DatabaseInfo.getCurrentYear()+";";
            stmt.executeUpdate(foo);
            
            connect.close();
        } catch (Exception e) {
            System.out.println(e);
            message.setText(e.getMessage() + " Please contact the database admin for help.");
        }
    }

    @Override
    protected void goBack(ActionEvent event) {
        transition(event, "menu");
    }

    @Override
    protected void populate(ActionEvent event) {
        colName.setCellValueFactory(new PropertyValueFactory<ReimbPOJO, String>("Name"));
        colCat.setCellValueFactory(new PropertyValueFactory<ReimbPOJO, String>("Category"));
        colAmt.setCellValueFactory(new PropertyValueFactory<ReimbPOJO, Double>("Amount"));
        colReq.setCellValueFactory(new PropertyValueFactory<ReimbPOJO, String>("DateRequested"));
        
        Connection connect = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connect = DriverManager.getConnection(DatabaseInfo.DB_URL);
            
            Statement stmt = connect.createStatement();
            
            String foo = "SELECT Name, Category, Amt, DateReq FROM Reimbursement"
                            + " WHERE DateRec IS NULL AND Year="+DatabaseInfo.getCurrentYear()
                            + " ORDER BY DateReq ASC, Category ASC, Name ASC;";
            
            ResultSet rset = stmt.executeQuery(foo);
            
            ObservableList<ReimbPOJO> csvData = FXCollections.observableArrayList();
            while(rset.next()) {
                String name = rset.getString("Name");
                String category = rset.getString("Category");
                double amount = rset.getDouble("Amt");
                String dateReq = rset.getString("DateReq");
                csvData.add(new ReimbPOJO(name, category, amount, dateReq));
            }
            reimbTable.setItems(csvData);
            
            connect.close();
        } catch (Exception e) {
            System.out.println(e);
            message.setText(e.getMessage() + " Please contact the database admin for help.");
        }
    }

}
