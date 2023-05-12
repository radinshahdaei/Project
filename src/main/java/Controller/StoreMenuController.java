package Controller;

import Model.Commodity;
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
        currentGovernment.getResource(ResourceType.GOLD).addCount(-price);
        currentGovernment.addResource(commodity.resourceType , amount);
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
        currentGovernment.getResource(ResourceType.GOLD).addCount(price);
        currentGovernment.removeResource(commodity.resourceType , amount);
    }
}
