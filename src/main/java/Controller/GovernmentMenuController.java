package Controller;

import Model.Government;
import Model.Resources.Resource;
import Model.Resources.ResourceModel;
import Model.Resources.ResourceType;
import Model.User;
import View.Game.GovernmentMenu;

import static Model.Game.currentGovernment;

public class GovernmentMenuController {

    public static void showPopularityFactors() {
        System.out.println("Food: " + currentGovernment.getFoodEffect() + " Tax: " + currentGovernment.getTaxEffect()
        + " Fear: " + currentGovernment.getFearEffect() + " Religion: " + currentGovernment.getReligionEffect());
    }
    public static void showPopularity() {
        System.out.println(currentGovernment.getPopularity());
    }
    public static void showFoodList() {
        int id = 0;
        for (Resource resource : currentGovernment.resources) {
            if(resource.getResourceType().resourceModel.equals(ResourceModel.FOOD)) {
                System.out.println(id + ") " + resource.getResourceType().name + " " + resource.getCount());
                id++;
            }
        }
    }

    public static boolean checkFoodRate(int rate) {
        int valuePerPerson = rate + 2;
        int totalFoodNeeded = (currentGovernment.getPopulation() * valuePerPerson + 1) / 2;
        return (totalFoodNeeded <= currentGovernment.getFoodCount());
    }
    public static void setFoodRate(int rate) {
        if(!checkFoodRate(rate)) {
            System.out.println("Not enough food!");
            return;
        }
        currentGovernment.setFoodRate(rate);
    }
    public static void showFoodRate() {
        System.out.println(currentGovernment.getFoodRate());
    }

    public static boolean checkTaxRate(int rate) {
        if(rate >= 0) return true;
        int valuePerPerson = 10 - (rate + 3) * 2;
        int totalGoldNeeded = (valuePerPerson * currentGovernment.getPopulation() + 9) / 10;
        return (totalGoldNeeded <= currentGovernment.getResource(ResourceType.GOLD).getCount());
    }
    public static void setTaxRate(int rate) {
        if(!checkTaxRate(rate)) {
            System.out.println("Not enough gold!");
            return;
        }
        currentGovernment.setTaxRate(rate);
    }
    public static void showTaxRate() {
        System.out.println(currentGovernment.getTaxRate());
    }
    public static void setFearRate(int rate) {
        currentGovernment.setFearRate(rate);
    }

    public static Government getGovernmentByUser(User user) {
        for (Government government : GameMenuController.game.getGovernments()) {
            if(government.getUser().equals(user)) {
                return government;
            }
        }
        return null;
    }

    public static Government getCurrentGovernment() {
        return currentGovernment;
    }
}
