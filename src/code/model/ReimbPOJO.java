package src.code.model;

public class ReimbPOJO {
    
    private String name;
    private String category;
    private double amount;
    private String dateRequested;

    public ReimbPOJO(String name, String category, double amount, String dateRequested) {
        this.name = name;
        this.category = category;
        this.amount = amount;
        this.dateRequested = dateRequested;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }

    public String getDateRequested() {
        return dateRequested;
    }

}
