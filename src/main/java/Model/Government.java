package Model;


import java.util.ArrayList;

public class Government {
    private int population;
    private int popularity;
    private int foodRate;
    private int foodStorage;
    private int taxRate;
    private int fearRate;
    private int woodInventory;
    private int stoneInventory;
    private int ironInventory;
    private int gold;
    private int pitch;
    private User user;
    private ArrayList<String> TypesOfFood = new ArrayList<>();
    private ArrayList<Building> buildings = new ArrayList<>();
    private ArrayList<Person> people = new ArrayList<>();
    //private ArrayList<PatrollingUnit> patrollingUnits = new ArrayList<>();

    public int getWoodInventory() {
        return woodInventory;
    }

    public void setWoodInventory(int woodInventory) {
        this.woodInventory = woodInventory;
    }

    public int getStoneInventory() {
        return stoneInventory;
    }

    public void setStoneInventory(int stoneInventory) {
        this.stoneInventory = stoneInventory;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getIronInventory() {
        return ironInventory;
    }

    public void setIronInventory(int ironInventory) {
        this.ironInventory = ironInventory;
    }

    public int getPitch() {
        return pitch;
    }

    public void setPitch(int pitch) {
        this.pitch = pitch;
    }

    public Government (User user) {
        this.population = 0;
        this.foodRate = -2;
        this.foodStorage = 0;
        this.taxRate = 0;
        this.fearRate = 0;
        this.woodInventory = 0;
        this.stoneInventory = 0;
        this.ironInventory = 0;
        this.gold = 0;
        this.pitch = 0;
        this.user = user;
    }

    public int getPopulation() {
        return population;
    }

    public int getPopularity() {
        return popularity;
    }

    public int getFoodRate() {
        return foodRate;
    }

    public int getFoodStorage() {
        return foodStorage;
    }

    public ArrayList<String> getTypesOfFood() {
        return TypesOfFood;
    }

    public int getTaxRate() {
        return taxRate;
    }

    public int getFearRate() {
        return fearRate;
    }

    public User getUser() {
        return user;
    }

    public ArrayList<Building> getBuildings() {
        return buildings;
    }

    public ArrayList<Person> getPeople() {
        return people;
    }
    public void calculatePopularity() {

    }

//    public ArrayList<PatrollingUnit> getPatrollingUnits() {
//        return patrollingUnits;
//    }
//
//    public void setPatrollingUnits(ArrayList<PatrollingUnit> patrollingUnits) {
//        this.patrollingUnits = patrollingUnits;
//    }
    public void runPatrols() {}
}
