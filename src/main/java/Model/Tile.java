package Model;

import Model.Person.Military.MilitaryUnit;
import Model.Person.Person;
import Model.Building.Building;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

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
    private Pane mainPane = new Pane();
    private Pane dataPane = new Pane();

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

    public ArrayList<Person> getPeople(User owner) {
        ArrayList<Person> peopleNOT = new ArrayList<>();
        for (Person person : people) {
            if (!person.getOwner().equals(owner)) peopleNOT.add(person);
        }
        return peopleNOT;
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

    public boolean isOnFire() {
        return onFire;
    }

    public boolean isHasOil() {
        return hasOil;
    }

    public boolean isHasKillingPit() {
        return hasKillingPit;
    }

    public Pane getMainPane() {
        return mainPane;
    }

    public void setMainPane(Pane mainPane) {
        this.mainPane = mainPane;
    }

    public Pane getDataPane() {
        return dataPane;
    }

    public void setDataPane(Pane dataPane) {
        this.dataPane = dataPane;
    }

    public void updateDataPane() {
        if (dataPane.getChildren().size() != 0) dataPane.getChildren().clear();
        int firstY = 20;
        int firstX = 10;
        Text coordinate = new Text("Tile coordinate: " + String.valueOf(x) + ", " + String.valueOf(y));
        coordinate.setLayoutX(firstX);
        coordinate.setLayoutY(firstY);
        dataPane.getChildren().add(coordinate);
        firstY += 15;
        Text textureDate = new Text("Texture: " + texture);
        textureDate.setLayoutX(firstX);
        textureDate.setLayoutY(firstY);
        dataPane.getChildren().add(textureDate);
        if (building != null) {
            firstY += 15;
            Text buildingData = new Text(building.getName() + ": Health-> " + String.valueOf(building.getHp()) +
                    " Owner-> " + building.getOwner().getUsername());
            buildingData.setLayoutX(firstX);
            buildingData.setLayoutY(firstY);
            dataPane.getChildren().add(buildingData);
        }
        if (people.size() != 0) {
            for (Person person: people) {
                firstY += 15;
                MilitaryUnit militaryUnit = (MilitaryUnit) person;
                Text soldierData = new Text(militaryUnit.getName() + ": Health-> " + militaryUnit.getDefence() +
                        " Owner-> " + militaryUnit.getOwner().getUsername());
                soldierData.setLayoutX(firstX);
                soldierData.setLayoutY(firstY);
                firstY += 15;
                dataPane.getChildren().add(soldierData);
                Text soldierData1 = new Text("  Damage-> " + militaryUnit.getAttack() + " Status-> " +
                        militaryUnit.getStatus());
                soldierData1.setLayoutX(firstX);
                soldierData1.setLayoutY(firstY);
                dataPane.getChildren().add(soldierData1);
            }
        }
    }

    public void showOnPane() {
        mainPane.getChildren().clear();
        mainPane.setPrefSize(170, 170);
        Rectangle backGroundRectangle = new Rectangle(0, 0, 170, 170);
        backGroundRectangle.setFill(Color.TRANSPARENT);
        Rectangle rect = new Rectangle(10, 10, 150, 150);
        rect.setFill(Color.GREEN);
        mainPane.getChildren().add(backGroundRectangle);
        mainPane.getChildren().add(rect);
        if (building != null) {
            ImageView buildingImage = new ImageView(new Image(building.getImageUrl()));
            buildingImage.setLayoutX(50);
            buildingImage.setLayoutY(50);
            buildingImage.setFitWidth(70);
            buildingImage.setFitHeight(70);
            mainPane.getChildren().add(buildingImage);
        }
    }
}