package View.Game;

import Controller.Controller;
import Controller.GovernmentMenuController;
import Controller.TradeMenuController;
import Model.Trade;
import View.Commands.TradeMenuCommands;

import java.util.regex.Matcher;

import static View.InputOutput.input;
import static View.InputOutput.output;

public class TradeMenu {

    public void run() {
        String command;
        Matcher matcher;
        while (true) {
            TradeMenuController.showNotification();
            command = input();
            if (command.matches("\\s*show\\s+related\\s+commands\\s*")) {
                output("back\ntrade -t <resourceType> -a <resourceAmount> -p <price> -m <message> -u <username>\ntrade list\ntrade accept -i <id> -m <message>\ntrade history");
            } else if (command.matches("\\s*back\\s*")) {
                output("Trade menu exited manually!");
            } else if ((matcher = TradeMenuCommands.getMatcher(command, TradeMenuCommands.TRADE)) != null) {

                if (command.matches("\\s*show\\s+related\\s+commands\\s*")) {
                    output("back\ntrade -t <resourceType> -a <resourceAmount> -p <price> -m <message> -u <username>\ntrade list\ntrade accept -i <id> -m <message>\ntrade history");
                } else if (command.matches("\\s*back\\s*")) {
                    output("Trade menu exited manually!");
                    return;
                } else if ((matcher = TradeMenuCommands.getMatcher(command, TradeMenuCommands.TRADE)) != null) {
                    String resourceType = Controller.removeDoubleQuote(matcher.group("resourceType"));
                    int resourceAmount = Integer.parseInt(Controller.removeDoubleQuote(matcher.group("resourceAmount")));
                    int price = Integer.parseInt(Controller.removeDoubleQuote(matcher.group("price")));
                    String message = Controller.removeDoubleQuote(matcher.group("message"));
                    String toUsername = Controller.removeDoubleQuote(matcher.group("username"));

                    Trade current = new Trade(resourceType, resourceAmount, price, message,
                            GovernmentMenuController.getGovernmentByUser(Controller.findUserByUsername(toUsername)), GovernmentMenuController.getCurrentGovernment());
                    TradeMenuController.addTrade(current);
                } else if ((matcher = TradeMenuCommands.getMatcher(command, TradeMenuCommands.LIST)) != null) {
                    TradeMenuController.showList();
                } else if ((matcher = TradeMenuCommands.getMatcher(command, TradeMenuCommands.ACCEPT)) != null) {
                    int id = Integer.parseInt(Controller.removeDoubleQuote(matcher.group("id")));
                    String message = Controller.removeDoubleQuote(matcher.group("message"));
                    TradeMenuController.acceptTrade(id);
                } else if ((matcher = TradeMenuCommands.getMatcher(command, TradeMenuCommands.HISTORY)) != null) {
                    TradeMenuController.showHistory();
                } else {
                    output("Invalid command!");
                }
            }
        }
    }
}
