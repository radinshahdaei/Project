package View.Game;

import Model.Game;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import static Model.Game.currentGovernment;

public class GovernmentMenuGUI {
    private Pane menuPane;

    public GovernmentMenuGUI(Pane menuPane) {
        this.menuPane = menuPane;
    }

    public void createGUI() {
        ImageView background = new ImageView(new Image(GovernmentMenuGUI.class.getResource("/MenuImages/menu.png").toExternalForm()));
        background.setFitWidth(menuPane.getWidth());
        background.setFitHeight(menuPane.getHeight());
        background.setLayoutY(-80);
        background.setLayoutX(5);
        menuPane.getChildren().add(background);

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

        Text popularity = new Text();
        currentGovernment.updatePopularity();
        popularity.setText("Popularity: " + String.valueOf(currentGovernment.getPopularity()));
        popularity.setLayoutX(350);
        popularity.setLayoutY(70);
        menuPane.getChildren().add(popularity);

        Text population = new Text();
        population.setText("Population: " + String.valueOf(currentGovernment.getPopulation()));
        population.setLayoutX(350);
        population.setLayoutY(100);
        menuPane.getChildren().add(population);

        Text foodEffect = new Text();
        foodEffect.setText("Food effect: " + String.valueOf(currentGovernment.getFoodEffect()));
        foodEffect.setLayoutX(350);
        foodEffect.setLayoutY(130);
        ImageView imageViewFoodEffect;
        if (currentGovernment.getFoodEffect() > 0) {
            foodEffect.setFill(Color.GREEN);
            imageViewFoodEffect = new ImageView(new Image(GovernmentMenuGUI.class.getResource("/MenuImages/happy.png").toExternalForm()));
            imageViewFoodEffect.setScaleX(0.3);
            imageViewFoodEffect.setScaleY(0.3);
            imageViewFoodEffect.setLayoutX(430);
            imageViewFoodEffect.setLayoutY(75);
        }
        else if (currentGovernment.getFoodEffect() < 0) {
            foodEffect.setFill(Color.RED);
            imageViewFoodEffect = new ImageView(new Image(GovernmentMenuGUI.class.getResource("/MenuImages/sad.png").toExternalForm()));
            imageViewFoodEffect.setScaleX(0.06);
            imageViewFoodEffect.setScaleY(0.06);
            imageViewFoodEffect.setLayoutX(230);
            imageViewFoodEffect.setLayoutY(-125);
        }
        else {
            imageViewFoodEffect = new ImageView(new Image(GovernmentMenuGUI.class.getResource("/MenuImages/neutral.png").toExternalForm()));
            imageViewFoodEffect.setScaleX(0.08);
            imageViewFoodEffect.setScaleY(0.08);
            imageViewFoodEffect.setLayoutX(330);
            imageViewFoodEffect.setLayoutY(-35);
        }
        menuPane.getChildren().add(foodEffect);
        menuPane.getChildren().add(imageViewFoodEffect);

        Text taxEffect = new Text();
        taxEffect.setText("Tax effect: " + String.valueOf(currentGovernment.getTaxEffect()));
        taxEffect.setLayoutX(350);
        taxEffect.setLayoutY(160);
        ImageView imageViewTaxEffect;
        if (currentGovernment.getTaxEffect() > 0) {
            taxEffect.setFill(Color.GREEN);
            imageViewTaxEffect = new ImageView(new Image(GovernmentMenuGUI.class.getResource("/MenuImages/happy.png").toExternalForm()));
            imageViewTaxEffect.setScaleX(0.3);
            imageViewTaxEffect.setScaleY(0.3);
            imageViewTaxEffect.setLayoutX(430);
            imageViewTaxEffect.setLayoutY(75 + 30);
        }
        else if (currentGovernment.getTaxEffect() < 0) {
            taxEffect.setFill(Color.RED);
            imageViewTaxEffect = new ImageView(new Image(GovernmentMenuGUI.class.getResource("/MenuImages/sad.png").toExternalForm()));
            imageViewTaxEffect.setScaleX(0.06);
            imageViewTaxEffect.setScaleY(0.06);
            imageViewTaxEffect.setLayoutX(230);
            imageViewTaxEffect.setLayoutY(-125 + 30);
        }
        else {
            imageViewTaxEffect = new ImageView(new Image(GovernmentMenuGUI.class.getResource("/MenuImages/neutral.png").toExternalForm()));
            imageViewTaxEffect.setScaleX(0.08);
            imageViewTaxEffect.setScaleY(0.08);
            imageViewTaxEffect.setLayoutX(330);
            imageViewTaxEffect.setLayoutY(-35 + 30);
        }
        menuPane.getChildren().add(taxEffect);
        menuPane.getChildren().add(imageViewTaxEffect);

        Text religionEffect = new Text();
        religionEffect.setText("Religion Effect: " + String.valueOf(currentGovernment.getReligionEffect()));
        religionEffect.setLayoutX(550);
        religionEffect.setLayoutY(70);
        ImageView imageViewReligionEffect;
        if (currentGovernment.getReligionEffect() > 0) {
            religionEffect.setFill(Color.GREEN);
            imageViewReligionEffect = new ImageView(new Image(GovernmentMenuGUI.class.getResource("/MenuImages/happy.png").toExternalForm()));
            imageViewReligionEffect.setScaleX(0.3);
            imageViewReligionEffect.setScaleY(0.3);
            imageViewReligionEffect.setLayoutX(430 + 200);
            imageViewReligionEffect.setLayoutY(75 - 60);
        }
        else if (currentGovernment.getReligionEffect() < 0) {
            religionEffect.setFill(Color.RED);
            imageViewReligionEffect = new ImageView(new Image(GovernmentMenuGUI.class.getResource("/MenuImages/sad.png").toExternalForm()));
            imageViewReligionEffect.setScaleX(0.06);
            imageViewReligionEffect.setScaleY(0.06);
            imageViewReligionEffect.setLayoutX(230 + 200);
            imageViewReligionEffect.setLayoutY(-125 - 60);
        }
        else {
            imageViewReligionEffect = new ImageView(new Image(GovernmentMenuGUI.class.getResource("/MenuImages/neutral.png").toExternalForm()));
            imageViewReligionEffect.setScaleX(0.08);
            imageViewReligionEffect.setScaleY(0.08);
            imageViewReligionEffect.setLayoutX(330 + 200);
            imageViewReligionEffect.setLayoutY(-35 - 60);
        }
        menuPane.getChildren().add(religionEffect);
        menuPane.getChildren().add(imageViewReligionEffect);

        Text fearEffect = new Text();
        fearEffect.setText("Fear effect: " +  String.valueOf(currentGovernment.getFearEffect()));
        fearEffect.setLayoutX(550);
        fearEffect.setLayoutY(100);
        ImageView imageViewFearEffect;
        if (currentGovernment.getFearEffect() > 0) {
            fearEffect.setFill(Color.GREEN);
            imageViewFearEffect = new ImageView(new Image(GovernmentMenuGUI.class.getResource("/MenuImages/happy.png").toExternalForm()));
            imageViewFearEffect.setScaleX(0.3);
            imageViewFearEffect.setScaleY(0.3);
            imageViewFearEffect.setLayoutX(430 + 200);
            imageViewFearEffect.setLayoutY(75 - 30);
        }
        else if (currentGovernment.getFearEffect() < 0) {
            fearEffect.setFill(Color.RED);
            imageViewFearEffect = new ImageView(new Image(GovernmentMenuGUI.class.getResource("/MenuImages/sad.png").toExternalForm()));
            imageViewFearEffect.setScaleX(0.06);
            imageViewFearEffect.setScaleY(0.06);
            imageViewFearEffect.setLayoutX(230 + 200);
            imageViewFearEffect.setLayoutY(-125 - 30);
        }
        else {
            imageViewFearEffect = new ImageView(new Image(GovernmentMenuGUI.class.getResource("/MenuImages/neutral.png").toExternalForm()));
            imageViewFearEffect.setScaleX(0.08);
            imageViewFearEffect.setScaleY(0.08);
            imageViewFearEffect.setLayoutX(330 + 200);
            imageViewFearEffect.setLayoutY(-35 - 30);
        }
        menuPane.getChildren().add(fearEffect);
        menuPane.getChildren().add(imageViewFearEffect);

        Text innEffect = new Text();
        innEffect.setText("Fear effect: " +  String.valueOf(currentGovernment.getInnEffect()));
        innEffect.setLayoutX(550);
        innEffect.setLayoutY(130);
        ImageView imageViewInnEffect;
        if (currentGovernment.getInnEffect() > 0) {
            innEffect.setFill(Color.GREEN);
            imageViewInnEffect = new ImageView(new Image(GovernmentMenuGUI.class.getResource("/MenuImages/happy.png").toExternalForm()));
            imageViewInnEffect.setScaleX(0.3);
            imageViewInnEffect.setScaleY(0.3);
            imageViewInnEffect.setLayoutX(430 + 200);
            imageViewInnEffect.setLayoutY(75);
        }
        else if (currentGovernment.getInnEffect() < 0) {
            innEffect.setFill(Color.RED);
            imageViewInnEffect = new ImageView(new Image(GovernmentMenuGUI.class.getResource("/MenuImages/sad.png").toExternalForm()));
            imageViewInnEffect.setScaleX(0.06);
            imageViewInnEffect.setScaleY(0.06);
            imageViewInnEffect.setLayoutX(230 + 200);
            imageViewInnEffect.setLayoutY(-125);
        }
        else {
            imageViewInnEffect = new ImageView(new Image(GovernmentMenuGUI.class.getResource("/MenuImages/neutral.png").toExternalForm()));
            imageViewInnEffect.setScaleX(0.08);
            imageViewInnEffect.setScaleY(0.08);
            imageViewInnEffect.setLayoutX(330 + 200);
            imageViewInnEffect.setLayoutY(-35);
        }
        menuPane.getChildren().add(innEffect);
        menuPane.getChildren().add(imageViewInnEffect);
    }

    public void runMenu() {
        menuPane.getChildren().clear();
        createGUI();
    }
}
