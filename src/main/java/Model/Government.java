package Model;


import Model.Building.Building;
import Model.Person.Person;
import Model.Resources.Resource;
import Model.Resources.ResourceType;

import java.util.ArrayList;

public class Government {
    public static int[] taxRateEffect = {7, 5, 3, 1, -2, -4, -6, -8, -12, -16, -20, -24};
    public ArrayList<Resource> resources = new ArrayList<>();
    public int coins;
    public ArrayList<Trade> tradeList = new ArrayList<>();
    public int lastTradeIndex;
    private int religion;
    private int population;
    private int popularity;
    private int foodRate;
    private int taxRate;
    private int fearRate;
    private int innRate;
    private User user;
    private ArrayList<Building> buildings = new ArrayList<>();
    private ArrayList<Person> people = new ArrayList<>();

    //private ArrayList<PatrollingUnit> patrollingUnits = new ArrayList<>();
    public Government(User user) {
        this.population = population = 0;
        this.popularity = popularity = 0;
        this.foodRate = foodRate = -2;
        this.taxRate = taxRate = 0;
        this.religion = religion = 0;
        this.fearRate = fearRate = 0;
        this.user = user;
        this.coins = 0;
        this.lastTradeIndex = -1;
    }

    public void initializeResources() {
        for (ResourceType resourceType : ResourceType.values()) {
            Resource resource = new Resource(resourceType, 0);
            this.resources.add(resource);
        }
    }

    public Resource getResource(ResourceType resourceType) {
        for (Resource resource : this.resources) {
            if (resource.getResourceType().equals(resourceType))
                return resource;
        }
        return null;
    }

    public void addResource(ResourceType resourceType, int amount) {
        for (Resource resource : this.resources) {
            if (resource.getResourceType().equals(resourceType)) {
                resource.addCount(amount);
                return;
            }
        }
    }

    public void removeResource(ResourceType resourceType, int amount) {
        for (Resource resource : this.resources) {
            if (resource.getResourceType().equals(resourceType)) {
                resource.addCount(amount);
                return;
            }
        }
    }

    public Building findBuildingByName(String name) {
        for (Building building : buildings) {
            if (building.getName().equals(name)) {
                return building;
            }
        }
        return null;
    }

    public void runPatrols() {
    }

    public int getPopulation() {
        return this.population;
    }

    public void setPopulation(int population) {
        this.population = population;
        this.updatePopularity();
    }

    public int getPopularity() {
        this.updatePopularity();
        return this.popularity;
    }

    public int getFoodRate() {
        return this.foodRate;
    }

    public void setFoodRate(int foodRate) {
        this.foodRate = foodRate;
        this.updatePopularity();
    }

    public int getTaxRate() {
        return this.taxRate;
    }

    public void setTaxRate(int taxRate) {
        this.taxRate = taxRate;
        this.updatePopularity();
    }

    public int getFearRate() {
        return this.fearRate;
    }

    public void setFearRate(int fearRate) {
        this.fearRate = fearRate;
        this.updatePopularity();
    }

    public int getFoodEffect() {
        return (this.foodRate == -2 ? -8 : this.foodRate == -1 ? -4 : this.foodRate == 1 ? 4 : this.foodRate == 2 ? 8 : 0);
    }

    public int getTaxEffect() {
        return taxRateEffect[this.taxRate + 3];
    }

    public int getReligionEffect() {
        return this.religion * 2;
    }

    public int getFearEffect() {
        return this.fearRate * -1;
    }

    public int getInnEffect() {
        return this.innRate;
    }

    public void updatePopularity() {
        this.popularity += this.getFoodEffect() + this.getTaxEffect() + this.getReligionEffect() + this.getFearEffect();
    }

    public void addTrade(Trade trade) {
        this.tradeList.add(trade);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ArrayList<Building> getBuildings() {
        return buildings;
    }

    public void setBuildings(ArrayList<Building> buildings) {
        this.buildings = buildings;
    }

    public ArrayList<Person> getPeople() {
        return people;
    }

    public void setPeople(ArrayList<Person> people) {
        this.people = people;
    }

    public void addInnRate(int amount) {
        this.innRate += amount;
    }

    public void addChurch(int people) {
        int amount = people * 4 / this.population + 1;
        this.religion += amount;
        if (this.religion > 4) this.religion = 4;
    }
}
