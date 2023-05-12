package Controller;

import Model.Trade;

import static Controller.Controller.currentUser;
import static Model.Game.currentGovernment;
import static View.InputOutput.output;

public class TradeMenuController {
    public static void addTrade(Trade trade) {
        if (trade.toGovernment == null) {
            output("The destination government was not found");
        }
        trade.toGovernment.addTrade(trade);
        trade.fromGovernment.addTrade(trade);
    }

    public static void showList() {
        int id = 0;
        for (Trade trade : currentGovernment.tradeList) {
            if(!trade.isAccepted) {
                System.out.println("ID: " + id + ")" + " Resource Type: " + trade.resourceType + " Resource Amount: " + trade.resourceAmount +
                        " Price: " + trade.price + "\n" + "Message: " + trade.message + " Is from:" + trade.fromGovernment.getUser().getUsername() + "\n");
            }
            id++;
        }
    }

    public static void acceptTrade(int id) {
        if(id < 0 || id >= currentGovernment.tradeList.size()) {
            System.out.println("Invalid Trade ID!");
            return;
        }
        Trade trade = currentGovernment.tradeList.get(id);
        trade.isAccepted = true;
    }

    public static void showHistory() {
        int id = 0;
        for (Trade trade : currentGovernment.tradeList) {
            if(trade.fromGovernment.equals(currentGovernment) || trade.isAccepted) {
                System.out.println("ID: " + id + ")" + " Resource Type: " + trade.resourceType + " Resource Amount: " + trade.resourceAmount +
                        " Price: " + trade.price + "\n" + "Message: " + trade.message + " Is from:" + trade.fromGovernment.getUser().getUsername());
            }
            id++;
        }
    }

    public static void showNotification() {
        currentGovernment.lastTradeIndex++;
        while(currentGovernment.lastTradeIndex < currentGovernment.tradeList.size()) {
            Trade trade = currentGovernment.tradeList.get(currentGovernment.lastTradeIndex);
            System.out.println("ID: " + currentGovernment.lastTradeIndex + ")" + " Resource Type: " + trade.resourceType + " Resource Amount: " + trade.resourceAmount +
                    " Price: " + trade.price + "\n" + "Message: " + trade.message + " Is from:" + trade.fromGovernment.getUser().getUsername());
            currentGovernment.lastTradeIndex++;
        }
    }
}
