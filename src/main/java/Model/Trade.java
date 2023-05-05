package Model;

public class Trade {
    public String resourceType;
    public int resourceAmount;
    public int price;
    public String message;
    public Government toGovernment;
    public Government fromGovernment;
    public boolean isAccepted;

    public Trade(String resourceType , int resourceAmount , int price , String message , Government to , Government from) {
        this.resourceType = resourceType;
        this.resourceAmount = resourceAmount;
        this.price = price;
        this.message = message;
        this.toGovernment = to;
        this.fromGovernment = from;
        this.isAccepted = false;
    }
}
