package Server.Model;

import Client.Model.Resources.ResourceType;

public class Commodity {
    public int sellPrice;
    public int buyPrice;
    public int stock;

    public ResourceType resourceType;

    public Commodity(int sellPrice , int buyPrice , ResourceType resourceType) {
        this.sellPrice = sellPrice;
        this.buyPrice = buyPrice;
        this.resourceType = resourceType;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
