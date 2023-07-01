package Controller;

import Model.Game;
import Model.Government;
import Model.User;
import View.InputOutput;
import Model.Resources.Resource;
import Model.Resources.ResourceModel;
import Model.Resources.ResourceType;

import static View.InputOutput.output;

public class GovernmentMenuController {

    public static void showPopularityFactors() {
        InputOutput.output("Food: " + Game.currentGovernment.getFoodEffect() + ", Tax: " + Game.currentGovernment.getTaxEffect()
                + ", Fear: " + Game.currentGovernment.getFearEffect() + ", Religion: " + Game.currentGovernment.getReligionEffect());
    }

    public static void showPopularity() {
        InputOutput.output(String.valueOf(Game.currentGovernment.getPopularity()));
    }

    public static void showFoodList() {
        int id = 1;
        for (Resource resource : Game.currentGovernment.resources) {
            if (resource.getResourceType().resourceModel.equals(ResourceModel.FOOD)) {
                InputOutput.output(id + ") " + resource.getResourceType().name + " " + resource.getCount());
                id++;
            }
        }
    }

    public static boolean checkFoodRate(int rate) {
        int valuePerPerson = rate + 2;
        int totalFoodNeeded = (Game.currentGovernment.getPopulation() * valuePerPerson + 1) / 2;
        return (totalFoodNeeded <= Game.currentGovernment.getFoodCount());
    }

    public static void setFoodRate(int rate) {
        if (!checkFoodRate(rate)) {
            InputOutput.output("Not enough food!", 'a');
            return;
        }
        Game.currentGovernment.setFoodRate(rate);
    }

    public static void showFoodRate() {
        InputOutput.output(String.valueOf(Game.currentGovernment.getFoodRate()));
    }


    public static boolean checkTaxRate(int rate) {
        if (rate >= 0) return true;
        int valuePerPerson = 10 - (rate + 3) * 2;
        int totalGoldNeeded = (valuePerPerson * Game.currentGovernment.getPopulation() + 9) / 10;
        return (totalGoldNeeded <= Game.currentGovernment.getResource(ResourceType.GOLD).getCount());
    }

    public static void setTaxRate(int rate) {
        if (!checkTaxRate(rate)) {
            InputOutput.output("Not enough gold!", 'a');
            return;
        }
        Game.currentGovernment.setTaxRate(rate);
    }

    public static void showTaxRate() {
        InputOutput.output(String.valueOf(Game.currentGovernment.getTaxRate()));
    }

    public static void setFearRate(int rate) {
        Game.currentGovernment.setFearRate(rate);
    }

    public static Government getGovernmentByUser(User user) {
        for (Government government : GameMenuController.game.getGovernments()) {
            if (government.getUser().equals(user)) {
                return government;
            }
        }
        return null;
    }

    public static Government getCurrentGovernment() {
        return Game.currentGovernment;
    }
}
