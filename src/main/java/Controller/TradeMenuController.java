package Controller;

import Model.Trade;

import static Controller.Controller.currentUser;

public class TradeMenuController {
    public static void addTrade(Trade trade) {
        trade.toUser.addTrade(trade);
        trade.fromUser.addTrade(trade);
    }

    public static void showList() {
        int id = 0;
        for (Trade trade : currentUser.tradeList) {
            if(!trade.isAccepted) {
                System.out.println("ID: " + id + ")" + " Resource Type: " + trade.resourceType + " Resource Amount: " + trade.resourceAmount +
                        " Price: " + trade.price + "\n" + "Message: " + trade.message + " Is from:" + trade.fromUser.getUsername() + "\n");
            }
            id++;
        }
    }

    public static void acceptTrade(int id) {
        if(id < 0 || id >= currentUser.tradeList.size()) {
            System.out.println("Invalid Trade ID!");
            return;
        }
        Trade trade = currentUser.tradeList.get(id);
        trade.isAccepted = true;
    }

    public static void showHistory() {
        int id = 0;
        for (Trade trade : currentUser.tradeList) {
            if(trade.fromUser.equals(currentUser) || trade.isAccepted) {
                System.out.println("ID: " + id + ")" + " Resource Type: " + trade.resourceType + " Resource Amount: " + trade.resourceAmount +
                        " Price: " + trade.price + "\n" + "Message: " + trade.message + " Is from:" + trade.fromUser.getUsername() + "\n");
            }
            id++;
        }
    }

    public static void showNotification() {
        currentUser.lastTradeIndex++;
        while(currentUser.lastTradeIndex < currentUser.tradeList.size()) {
            Trade trade = currentUser.tradeList.get(currentUser.lastTradeIndex);
            System.out.println("ID: " + currentUser.lastTradeIndex + ")" + " Resource Type: " + trade.resourceType + " Resource Amount: " + trade.resourceAmount +
                    " Price: " + trade.price + "\n" + "Message: " + trade.message + " Is from:" + trade.fromUser.getUsername() + "\n");
            currentUser.lastTradeIndex++;
        }
    }
}
