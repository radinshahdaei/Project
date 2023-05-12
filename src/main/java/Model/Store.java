package Model;

import Model.Resources.ResourceType;

import java.util.ArrayList;

public class Store {
    public static ArrayList<Commodity> commodities;

    public static void initializeCommodities() {
        commodities = new ArrayList<>();
        for (ResourceType resourceType : ResourceType.values()) {
            Commodity commodity = new Commodity(1 , 1 , resourceType);
            commodities.add(commodity);
        }
    }

    public static Commodity getCommodityByName(String name) {
        for (Commodity commodity : commodities) {
            if(commodity.resourceType.name.equalsIgnoreCase(name)) {
                return commodity;
            }
        }
        return null;
    }

    public static void changeSellPrice(String name , int amount) {
        for (Commodity commodity : commodities) {
            if(commodity.resourceType.name.equalsIgnoreCase(name)) {
                commodity.sellPrice = amount;
                return ;
            }
        }
    }

    public static void changeBuyPrice(String name , int amount) {
        for (Commodity commodity : commodities) {
            if(commodity.resourceType.name.equalsIgnoreCase(name)) {
                commodity.buyPrice = amount;
                return ;
            }
        }
    }
}
