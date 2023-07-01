package Server.View.Game;

import Client.View.Commands.GovernmentMenuCommands;
import Client.Controller.GovernmentMenuController;

import java.util.regex.Matcher;

import static Client.View.InputOutput.input;
import static Client.View.InputOutput.output;

public class GovernmentMenu {

    public void run() {
        String command;
        Matcher matcher;
        while (true) {
            command = input();
            if (command.matches("\\s*show\\s+related\\s+commands\\s*")) {
                output("back\nshow popularity factors\nshow popularity\nshow food list\nfood rate -r <rate>\nfood rate show\ntax rate -r <rate>\ntax rate show\nfear rate -r <rate>");
            } else if (command.matches("\\s*back\\s*")) {
                return;
            } else if (command.matches("\\s*show\\s+popularity\\s+factors\\s*")) {
                GovernmentMenuController.showPopularityFactors();
            } else if (command.matches("\\s*show\\s+popularity\\s*")) {
                GovernmentMenuController.showPopularity();
            } else if (command.matches("\\s*show\\s+food\\s+list\\s*")) {
                GovernmentMenuController.showFoodList();
            } else if ((matcher = GovernmentMenuCommands.getMatcher(command, GovernmentMenuCommands.FOODRATE)) != null) {
                int rate = Integer.parseInt(matcher.group("rate"));
                GovernmentMenuController.setFoodRate(rate);
            } else if (command.matches("\\s*food\\s+rate\\s+show\\s*")) {
                GovernmentMenuController.showFoodRate();
            } else if ((matcher = GovernmentMenuCommands.getMatcher(command, GovernmentMenuCommands.TAXRATE)) != null) {
                int rate = Integer.parseInt(matcher.group("rate"));
                GovernmentMenuController.setTaxRate(rate);
            } else if (command.matches("\\s*tax\\s+rate\\s+show\\s*")) {
                GovernmentMenuController.showTaxRate();
            } else if ((matcher = GovernmentMenuCommands.getMatcher(command, GovernmentMenuCommands.FEARRATE)) != null) {
                int rate = Integer.parseInt(matcher.group("rate"));
                GovernmentMenuController.setFearRate(rate);
            } else {
                output("Invalid command!");
            }
        }
    }
}
