package Server.View.Game;

import Client.Model.Game;
import Client.Model.Building.Storage.Storage;
import Client.Model.Resources.Resource;
import Client.Model.Resources.ResourceType;
import Client.View.Game.GovernmentMenuGUI;
import Client.View.Game.MapGUI;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class ResourceViewGUI {
    private Pane menuPane;

    public ResourceViewGUI(Pane menuPane) {
        this.menuPane = menuPane;
    }

    public void run() {
        menuPane.getChildren().clear();
        createGUI();
    }

    private void createGUI() {
        Storage stockpile = (Storage) Game.currentGovernment.findBuildingByName("stockpile");
        Storage granary = (Storage) Game.currentGovernment.findBuildingByName("granary");
        Storage armoury = (Storage) Game.currentGovernment.findBuildingByName("armoury");
        Storage stable = (Storage) Game.currentGovernment.findBuildingByName("stable");
        ArrayList<Resource> resources = findCurrentGovernmentResources(stockpile, granary, armoury, stable);

        ImageView back = new ImageView(new Image(GovernmentMenuGUI.class.getResource("/MenuImages/back.png").toExternalForm()));
        back.setLayoutX(-200);
        back.setLayoutY(-200);
        back.setScaleX(0.1);
        back.setScaleY(0.1);
        back.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                MapGUI.menusGUI.runMenu();
            }
        });
        menuPane.getChildren().add(back);

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(50);
        hBox.setLayoutX(200);
        hBox.setLayoutY(20);
        menuPane.getChildren().add(hBox);

        VBox vBox0 = new VBox();
        vBox0.setAlignment(Pos.CENTER);
        vBox0.setSpacing(30);
        hBox.getChildren().add(vBox0);
        for (int i = 0 ; i < 5 ; i++) {
            Resource resource = resources.get(i);
            Text text = new Text();
            text.setText(resource.getResourceType().name + ": " + resource.getCount());
            vBox0.getChildren().add(text);
        }
        VBox vBox1 = new VBox();
        vBox1.setAlignment(Pos.CENTER);
        vBox1.setSpacing(30);
        hBox.getChildren().add(vBox1);
        for (int i = 5 ; i < 10 ; i++) {
            Resource resource = resources.get(i);
            Text text = new Text();
            text.setText(resource.getResourceType().name + ": " + resource.getCount());
            vBox1.getChildren().add(text);
        }
        VBox vBox2 = new VBox();
        vBox2.setAlignment(Pos.CENTER);
        vBox2.setSpacing(30);
        hBox.getChildren().add(vBox2);
        for (int i = 10 ; i < 15 ; i++) {
            Resource resource = resources.get(i);
            Text text = new Text();
            text.setText(resource.getResourceType().name + ": " + resource.getCount());
            vBox2.getChildren().add(text);
        }
        VBox vBox3 = new VBox();
        vBox3.setAlignment(Pos.CENTER);
        vBox3.setSpacing(30);
        hBox.getChildren().add(vBox3);
        for (int i = 15 ; i < 20 ; i++) {
            Resource resource = resources.get(i);
            Text text = new Text();
            text.setText(resource.getResourceType().name + ": " + resource.getCount());
            vBox3.getChildren().add(text);
        }
        VBox vBox4 = new VBox();
        vBox4.setAlignment(Pos.CENTER);
        vBox4.setSpacing(30);
        hBox.getChildren().add(vBox4);
        for (int i = 20 ; i < 23 ; i++) {
            Resource resource = resources.get(i);
            Text text = new Text();
            text.setText(resource.getResourceType().name + ": " + resource.getCount());
            vBox4.getChildren().add(text);
        }
    }

    public ArrayList<Resource> findCurrentGovernmentResources(Storage stockpile, Storage granary, Storage armoury, Storage stable) {
        ArrayList<Resource> output = new ArrayList<>();
        for (ResourceType resourceType : ResourceType.values()) {
            Resource resource = new Resource(resourceType, 0);
            output.add(resource);
        }
        if (stockpile != null) {
            for (Resource resource: stockpile.getStorage()) {
                Resource resource1 = getResourceFromList(resource.getResourceType().name, output);
                assert resource1 != null;
                resource1.setCount(resource.getCount());
            }
        }
        if (granary != null) {
            for (Resource resource: granary.getStorage()) {
                Resource resource1 = getResourceFromList(resource.getResourceType().name, output);
                assert resource1 != null;
                resource1.setCount(resource.getCount());
            }
        }
        if (armoury != null) {
            for (Resource resource: armoury.getStorage()) {
                Resource resource1 = getResourceFromList(resource.getResourceType().name, output);
                assert resource1 != null;
                resource1.setCount(resource.getCount());
            }
        }
        if (stable != null) {
            for (Resource resource: stable.getStorage()) {
                Resource resource1 = getResourceFromList(resource.getResourceType().name, output);
                assert resource1 != null;
                resource1.setCount(resource.getCount());
            }
        }

        return output;
    }

    public Resource getResourceFromList(String name, ArrayList<Resource> output) {
        for (Resource resource: output) {
            if (resource.getResourceType().name.equals(name)) {
                return resource;
            }
        }
        return null;
    }
}
