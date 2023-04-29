package Model;

public class Trade {
    public String resourceType;
    public int resourceAmount;
    public int price;
    public String message;
    public User toUser;
    public User fromUser;
    public boolean isAccepted;

    public Trade(String resourceType , int resourceAmount , int price , String message , User toUser , User fromUser) {
        this.resourceType = resourceType;
        this.resourceAmount = resourceAmount;
        this.price = price;
        this.message = message;
        this.toUser = toUser;
        this.fromUser = fromUser;
        this.isAccepted = false;
    }
}
