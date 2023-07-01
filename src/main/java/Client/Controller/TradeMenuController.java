package Client.Controller;

import Client.Model.Game;
import Client.Model.Trade;
import Client.View.InputOutput;
import Client.Model.Building.Storage.Storage;
import Client.Model.Resources.Resource;
import Client.Model.Resources.ResourceType;

import static Client.Model.Building.Factory.Factory.getStorageName;
import static Client.View.InputOutput.output;

public class TradeMenuController {
    public static void addTrade(Trade trade) {
        if (trade.toGovernment == null) {
            InputOutput.output("The destination government was not found");
        }
        trade.toGovernment.addTrade(trade);
        trade.fromGovernment.addTrade(trade);
    }

    public static String showList() {
        int id = 0;
        String list = "";
        for (Trade trade : Game.currentGovernment.tradeList) {
            if (!trade.isAccepted) {
                list += ("ID: " + id + ")" + " Resource Type: " + trade.resourceType + " Resource Amount: " + trade.resourceAmount +
                        " Price: " + trade.price + "\n" + "Message: " + trade.message + " Is from:" + trade.fromGovernment.getUser().getUsername() + "\n");
            }
            id++;
        }
        return list;
    }

    public static void acceptTrade(int id) {
        if (id < 0 || id >= Game.currentGovernment.tradeList.size()) {
            System.out.println("Invalid Trade ID!");
            return;
        }
        Trade trade = Game.currentGovernment.tradeList.get(id);
        trade.isAccepted = true;
        String resourceName = trade.resourceType;
        int resourceAmount = trade.resourceAmount;
        Resource resource = Resource.createResource(ResourceType.getResourceByName(resourceName), resourceAmount);
        String storageName = getStorageName(resource.getResourceType().resourceModel);
        Storage storageFrom = (Storage) trade.fromGovernment.findBuildingByName(storageName);
        Storage storageTo = (Storage) trade.toGovernment.findBuildingByName(storageName);
        if (storageFrom == null || storageTo == null) return;
        storageFrom.removeFromStorage(resource);
        storageTo.addToStorage(resource);
    }

    public static void showHistory() {
        int id = 0;
        for (Trade trade : Game.currentGovernment.tradeList) {
            if (trade.fromGovernment.equals(Game.currentGovernment) || trade.isAccepted) {
                System.out.println("ID: " + id + ")" + " Resource Type: " + trade.resourceType + " Resource Amount: " + trade.resourceAmount +
                        " Price: " + trade.price + "\n" + "Message: " + trade.message + " Is from:" + trade.fromGovernment.getUser().getUsername());
            }
            id++;
        }
    }

    public static void showNotification() {
        Game.currentGovernment.lastTradeIndex++;
        while (Game.currentGovernment.lastTradeIndex < Game.currentGovernment.tradeList.size()) {
            Trade trade = Game.currentGovernment.tradeList.get(Game.currentGovernment.lastTradeIndex);
            System.out.println("ID: " + Game.currentGovernment.lastTradeIndex + ")" + " Resource Type: " + trade.resourceType + " Resource Amount: " + trade.resourceAmount +
                    " Price: " + trade.price + "\n" + "Message: " + trade.message + " Is from:" + trade.fromGovernment.getUser().getUsername());
            Game.currentGovernment.lastTradeIndex++;
        }
    }
}
