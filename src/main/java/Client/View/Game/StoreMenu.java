package Client.View.Game;

import Client.View.Commands.StoreMenuCommands;
import Client.Controller.StoreMenuController;

import java.util.regex.Matcher;

import static Client.View.InputOutput.input;
import static Client.View.InputOutput.output;

public class StoreMenu {

    public void run() {
        String command;
        Matcher matcher;
        while(true) {
            command = input();
            if(command.matches("\\s*show\\s+related\\s+commands\\s*")) {
                output("back\nshow price list\nbuy -i <name> -a <amount>\nsell -i <name> -a <amount>");
            }
            else if(command.matches("\\s*back\\s*")) {
                output("Store menu exited manually!");
                return ;
            }
            else if(command.matches("\\s*show\\s+price\\s+list\\s*")) {
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
            else {
                output("Invalid command!");
            }
        }
    }
}
