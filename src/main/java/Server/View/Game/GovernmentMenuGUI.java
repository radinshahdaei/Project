package Server.View.Game;

import Client.Controller.GovernmentMenuController;
import Client.Model.Game;
import Client.View.Game.MapGUI;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

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
        popularity.setText("Popularity: " + String.valueOf(Game.currentGovernment.getPopularity()));
        popularity.setLayoutX(350);
        popularity.setLayoutY(70);
        menuPane.getChildren().add(popularity);

        Text population = new Text();
        population.setText("Population: " + String.valueOf(Game.currentGovernment.getPopulation()));
        population.setLayoutX(350);
        population.setLayoutY(100);
        menuPane.getChildren().add(population);

        Text foodEffect = new Text();
        foodEffect.setText("Food effect: " + String.valueOf(Game.currentGovernment.getFoodEffect()));
        foodEffect.setLayoutX(350);
        foodEffect.setLayoutY(130);
        ImageView imageViewFoodEffect;
        if (Game.currentGovernment.getFoodEffect() > 0) {
            foodEffect.setFill(Color.GREEN);
            imageViewFoodEffect = new ImageView(new Image(GovernmentMenuGUI.class.getResource("/MenuImages/happy.png").toExternalForm()));
            imageViewFoodEffect.setScaleX(0.3);
            imageViewFoodEffect.setScaleY(0.3);
            imageViewFoodEffect.setLayoutX(430);
            imageViewFoodEffect.setLayoutY(75);
        }
        else if (Game.currentGovernment.getFoodEffect() < 0) {
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
        taxEffect.setText("Tax effect: " + String.valueOf(Game.currentGovernment.getTaxEffect()));
        taxEffect.setLayoutX(350);
        taxEffect.setLayoutY(160);
        ImageView imageViewTaxEffect;
        if (Game.currentGovernment.getTaxEffect() > 0) {
            taxEffect.setFill(Color.GREEN);
            imageViewTaxEffect = new ImageView(new Image(GovernmentMenuGUI.class.getResource("/MenuImages/happy.png").toExternalForm()));
            imageViewTaxEffect.setScaleX(0.3);
            imageViewTaxEffect.setScaleY(0.3);
            imageViewTaxEffect.setLayoutX(430);
            imageViewTaxEffect.setLayoutY(75 + 30);
        }
        else if (Game.currentGovernment.getTaxEffect() < 0) {
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
        religionEffect.setText("Religion Effect: " + String.valueOf(Game.currentGovernment.getReligionEffect()));
        religionEffect.setLayoutX(550);
        religionEffect.setLayoutY(70);
        ImageView imageViewReligionEffect;
        if (Game.currentGovernment.getReligionEffect() > 0) {
            religionEffect.setFill(Color.GREEN);
            imageViewReligionEffect = new ImageView(new Image(GovernmentMenuGUI.class.getResource("/MenuImages/happy.png").toExternalForm()));
            imageViewReligionEffect.setScaleX(0.3);
            imageViewReligionEffect.setScaleY(0.3);
            imageViewReligionEffect.setLayoutX(430 + 200);
            imageViewReligionEffect.setLayoutY(75 - 60);
        }
        else if (Game.currentGovernment.getReligionEffect() < 0) {
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
        fearEffect.setText("Fear effect: " +  String.valueOf(Game.currentGovernment.getFearEffect()));
        fearEffect.setLayoutX(550);
        fearEffect.setLayoutY(100);
        ImageView imageViewFearEffect;
        if (Game.currentGovernment.getFearEffect() > 0) {
            fearEffect.setFill(Color.GREEN);
            imageViewFearEffect = new ImageView(new Image(GovernmentMenuGUI.class.getResource("/MenuImages/happy.png").toExternalForm()));
            imageViewFearEffect.setScaleX(0.3);
            imageViewFearEffect.setScaleY(0.3);
            imageViewFearEffect.setLayoutX(430 + 200);
            imageViewFearEffect.setLayoutY(75 - 30);
        }
        else if (Game.currentGovernment.getFearEffect() < 0) {
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
        innEffect.setText("Inn effect: " +  String.valueOf(Game.currentGovernment.getInnEffect()));
        innEffect.setLayoutX(550);
        innEffect.setLayoutY(130);
        ImageView imageViewInnEffect;
        if (Game.currentGovernment.getInnEffect() > 0) {
            innEffect.setFill(Color.GREEN);
            imageViewInnEffect = new ImageView(new Image(GovernmentMenuGUI.class.getResource("/MenuImages/happy.png").toExternalForm()));
            imageViewInnEffect.setScaleX(0.3);
            imageViewInnEffect.setScaleY(0.3);
            imageViewInnEffect.setLayoutX(430 + 200);
            imageViewInnEffect.setLayoutY(75);
        }
        else if (Game.currentGovernment.getInnEffect() < 0) {
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

        Text changeRates = new Text();
        changeRates.setText("Change rates!");
        changeRates.setLayoutX(750);
        changeRates.setLayoutY(70);
        changeRates.setScaleX(1.3);
        changeRates.setScaleY(1.3);
        changeRates.setFill(Color.BLUE);
        changeRates.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                changeGUI();
            }
        });
        menuPane.getChildren().add(changeRates);
    }

    public void changeGUI() {
        menuPane.getChildren().clear();
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
                runMenu();
            }
        });
        menuPane.getChildren().add(back);

        Text foodRateText = new Text();
        foodRateText.setText("Food Rate");
        foodRateText.setLayoutX(375);
        foodRateText.setLayoutY(70);
        foodRateText.setScaleX(1.2);
        foodRateText.setScaleY(1.2);
        ChoiceBox<Integer> foodRate = new ChoiceBox<>();
        for (int i = -2 ; i <=2 ; i++) {
            foodRate.getItems().add(i);
        }
        foodRate.getSelectionModel().select(Game.currentGovernment.getFoodRate() + 2);
        foodRate.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) ->
                fixFoodRate(newValue, foodRate));
        foodRate.setLayoutX(455);
        foodRate.setLayoutY(53);
        menuPane.getChildren().add(foodRateText);
        menuPane.getChildren().add(foodRate);

        Text fearRateText = new Text();
        fearRateText.setText("Fear Rate");
        fearRateText.setLayoutX(375);
        fearRateText.setLayoutY(100);
        fearRateText.setScaleX(1.2);
        fearRateText.setScaleY(1.2);
        ChoiceBox<Integer> fearRate = new ChoiceBox<>();
        for (int i = -5 ; i <=5 ; i++) {
            fearRate.getItems().add(i);
        }
        fearRate.getSelectionModel().select(Game.currentGovernment.getFearRate() + 5);
        fearRate.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) ->
                fixFearRate(newValue, fearRate));
        fearRate.setLayoutX(455);
        fearRate.setLayoutY(83);
        menuPane.getChildren().add(fearRateText);
        menuPane.getChildren().add(fearRate);

        Text taxRateText = new Text();
        taxRateText.setText("Tax Rate");
        taxRateText.setLayoutX(375);
        taxRateText.setLayoutY(130);
        taxRateText.setScaleX(1.2);
        taxRateText.setScaleY(1.2);
        ChoiceBox<Integer> taxRate = new ChoiceBox<>();
        for (int i = -3 ; i <=8 ; i++) {
            taxRate.getItems().add(i);
        }
        taxRate.getSelectionModel().select(Game.currentGovernment.getTaxRate() + 3);
        taxRate.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) ->
                fixTaxRate(newValue, taxRate));
        taxRate.setLayoutX(455);
        taxRate.setLayoutY(113);
        menuPane.getChildren().add(taxRateText);
        menuPane.getChildren().add(taxRate);
    }

    private void fixTaxRate(Integer newValue, ChoiceBox<Integer> taxRate) {
        GovernmentMenuController.setTaxRate(newValue);
        taxRate.getSelectionModel().select(Game.currentGovernment.getTaxRate() + 3);
    }

    private void fixFearRate(Integer newValue, ChoiceBox<Integer> fearRate) {
        GovernmentMenuController.setFearRate(newValue);
        fearRate.getSelectionModel().select(Game.currentGovernment.getFearRate() + 5);
    }

    private static void fixFoodRate(Integer newValue, ChoiceBox<Integer> foodRate) {
        GovernmentMenuController.setFoodRate(newValue);
        foodRate.getSelectionModel().select(Game.currentGovernment.getFoodRate() + 2);
    }

    public void runMenu() {
        menuPane.getChildren().clear();
        createGUI();
    }
}
