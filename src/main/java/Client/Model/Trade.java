package Client.Model;

import Client.Model.Resources.Resource;

import java.util.ArrayList;

public class Trade {
    public String resourceType;
    public int resourceAmount;
    public int price;
    public String message;
    public Government toGovernment;
    public Government fromGovernment;
    public boolean isAccepted;

    public boolean isDonation;
    public boolean isRequest;

    public void setDonation(boolean donation) {
        isDonation = donation;
    }

    public void setRequest(boolean request) {
        isRequest = request;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Resource> resources = new ArrayList<>();

    public Trade(String resourceType , int resourceAmount , int price , String message , Government to , Government from) {
        this.resourceType = resourceType;
        this.resourceAmount = resourceAmount;
        this.price = price;
        this.message = message;
        this.toGovernment = to;
        this.fromGovernment = from;
        this.isAccepted = false;
    }

    public Trade(ArrayList<Resource> resources, Government fromGovernment, Government toGovernment){
        this.resources = resources;
        this.fromGovernment = fromGovernment;
        this.toGovernment = toGovernment;
        this.isAccepted = false;
    }
}
