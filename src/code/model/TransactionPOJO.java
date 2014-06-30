package src.code.model;

public class TransactionPOJO {
    
    private String date;
    private String name;
    private String category;
    private int year;
    private int GTID;
    private double cost;
    private double earned;
    private String explanation;
    
    public TransactionPOJO(String date, String name, String category, int year,
            int GTID, double cost, double earned, String explanation) {
        this.date = date;
        this.name = name;
        this.category = category;
        this.year = year;
        this.GTID = GTID;
        this.cost = cost;
        this.earned = earned;
        this.explanation = explanation;
    }

    public String getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public int getYear() {
        return year;
    }

    public int getGTID() {
        return GTID;
    }

    public double getCost() {
        return cost;
    }

    public double getEarned() {
        return earned;
    }

    public String getExplanation() {
        return explanation;
    }

}
