package src.code.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginValidator {
    
    public final static String DB_URL = "db\\YJFCBudgetTool.db";
    public final static String INVALID_TAG = "INVALID";
    
    public static String validateLogin(String username, String password) {
        Connection connect = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connect = DriverManager.getConnection("jdbc:sqlite:"+DB_URL);
            
            Statement stmt = connect.createStatement();
            String strSelect = "SELECT UserType FROM Users WHERE GTID='"+username+"' AND Password='"+password+"';";
            ResultSet rset = stmt.executeQuery(strSelect);
            if(rset.next()) {
                return rset.getString("UserType");
            }
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        return INVALID_TAG;
    }
}
