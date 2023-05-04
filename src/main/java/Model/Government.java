package Model;


import Model.Building.Building;
import Model.Resources.Resource;
import Model.Person.Person;

import java.util.ArrayList;

public class Government {
    private int population;
    private int popularity;
    private int foodRate;
    private int taxRate;
    private int fearRate;
    private User user;
    private ArrayList<Building> buildings = new ArrayList<>();
    private ArrayList<Person> people = new ArrayList<>();
    //private ArrayList<Resource> resources = new ArrayList<>();
    //private ArrayList<PatrollingUnit> patrollingUnits = new ArrayList<>();

    public Building findBuildingByName(String name) {
        for (Building building : buildings) {
            if (building.getName().equals(name)) {
                return building;
            }
        }
        return null;
    }


    public void calculatePopularity() {

    }

    public void runPatrols() {
    }

    public Government(User user) {
        this.population = population = 0;
        this.popularity = popularity = 0;
        this.foodRate = foodRate = -2;
        this.taxRate = taxRate = 0;
        this.fearRate = fearRate = 0;
        this.user = user;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public int getFoodRate() {
        return foodRate;
    }

    public void setFoodRate(int foodRate) {
        this.foodRate = foodRate;
    }

    public int getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(int taxRate) {
        this.taxRate = taxRate;
    }

    public int getFearRate() {
        return fearRate;
    }

    public void setFearRate(int fearRate) {
        this.fearRate = fearRate;
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

//    public ArrayList<Resource> getResources() {
//        return resources;
//    }

//    public void setResources(ArrayList<Resource> resources) {
//        this.resources = resources;
//    }
}
