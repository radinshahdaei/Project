package Model;

import Model.Building.Building;
import Model.Person.Person;

import java.util.ArrayList;

public class Tile {
    private int x;
    private int y;
    private String texture;
    private boolean onFire = false;
    private boolean hasOil = false;
    private boolean hasKillingPit = false;
    private ArrayList<Person> people = new ArrayList<>();
    private Building building = null;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public ArrayList<Person> getPeople() {
        return people;
    }

    public void setPeople(ArrayList<Person> people) {
        this.people = people;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public String getTexture() {
        return texture;
    }

    public void setTexture(String texture) {
        this.texture = texture;
    }

    public void setOnFire(boolean onFire) {
        this.onFire = onFire;
    }

    public void setHasOil(boolean hasOil) {
        this.hasOil = hasOil;
    }

    public void setHasKillingPit(boolean hasKillingPit) {
        this.hasKillingPit = hasKillingPit;
    }
}
