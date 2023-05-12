package Model;


import Model.Building.Building;
import Model.Person.Military.MilitaryUnit;
import Model.Person.Military.Special.Engineer;
import Model.Person.Person;
import Model.Person.PersonType;
import Model.Resources.Resource;
import Model.Resources.ResourceModel;
import Model.Resources.ResourceType;

import java.util.ArrayList;

public class Government {
    public static int[] taxRateEffect = {7, 5, 3, 1, -2, -4, -6, -8, -12, -16, -20, -24};
    public ArrayList<Resource> resources = new ArrayList<>();
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
    private boolean isDead = false;

    //private ArrayList<PatrollingUnit> patrollingUnits = new ArrayList<>();
    public Government(User user) {
        this.population = population = 0;
        this.popularity = popularity = 0;
        this.foodRate = foodRate = -2;
        this.taxRate = taxRate = 0;
        this.religion = religion = 0;
        this.fearRate = fearRate = 0;
        this.user = user;
        this.lastTradeIndex = -1;
        this.initializeResources();
    }

    public void checkIfDead() {
        Building keep = findBuildingByName("keep");
        if (keep.getHp() != 0) return;
        for (Building building : buildings) {
            building.setHp(0);
        }
        for (Person person : people) {
            if (person instanceof MilitaryUnit) ((MilitaryUnit) person).setDefence(0);
        }
        isDead = true;
    }

    public void addPopulation(int count) {
        this.population += count;
        Person person;
        for (int i = 0; i < count; i++) {
            person = new Person(null, PersonType.PEASANT, this.getUser());
            people.add(person);
        }
    }

    public boolean isDead() {
        return isDead;
    }

    public ArrayList<Person> getEngineers() {
        ArrayList<Person> allEngineers = new ArrayList<>();
        for (Person person : people) {
            if (person instanceof Engineer && ((Engineer) person).isAvailable()) {
                allEngineers.add(person);
            }
        }
        return allEngineers;
    }

    public ArrayList<Person> getPeasants() {
        ArrayList<Person> allPeasants = new ArrayList<>();
        for (Person person : people) {
            if (person.getType().equals(PersonType.PEASANT)) allPeasants.add(person);
        }
        return allPeasants;
    }


    public Resource getResourceByType(ResourceType resourceType) {
        for (Resource resource : resources) {
            if (resourceType.equals(resource.getResourceType())) {
                return resource;
            }
        }
        return null;
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

    public ArrayList<Resource> getResourcesByModel(ResourceModel resourceModel) {
        ArrayList<Resource> result = new ArrayList<>();
        for (Resource resource : this.resources) {
            if (resource.getResourceType().resourceModel.equals(resourceModel)) {
                result.add(resource);
            }
        }
        //sort
        for (int i = 0; i < result.size(); ++i) {
            for (int j = i + 1; j < result.size(); ++j) {
                if (result.get(i).getCount() < result.get(j).getCount()) {
                    //swap
                    Resource tmp = result.get(j);
                    result.set(j, result.get(i));
                    result.set(i, tmp);
                }
            }
        }
        return result;
    }

    public int getFoodCount() {
        int counter = 0;
        for (Resource resource : this.resources) {
            if (resource.getResourceType().resourceModel.equals(ResourceModel.FOOD)) {
                counter += resource.getCount();
            }
        }
        return counter;
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
        this.population += population;
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
        this.popularity += this.getFoodEffect() + this.getTaxEffect() + this.getReligionEffect() + this.getFearEffect() + this.getInnEffect();
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
