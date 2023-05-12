package View.Game;

import Controller.GovernmentMenuController;
import View.Commands.GovernmentMenuCommands;
import View.Commands.GovernmentMenuCommands;

import java.util.regex.Matcher;

import static View.InputOutput.input;
import static View.InputOutput.output;

public class GovernmentMenu {

    public void run() {
        String command;
        Matcher matcher;
        while(true) {
            command = input();
            if (command.matches("\\s*back\\s*")) {
                output("Government menu exited manually!");
                return;
            }
            else if(command.matches("\\s*show\\s+popularity\\s+factors\\s*")) {
                GovernmentMenuController.showPopularityFactors();
            }
            else if(command.matches("\\s*show\\s+popularity\\s*")) {
                GovernmentMenuController.showPopularity();
            }
            else if(command.matches("\\s*show\\s+food\\s+list\\s*")) {
                GovernmentMenuController.showFoodList();
            }
            else if((matcher = GovernmentMenuCommands.getMatcher(command , GovernmentMenuCommands.FOODRATE)) != null) {
                int rate = Integer.parseInt(matcher.group("rate"));
                GovernmentMenuController.setFoodRate(rate);
            }
            else if(command.matches("\\s*food\\s+rate\\s+show\\s*")) {
                GovernmentMenuController.showFoodRate();
            }
            else if((matcher = GovernmentMenuCommands.getMatcher(command , GovernmentMenuCommands.TAXRATE)) != null) {
                int rate = Integer.parseInt(matcher.group("rate"));
                GovernmentMenuController.setTaxRate(rate);
            }
            else if(command.matches("\\s*tax\\s+rate\\s+show\\s*")) {
                GovernmentMenuController.showTaxRate();
            }
            else if((matcher = GovernmentMenuCommands.getMatcher(command , GovernmentMenuCommands.FEARRATE)) != null) {
                int rate = Integer.parseInt(matcher.group("rate"));
                GovernmentMenuController.setFearRate(rate);
            }
            else {
                output("Invalid command!");
            }
        }
    }
}
