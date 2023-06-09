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
import Controller.ProfileMenuController;
import Controller.ManageData;
import Controller.Controller;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

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
        avatar.setImage(new Image(user.getImageUrl()));
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
        if (user.getSlogan().equals("")) details+= "Slogan is empty";
        else details += "Slogan: "+ user.getSlogan();
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
                user.setUsername(usernamePrompt.getText()); //TODO bug fix
                ManageData.saveCurrentUser();
                ManageData.saveUsers();
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
            ManageData.saveCurrentUser();
            ManageData.saveUsers();
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
                ManageData.saveCurrentUser();
                ManageData.saveUsers();
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
            ManageData.saveCurrentUser();
            ManageData.saveUsers();
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
        changePassword.setOnAction(actionEvent -> changePassword());
        Button back = new Button("Back");
        Button scoreboard = new Button("Scoreboard");
        scoreboard.setOnAction(actionEvent -> showScoreboard());
        changePassword.setMinWidth(220);
        back.setMinWidth(60);
        scoreboard.setMinWidth(155);
        HBox hBox = new HBox();
        hBox.getChildren().addAll(changePassword,scoreboard,back);
        hBox.setMinWidth(450);
        hBox.setSpacing(7);
        vBox.getChildren().add(hBox);

        //TODO add scoreboard

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
        if (user.getSlogan().equals("")) details+= "Slogan is empty";
        else details += "Slogan: "+ user.getSlogan();
        return details;
    }

    public void changePassword(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Change Password");

        DialogPane dialogPane = alert.getDialogPane();
        VBox vBox = new VBox();

        dialogPane.setMinWidth(450);
        dialogPane.setMinHeight(300);
        BorderPane oldPasswordView = new BorderPane();
        Text oldPasswordText = new Text("Old password:");
        Text oldPasswordError = new Text("");
        oldPasswordError.setFill(Color.RED);
        oldPasswordView.setLeft(oldPasswordText);
        oldPasswordView.setRight(oldPasswordError);
        oldPasswordView.setMaxWidth(450);

        PasswordField oldPasswordPrompt = new PasswordField();
        oldPasswordPrompt.setMaxWidth(450);
        oldPasswordPrompt.setPromptText("old password");
        oldPasswordPrompt.textProperty().addListener(observable -> oldPasswordPrompt.setStyle("-fx-background-color: #FFFFFF;"));

        TextField oldPasswordShownPrompt = new TextField();
        oldPasswordShownPrompt.setVisible(false);
        oldPasswordShownPrompt.setMaxWidth(450);
        oldPasswordShownPrompt.setPromptText("old password");
        oldPasswordShownPrompt.textProperty().addListener(observable -> oldPasswordShownPrompt.setStyle("-fx-background-color: #FFFFFF;"));


        BorderPane passwordView = new BorderPane();
        Text passwordText = new Text("New password:");
        Text passwordError = new Text("");
        passwordError.setFill(Color.RED);
        passwordView.setLeft(passwordText);
        passwordView.setRight(passwordError);
        passwordView.setMaxWidth(450);

        PasswordField passwordPrompt = new PasswordField();
        PasswordField passwordConfirmationPrompt = new PasswordField();
        passwordPrompt.setMaxWidth(450);
        passwordConfirmationPrompt.setMaxWidth(450);
        passwordPrompt.setPromptText("new password");
        passwordConfirmationPrompt.setPromptText("confirm password");
        passwordPrompt.textProperty().addListener(observable -> passwordPrompt.setStyle("-fx-background-color: #FFFFFF;"));
        passwordConfirmationPrompt.textProperty().addListener(observable -> passwordConfirmationPrompt.setStyle("-fx-background-color: #FFFFFF;"));

        TextField passwordShownPrompt = new TextField();
        TextField passwordConfirmationShownPrompt = new TextField();
        passwordShownPrompt.setVisible(false);
        passwordConfirmationShownPrompt.setVisible(false);
        passwordShownPrompt.setMaxWidth(450);
        passwordConfirmationShownPrompt.setMaxWidth(450);
        passwordShownPrompt.setPromptText("new password");
        passwordConfirmationShownPrompt.setPromptText("confirm password");
        passwordShownPrompt.textProperty().addListener(observable -> passwordShownPrompt.setStyle("-fx-background-color: #FFFFFF;"));
        passwordConfirmationShownPrompt.textProperty().addListener(observable -> passwordConfirmationShownPrompt.setStyle("-fx-background-color: #FFFFFF;"));

        AtomicBoolean passwordHidden = new AtomicBoolean(true);
        Button showPasswordButton = new Button("Show Password");
        showPasswordButton.setOnAction(event -> {
            passwordHidden.set(!passwordHidden.get());
            if (showPasswordButton.getText().equals("Show Password")) {
                showPasswordButton.setText("Hide Password");
                oldPasswordPrompt.setVisible(false);
                oldPasswordShownPrompt.setVisible(true);
                passwordPrompt.setVisible(false);
                passwordConfirmationPrompt.setVisible(false);
                passwordShownPrompt.setVisible(true);
                passwordShownPrompt.setText(passwordPrompt.getText());
                oldPasswordShownPrompt.setText(oldPasswordPrompt.getText());
                passwordConfirmationShownPrompt.setVisible(true);
                passwordConfirmationShownPrompt.setText(passwordConfirmationPrompt.getText());
                vBox.getChildren().set(vBox.getChildren().indexOf(passwordPrompt),passwordShownPrompt);
                vBox.getChildren().set(vBox.getChildren().indexOf(oldPasswordPrompt),oldPasswordShownPrompt);
                vBox.getChildren().set(vBox.getChildren().indexOf(passwordConfirmationPrompt),passwordConfirmationShownPrompt);
            }
            else {
                showPasswordButton.setText("Show Password");
                oldPasswordShownPrompt.setVisible(false);
                oldPasswordPrompt.setVisible(true);
                passwordShownPrompt.setVisible(false);
                passwordConfirmationShownPrompt.setVisible(false);
                passwordPrompt.setVisible(true);
                passwordPrompt.setText(passwordShownPrompt.getText());
                oldPasswordPrompt.setText(oldPasswordShownPrompt.getText());
                passwordConfirmationPrompt.setVisible(true);
                passwordConfirmationPrompt.setText(passwordConfirmationShownPrompt.getText());
                vBox.getChildren().set(vBox.getChildren().indexOf(oldPasswordShownPrompt),oldPasswordPrompt);
                vBox.getChildren().set(vBox.getChildren().indexOf(passwordShownPrompt),passwordPrompt);
                vBox.getChildren().set(vBox.getChildren().indexOf(passwordConfirmationShownPrompt),passwordConfirmationPrompt);
            }
        });

        showPasswordButton.setMinWidth(vBox.widthProperty().get());
        showPasswordButton.setMinWidth(450);
        vBox.setSpacing(5);
        vBox.getChildren().addAll(oldPasswordView,oldPasswordPrompt,passwordView,passwordPrompt,passwordConfirmationPrompt,showPasswordButton);
        dialogPane.setContent(vBox);


        ButtonType buttonTypeYes = new ButtonType("Confirm");
        ButtonType buttonTypeNo = new ButtonType("Cancel");
        alert.getButtonTypes().setAll(buttonTypeNo, buttonTypeYes);


        AtomicBoolean errorCheck = new AtomicBoolean(true);
        Thread errorCheckThread = new Thread(() -> {
            while (errorCheck.get()) {
                try {
                    String result;

                    if (passwordHidden.get()) {
                        result = RegisterMenuController.checkPasswordError(passwordPrompt.getText(),passwordConfirmationPrompt.getText());
                    } else {
                        result = RegisterMenuController.checkPasswordError(passwordShownPrompt.getText(),passwordConfirmationShownPrompt.getText());
                    }
                    if (result.equals("Success")) {
                        passwordError.setText("");
                    } else passwordError.setText(result);

                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        errorCheckThread.start();

        AtomicBoolean deleteConfirmed = new AtomicBoolean(false);
        while (!deleteConfirmed.get()) {
            alert.showAndWait().ifPresent(buttonType -> {
                if (buttonType == buttonTypeYes) {
                    boolean emptyField = false;
                    if (passwordHidden.get() && oldPasswordPrompt.getText().trim().equals("")) {
                        oldPasswordPrompt.setStyle("-fx-background-color: #FFCCCC;");
                        emptyField = true;
                    }

                    if (!passwordHidden.get() && oldPasswordShownPrompt.getText().trim().equals("")) {
                        oldPasswordShownPrompt.setStyle("-fx-background-color: #FFCCCC;");
                        emptyField = true;
                    }

                    if (passwordHidden.get() && passwordPrompt.getText().trim().equals("")) {
                        passwordPrompt.setStyle("-fx-background-color: #FFCCCC;");
                        emptyField = true;
                    }

                    if (passwordHidden.get() && passwordConfirmationPrompt.getText().trim().equals("")) {
                        passwordConfirmationPrompt.setStyle("-fx-background-color: #FFCCCC;");
                        emptyField = true;
                    }

                    if (!passwordHidden.get() && passwordShownPrompt.getText().trim().equals("")) {
                        passwordShownPrompt.setStyle("-fx-background-color: #FFCCCC;");
                        emptyField = true;
                    }

                    if (!passwordHidden.get() && passwordConfirmationShownPrompt.getText().trim().equals("")) {
                        passwordConfirmationShownPrompt.setStyle("-fx-background-color: #FFCCCC;");
                        emptyField = true;
                    }

                    if (!Controller.currentUser.getPassword().equals(oldPasswordPrompt.getText())) {
                        oldPasswordError.setText("Wrong Password!");
                    }

                    else if (oldPasswordError.getText().equals("") && passwordError.getText().equals("") && !emptyField) {
                        String password;
                        if (passwordHidden.get()) password = passwordPrompt.getText();
                        else password = passwordShownPrompt.getText();
                        Controller.currentUser.setPassword(ManageData.encrypt(password));
                        ManageData.saveUsers();
                        deleteConfirmed.set(true);
                        errorCheck.set(false);
                    }

                } else if (buttonType == buttonTypeNo){
                    deleteConfirmed.set(true);
                    errorCheck.set(false);
                }
            });

        }
    }

    public static void showScoreboard(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        DialogPane dialogPane = alert.getDialogPane();
        VBox vbox = new VBox();
        vbox.setSpacing(10);

        alert.setTitle("Scoreboard");

        ArrayList<User> users = ProfileMenuController.sortUsers();
        int index = 1;
        for (User user:users){
            BorderPane borderPane = new BorderPane();
            ImageView imageView = new ImageView(new Image(user.getImageUrl()));
            imageView.setFitHeight(20);
            imageView.setFitWidth(20);
            Label label = new Label();
            label.setText(index+") Username: "+user.getUsername()+", HighScore: "+user.getHighScore());
            borderPane.setLeft(label);
            borderPane.setRight(imageView);
            vbox.getChildren().add(borderPane);
            index++;
        }

        vbox.setAlignment(Pos.CENTER);
        ScrollPane scrollPane = new ScrollPane(vbox);
        scrollPane.setFitToWidth(true);

        dialogPane.setMaxHeight(400);
        dialogPane.setMinWidth(300);

        dialogPane.setContent(scrollPane);
        alert.showAndWait();
    }
}

