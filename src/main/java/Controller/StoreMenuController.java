package Controller;

import Model.Building.Building;
import Model.Building.Factory.Factory;
import Model.Building.Storage.Storage;
import Model.Commodity;
import Model.Resources.Resource;
import Model.Resources.ResourceType;
import Model.Store;

import static Controller.Controller.currentUser;
import static Model.Game.currentGovernment;
import static View.InputOutput.output;

public class StoreMenuController {
    public static void showCommodities() {
        int id = 0;
        for (Commodity commodity : Store.commodities) {
            System.out.println("ID: " + id + "Resource type: " + commodity.resourceType.name + " Sell price: " + commodity.sellPrice + " Buy price: " + commodity.buyPrice
            + "Our storage: " + currentGovernment.getResource(commodity.resourceType).getCount());
            id++;
        }
    }

    public static void buy(String name , int amount) {
        Commodity commodity = Store.getCommodityByName(name);
        if (commodity == null) {
            output("there is no such commodity");
            return;
        }
        int price = commodity.buyPrice * amount;
        if(price > currentGovernment.getResource(ResourceType.GOLD).getCount()) {
            System.out.println("Not enough gold!");
            return ;
        }
        RegisterMenuController.captcha();
        String storageName = Factory.getStorageName(commodity.resourceType.resourceModel);
        for (Building building:currentGovernment.getBuildings()) {
            if (building.getName().equals(storageName)) {
                ((Storage) building).addToStorage(Resource.createResource(commodity.resourceType, amount));
                break;
            }
        }
        for (Building building:currentGovernment.getBuildings()) {
            if (building.getName().equals("stockpile")) {
                ((Storage) building).removeFromStorage(Resource.createResource(ResourceType.GOLD, price));
                break;
            }
        }
    }

    public static void sell(String name , int amount) {
        Commodity commodity = Store.getCommodityByName(name);
        if (commodity == null) {
            output("there is no such commodity");
            return;
        }
        if(currentGovernment.getResource(commodity.resourceType).getCount() < amount) {
            System.out.println("Not enough resource!");
            return ;
        }
        RegisterMenuController.captcha();
        int price = commodity.sellPrice * amount;
        String storageName = Factory.getStorageName(commodity.resourceType.resourceModel);
        for (Building building:currentGovernment.getBuildings()) {
            if (building.getName().equals(storageName)) {
                ((Storage) building).removeFromStorage(Resource.createResource(commodity.resourceType, amount));
                break;
            }
        }
        for (Building building:currentGovernment.getBuildings()) {
            if (building.getName().equals("stockpile")) {
                ((Storage) building).addToStorage(Resource.createResource(ResourceType.GOLD, price));
                break;
            }
        }
    }
}
