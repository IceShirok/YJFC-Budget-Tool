package src.code.model;

public class SgaPOJO {
    
    private String name;
    private String category;
    private double prevYearReq;
    private double prevYearApp;
    private double currYearReq;
    private double currYearApp;
    
    public SgaPOJO(String name, String category, double prevYearReq, double prevYearApp,
                        double currYearReq, double currYearApp) {
        this.name = name;
        this.category = category;
        this.prevYearReq = prevYearReq;
        this.prevYearApp = prevYearApp;
        this.currYearReq = currYearReq;
        this.currYearApp = currYearApp;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public double getPrevYearReq() {
        return prevYearReq;
    }

    public double getPrevYearApp() {
        return prevYearApp;
    }

    public double getCurrYearReq() {
        return currYearReq;
    }

    public double getCurrYearApp() {
        return currYearApp;
    }

}
