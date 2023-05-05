package View.Game;

import Controller.StoreMenuController;
import Controller.TradeMenuController;
import Model.Trade;
import View.Commands.StoreMenuCommands;
import View.Commands.TradeMenuCommands;

import java.util.regex.Matcher;

import static Controller.Controller.*;
import static View.InputOutput.input;

public class StoreMenu {

    public void run() {
        String command;
        Matcher matcher;
        while(true) {
            command = input();
            if(command.matches("\\s*show\\s+price\\s+list\\s*")) {
                StoreMenuController.showCommodities();
            }
            else if((matcher = StoreMenuCommands.getMatcher(command , StoreMenuCommands.BUY)) != null) {
                String name = matcher.group("name");
                int amount  = Integer.parseInt(matcher.group("amount"));
                StoreMenuController.buy(name , amount);
            }
            else if((matcher = StoreMenuCommands.getMatcher(command , StoreMenuCommands.SELL)) != null) {
                String name = matcher.group("name");
                int amount  = Integer.parseInt(matcher.group("amount"));
                StoreMenuController.sell(name , amount);
            }
        }
    }
}
