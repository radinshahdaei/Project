package Controller;

import Model.Commodity;
import Model.Store;

import static Controller.Controller.currentUser;
import static Model.Game.currentGovernment;

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
        int price = commodity.buyPrice * amount;
        if(price > currentGovernment.coins) {
            System.out.println("Not enough coins!");
            return ;
        }
        RegisterMenuController.captcha();
        currentGovernment.coins -= price;
        currentGovernment.addResource(commodity.resourceType , amount);
    }

    public static void sell(String name , int amount) {
        Commodity commodity = Store.getCommodityByName(name);
        if(currentGovernment.getResource(commodity.resourceType).getCount() < amount) {
            System.out.println("Not enough resource!");
            return ;
        }
        RegisterMenuController.captcha();
        int price = commodity.sellPrice * amount;
        currentGovernment.coins += price;
        currentGovernment.removeResource(commodity.resourceType , amount);
    }
}
