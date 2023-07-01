package Server.Model;

import Client.Model.Building.Storage.Storage;
import Client.Model.Commodity;
import Client.Model.Game;
import Client.Model.Resources.ResourceModel;
import Client.Model.Resources.ResourceType;

import java.util.ArrayList;

public class Store {
    public static ArrayList<Commodity> commodities;

    public static void initializeCommodities() {
        commodities = new ArrayList<>();
        for (ResourceType resourceType : ResourceType.values()) {
            if (!(resourceType.equals(ResourceType.GOLD) || resourceType.equals(ResourceType.HORSE) || resourceType.equals(ResourceType.COW))) {
                Commodity commodity = new Commodity(2 , 4 , resourceType);
                setStock(commodity);
                commodities.add(commodity);
            }
        }
    }

    public static void setStock(Commodity commodity){
        if (Client.Model.Game.currentGovernment == null) return;
        if (commodity.resourceType.resourceModel.equals(ResourceModel.FOOD)) {
            Storage granary = (Storage) Client.Model.Game.currentGovernment.findBuildingByName("granary");
            if (granary == null) return;
            commodity.setStock(granary.getStoredResourceByType(commodity.resourceType).getCount());
        } else if (commodity.resourceType.resourceModel.equals(ResourceModel.WEAPON)) {
            Storage armoury = (Storage) Client.Model.Game.currentGovernment.findBuildingByName("armoury");
            if (armoury == null) return;
            commodity.setStock(armoury.getStoredResourceByType(commodity.resourceType).getCount());
        } else if (commodity.resourceType.resourceModel.equals(ResourceModel.OTHER)) {
            Storage stockpile = (Storage) Game.currentGovernment.findBuildingByName("stockpile");
            if (stockpile == null) return;
            commodity.setStock(stockpile.getStoredResourceByType(commodity.resourceType).getCount());
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
