package Model;

import Model.Resources.ResourceType;

public class Commodity {
    public int sellPrice;
    public int buyPrice;

    public ResourceType resourceType;

    public Commodity(int sellPrice , int buyPrice , ResourceType resourceType) {
        this.sellPrice = sellPrice;
        this.buyPrice = buyPrice;
        this.resourceType = resourceType;
    }
}
