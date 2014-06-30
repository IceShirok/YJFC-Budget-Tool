package src.code.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DatabaseInfo {

    public final static String DB_URL = "jdbc:sqlite:db\\YJFCBudgetTool.db";
    public final static String INVALID_TAG = "INVALID";
    public static int getCurrentYear() {
        return 14;
    }
    
    private final static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    public static String getTimeStamp() {
        return dateFormat.format(new Date()).toString();
    }
    
    public static String getSundayDate() {
        Calendar cal = Calendar.getInstance();
        while (cal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
            cal.add(Calendar.DAY_OF_WEEK, -1);
        }
        return dateFormat.format(cal.getTime()).toString();
    }

}
