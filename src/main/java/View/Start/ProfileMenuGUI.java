package View.Start;

import Controller.Controller;
import Model.Game;
import Model.User;
import View.Examples.ChangeAvatar;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import Controller.RegisterMenuController;
import Controller.ManageData;

public class ProfileMenuGUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 500, 350);
        stage.setScene(scene);
        stage.setTitle("Profile");
        stage.show();

        User user = Controller.currentUser;

        VBox vBox = new VBox();
        root.setCenter(vBox);
        vBox.setMaxWidth(450);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);

        BorderPane showDetails = new BorderPane();
        VBox avatarView = new VBox();
        ImageView avatar = new ImageView();
        avatar.setImage(new Image(Game.class.getResource("/Images/Avatars/1.png").toString())); //TODO change to user avatar
        Button changeAvatar = new Button("Change Avatar");
        changeAvatar.setOnAction(actionEvent -> {
            ChangeAvatar changeAvatar1 = new ChangeAvatar();
            changeAvatar1.start(stage);
            ManageData.saveCurrentUser();
            ManageData.saveUsers();
        });

        avatar.setFitHeight(100);
        avatar.setFitWidth(100);
        avatarView.getChildren().addAll(avatar,changeAvatar);
        String details = "Username: "+ user.getUsername() + " (" + user.getNickname()+")\n\n";
        details += "Email: " + user.getEmail() +"\n\n";
        details += "Slogan: "+ user.getSlogan();
        Label text = new Label(details);
        text.setFont(new Font(13));
        showDetails.setLeft(avatarView);
        showDetails.setCenter(text);

        vBox.getChildren().add(showDetails);

        BorderPane usernameView = new BorderPane();
        TextField usernamePrompt = new TextField();
        Button changeUsername = new Button("Change");
        Text usernameError = new Text("");
        usernameError.setFill(Color.RED);

        usernamePrompt.setMinWidth(220);

        changeUsername.setOnAction(actionEvent -> {
            if (usernameError.getText().equals("")){
                user.setUsername(usernamePrompt.getText());
                ManageData.saveUsers();
                ManageData.saveCurrentUser();
                setAlert("Success","username changed successfully!","");
                String newDetails = updateDetails(user);
                text.setText(newDetails);
            }
        });

        usernamePrompt.setPromptText("new username");
        usernamePrompt.textProperty().addListener((observableValue, s, t1) -> {
            String result = RegisterMenuController.checkUsernameError(usernamePrompt.getText());
            if (result.equals("Success") || usernamePrompt.getText().equals("")) usernameError.setText("");
            else usernameError.setText(result);
        });
        usernameView.setLeft(usernamePrompt);
        usernameView.setCenter(usernameError);
        usernameView.setRight(changeUsername);
        vBox.getChildren().add(usernameView);


        BorderPane nicknameView = new BorderPane();
        TextField nicknamePrompt = new TextField();
        Button changeNickname = new Button("Change");


        nicknamePrompt.setMinWidth(220);

        changeNickname.setOnAction(actionEvent -> {
            user.setNickname(nicknamePrompt.getText());
            ManageData.saveUsers();
            ManageData.saveCurrentUser();
            setAlert("Success","nickname changed successfully!","");
            String newDetails = updateDetails(user);
            text.setText(newDetails);
        });

        nicknamePrompt.setPromptText("new nickname");

        nicknameView.setLeft(nicknamePrompt);
        nicknameView.setRight(changeNickname);
        vBox.getChildren().add(nicknameView);


        BorderPane emailView = new BorderPane();
        TextField emailPrompt = new TextField();
        Button changeEmail = new Button("Change");
        Text emailError = new Text("");
        emailError.setFill(Color.RED);

        emailPrompt.setMinWidth(220);

        changeEmail.setOnAction(actionEvent -> {
            if (emailError.getText().equals("")){
                user.setEmail(emailPrompt.getText());
                ManageData.saveUsers();
                ManageData.saveCurrentUser();
                setAlert("Success","email changed successfully!","");
                String newDetails = updateDetails(user);
                text.setText(newDetails);
            }
        });

        emailPrompt.setPromptText("new email");
        emailPrompt.textProperty().addListener((observableValue, s, t1) -> {
            String result = RegisterMenuController.checkEmailError(emailPrompt.getText());
            if (result.equals("Success") || emailPrompt.getText().equals("")) emailError.setText("");
            else emailError.setText(result);
        });
        emailView.setLeft(emailPrompt);
        emailView.setCenter(emailError);
        emailView.setRight(changeEmail);
        vBox.getChildren().add(emailView);

        HBox sloganShowed = new HBox();
        TextField sloganPrompt = new TextField();
        sloganPrompt.setMaxWidth(450);
        sloganPrompt.setPromptText("new slogan");

        Button changeSlogan = new Button("Change");
        changeSlogan.setOnAction(actionEvent -> {
            user.setSlogan(sloganPrompt.getText());
            ManageData.saveUsers();
            ManageData.saveCurrentUser();
            setAlert("Success","slogan changed successfully!","");
            String newDetails = updateDetails(user);
            text.setText(newDetails);
        });

        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.setItems(FXCollections.observableArrayList(
                "Answer the call of the Holy Land and join the Crusade.",
                "Experience the thrill of medieval warfare as a Crusader.",
                "Fight for faith and glory in the name of the Cross.",
                "Become a legend in a battle for Jerusalem and beyond.",
                "Join the ranks of the brave and the faithful on a journey to the Holy Land."));

        comboBox.setPromptText("Famous Slogans");

        comboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            sloganPrompt.setText(newValue);
        });

        sloganShowed.getChildren().addAll(sloganPrompt,comboBox,changeSlogan);
        sloganShowed.setSpacing(5);
        sloganShowed.setAlignment(Pos.CENTER);
        comboBox.setMaxWidth(160);
        sloganPrompt.setMinWidth(220);

        vBox.getChildren().add(sloganShowed);

        Button changePassword = new Button("Change Password");
        Button back = new Button("Back");
        Button scoreboard = new Button("Scoreboard");
        changePassword.setMinWidth(220);
        back.setMinWidth(60);
        scoreboard.setMinWidth(155);
        HBox hBox = new HBox();
        hBox.getChildren().addAll(changePassword,scoreboard,back);
        hBox.setMinWidth(450);
        hBox.setSpacing(7);
        vBox.getChildren().add(hBox);

    }

    public void setAlert(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public String updateDetails(User user){
        String details = "Username: "+ user.getUsername() + " (" + user.getNickname()+")\n\n";
        details += "Email: " + user.getEmail() +"\n\n";
        details += "Slogan: "+ user.getSlogan();
        return details;
    }
}
