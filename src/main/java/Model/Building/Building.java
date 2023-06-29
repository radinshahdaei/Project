package Model.Building;

import Model.Building.Barracks.Barracks;
import Model.Building.DeffensiveBuilding.DefensiveBuilding;
import Model.Building.Factory.Factory;
import Model.Building.Storage.Storage;
import Model.Building.WeaponMaker.WeaponMaker;
import Model.Game;
import Model.Resources.Resource;
import Model.Resources.ResourceType;
import Model.User;

import java.util.ArrayList;
import java.util.HashMap;

import static View.InputOutput.output;

public class Building {
    public static HashMap<String, BuildingType> ALL_BUILDINGS = new HashMap<>();

    static {
        ALL_BUILDINGS.put("lookout tower", BuildingType.DEFENSIVE_BUILDING);
        ALL_BUILDINGS.put("perimeter tower", BuildingType.DEFENSIVE_BUILDING);
        ALL_BUILDINGS.put("defence turret", BuildingType.DEFENSIVE_BUILDING);
        ALL_BUILDINGS.put("square tower", BuildingType.DEFENSIVE_BUILDING);
        ALL_BUILDINGS.put("round tower", BuildingType.DEFENSIVE_BUILDING);

        ALL_BUILDINGS.put("armoury", BuildingType.STORAGE);
        ALL_BUILDINGS.put("granary", BuildingType.STORAGE);
        ALL_BUILDINGS.put("stockpile", BuildingType.STORAGE);
        ALL_BUILDINGS.put("stable", BuildingType.STORAGE);

        ALL_BUILDINGS.put("iron mine", BuildingType.FACTORY);
        ALL_BUILDINGS.put("quarry", BuildingType.FACTORY);
        ALL_BUILDINGS.put("wood cutter", BuildingType.FACTORY);
        ALL_BUILDINGS.put("pitch rig", BuildingType.FACTORY);
        ALL_BUILDINGS.put("apple orchard", BuildingType.FACTORY);
        ALL_BUILDINGS.put("hops farmer", BuildingType.FACTORY);
        ALL_BUILDINGS.put("hunter post", BuildingType.FACTORY);
        ALL_BUILDINGS.put("diary farmer", BuildingType.FACTORY);
        ALL_BUILDINGS.put("wheat farmer", BuildingType.FACTORY);
        ALL_BUILDINGS.put("bakery", BuildingType.FACTORY);
        ALL_BUILDINGS.put("brewer", BuildingType.FACTORY);
        ALL_BUILDINGS.put("inn", BuildingType.FACTORY);

        ALL_BUILDINGS.put("armourer", BuildingType.WEAPON_MAKER);
        ALL_BUILDINGS.put("tanner", BuildingType.WEAPON_MAKER);
        ALL_BUILDINGS.put("fletcher", BuildingType.WEAPON_MAKER);
        ALL_BUILDINGS.put("poleturner", BuildingType.WEAPON_MAKER);
        ALL_BUILDINGS.put("blacksmith", BuildingType.WEAPON_MAKER);

        ALL_BUILDINGS.put("high wall", BuildingType.WALL);
        ALL_BUILDINGS.put("low wall", BuildingType.WALL);
        ALL_BUILDINGS.put("big stone gatehouse", BuildingType.WALL);
        ALL_BUILDINGS.put("small stone gatehouse", BuildingType.WALL);

        ALL_BUILDINGS.put("barracks", BuildingType.BARRACKS);
        ALL_BUILDINGS.put("mercenary post", BuildingType.BARRACKS);
        ALL_BUILDINGS.put("engineer guild", BuildingType.BARRACKS);
        ALL_BUILDINGS.put("tunneler guild", BuildingType.BARRACKS);

        ALL_BUILDINGS.put("keep", BuildingType.SPECIAL);
        ALL_BUILDINGS.put("hovel", BuildingType.SPECIAL);
        ALL_BUILDINGS.put("campfire", BuildingType.SPECIAL);
        ALL_BUILDINGS.put("church", BuildingType.SPECIAL);
        ALL_BUILDINGS.put("cathedral", BuildingType.SPECIAL);
        ALL_BUILDINGS.put("siege tent", BuildingType.SPECIAL);
        ALL_BUILDINGS.put("oil smelter", BuildingType.SPECIAL);
        
    }

    private String name;
    private int hp;
    private final int maxHP;
    private int x;
    private int y;
    private int workers;
    private ArrayList<Resource> price = new ArrayList<>();

    private String imageUrl;
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    private User owner;

    public Building(String name, int hp, int x, int y, int workers, ArrayList<Resource> price, User owner) {
        this.name = name;
        this.hp = hp;
        this.maxHP = hp;
        this.x = x;
        this.y = y;
        this.workers = workers;
        this.price = price;
        this.owner = owner;
        this.imageUrl = imageUrl(name);
    }

    public String imageUrl(String name){
        String buildingName = name.replaceAll("\\s+","").toLowerCase();
        return Game.class.getResource("/Images/Building/"+buildingName+".png").toString();
    }

    public static Building createBuildings(String name, int x, int y, BuildingType buildingType, User owner) {
        Building building = null;
        if (buildingType.equals(BuildingType.DEFENSIVE_BUILDING)) {
            building = DefensiveBuilding.createBuilding(name, x, y, owner);
        } else if (buildingType.equals(BuildingType.FACTORY)) {
            building = Factory.createBuilding(name, x, y, owner);
        } else if (buildingType.equals(BuildingType.STORAGE)) {
            building = Storage.createBuilding(name, x, y, owner);
        } else if (buildingType.equals(BuildingType.WEAPON_MAKER)) {
            building = WeaponMaker.createBuilding(name, x, y, owner);
        } else if (buildingType.equals(BuildingType.BARRACKS)) {
            building = Barracks.createBuilding(name, x, y, owner);
        } else if (buildingType.equals(BuildingType.WALL)) {
            building = Wall.createWall(name, x, y, owner);
        } else if (name.equals("keep")) {
            building = Keep.createBuilding(x, y, owner);
        } else if (name.equals("hovel")) {
            building = Hovel.createBuilding(x, y, owner);
        } else if (name.equals("campfire")) {
            building = Campfire.createBuilding(x, y, owner);
        } else if (name.equalsIgnoreCase("church") || name.equalsIgnoreCase("cathedral")) {
            building = Church.createBuilding(name, x, y, owner);
        } else if (name.equalsIgnoreCase("siege tent") || name.equalsIgnoreCase("oil smelter")) {
            building = EnginnerBuilding.createBuilding(name, x, y, owner);
        }
        return building;
    }

    public void repair() {
        Storage stockpile = (Storage) Game.currentGovernment.findBuildingByName("stockpile");
        Resource stone = stockpile.getStoredResourceByType(ResourceType.STONE);
        if (stone.getCount() < 20) {
            output("You don't have enough stone!");
            return;
        }
        this.setHp(maxHP);
        stockpile.removeFromStorage(Resource.createResource(ResourceType.STONE, 20));
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getWorkers() {
        return workers;
    }

    public int getHp() {
        return hp;
    }

    public void reduceHP(int amount) {
        this.hp -= amount;
        if (this.hp < 0) this.hp = 0;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Resource> getPrice() {
        return price;
    }

    public User getOwner() {
        return owner;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
