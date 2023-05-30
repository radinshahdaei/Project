package View.LoginRegister;

import Controller.Controller;
import Model.User;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import Controller.RegisterMenuController;

public class RegisterMenuGUI extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 500, 500);
        stage.setScene(scene);
        stage.setTitle("Register");
        stage.show();

        Controller.setStage(stage);

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);

        BorderPane usernameView = new BorderPane();
        Text usernameText = new Text("Username:");
        Text usernameError = new Text("");
        usernameError.setFill(Color.RED);
        usernameView.setLeft(usernameText);
        usernameView.setRight(usernameError);
        usernameView.setMaxWidth(450);

        TextField usernamePrompt = new TextField();
        usernamePrompt.setMaxWidth(450);
        usernamePrompt.setPromptText("username");
        usernamePrompt.textProperty().addListener(observable -> {
            usernamePrompt.setStyle("-fx-background-color: #FFFFFF;");
        });

        BorderPane passwordView = new BorderPane();
        Text passwordText = new Text("Password:");
        Text passwordError = new Text("");
        passwordError.setFill(Color.RED);
        passwordView.setLeft(passwordText);
        passwordView.setRight(passwordError);
        passwordView.setMaxWidth(450);

        PasswordField passwordPrompt = new PasswordField();
        PasswordField passwordConfirmationPrompt = new PasswordField();
        passwordPrompt.setMaxWidth(450);
        passwordConfirmationPrompt.setMaxWidth(450);
        passwordPrompt.setPromptText("password");
        passwordConfirmationPrompt.setPromptText("confirm password");
        passwordPrompt.textProperty().addListener(observable -> {
            passwordPrompt.setStyle("-fx-background-color: #FFFFFF;");
        });
        passwordConfirmationPrompt.textProperty().addListener(observable -> {
            passwordConfirmationPrompt.setStyle("-fx-background-color: #FFFFFF;");
        });

        TextField passwordShownPrompt = new TextField();
        TextField passwordConfirmationShownPrompt = new TextField();
        passwordShownPrompt.setVisible(false);
        passwordConfirmationShownPrompt.setVisible(false);
        passwordShownPrompt.setMaxWidth(450);
        passwordConfirmationShownPrompt.setMaxWidth(450);
        passwordShownPrompt.setPromptText("password");
        passwordConfirmationShownPrompt.setPromptText("confirm password");
        passwordShownPrompt.textProperty().addListener(observable -> {
            passwordShownPrompt.setStyle("-fx-background-color: #FFFFFF;");
        });
        passwordConfirmationShownPrompt.textProperty().addListener(observable -> {
            passwordConfirmationShownPrompt.setStyle("-fx-background-color: #FFFFFF;");
        });

        AtomicBoolean passwordHidden = new AtomicBoolean(true);
        Button showPasswordButton = new Button("Show Password");
        showPasswordButton.setOnAction(event -> {
            passwordHidden.set(!passwordHidden.get());
            if (showPasswordButton.getText().equals("Show Password")) {
                showPasswordButton.setText("Hide Password");
                passwordPrompt.setVisible(false);
                passwordConfirmationPrompt.setVisible(false);
                passwordShownPrompt.setVisible(true);
                passwordShownPrompt.setText(passwordPrompt.getText());
                passwordConfirmationShownPrompt.setVisible(true);
                passwordConfirmationShownPrompt.setText(passwordConfirmationPrompt.getText());
                vBox.getChildren().set(vBox.getChildren().indexOf(passwordPrompt),passwordShownPrompt);
                vBox.getChildren().set(vBox.getChildren().indexOf(passwordConfirmationPrompt),passwordConfirmationShownPrompt);
            } else {
                showPasswordButton.setText("Show Password");
                passwordShownPrompt.setVisible(false);
                passwordConfirmationShownPrompt.setVisible(false);
                passwordPrompt.setVisible(true);
                passwordPrompt.setText(passwordShownPrompt.getText());
                passwordConfirmationPrompt.setVisible(true);
                passwordConfirmationPrompt.setText(passwordConfirmationShownPrompt.getText());
                vBox.getChildren().set(vBox.getChildren().indexOf(passwordShownPrompt),passwordPrompt);
                vBox.getChildren().set(vBox.getChildren().indexOf(passwordConfirmationShownPrompt),passwordConfirmationPrompt);
            }
        });

        BorderPane emailView = new BorderPane();
        Text emailText = new Text("Email:");
        Text emailError = new Text("");
        emailError.setFill(Color.RED);
        emailView.setLeft(emailText);
        emailView.setRight(emailError);
        emailView.setMaxWidth(450);

        TextField emailPrompt = new TextField();
        emailPrompt.setMaxWidth(450);
        emailPrompt.setPromptText("email");
        emailPrompt.textProperty().addListener(observable -> {
            emailPrompt.setStyle("-fx-background-color: #FFFFFF;");
        });

        BorderPane nicknameView = new BorderPane();
        Text nicknameText = new Text("Nickname:");
        nicknameView.setLeft(nicknameText);
        nicknameView.setMaxWidth(450);

        TextField nicknamePrompt = new TextField();
        nicknamePrompt.setMaxWidth(450);
        nicknamePrompt.setPromptText("nickname");
        nicknamePrompt.textProperty().addListener(observable -> {
            nicknamePrompt.setStyle("-fx-background-color: #FFFFFF;");
        });

        BorderPane sloganView = new BorderPane();
        Text sloganText = new Text("Slogan");
        Text sloganNeeded = new Text("(Can be empty)");
        sloganView.setLeft(sloganText);
        sloganView.setRight(sloganNeeded);
        sloganView.setMaxWidth(450);

        TextField sloganPrompt = new TextField();
        sloganPrompt.setMaxWidth(450);
        sloganPrompt.setPromptText("slogan");

        Button randomPasswordButton = new Button("Random Password");
        randomPasswordButton.setOnAction(actionEvent -> {
            if (showPasswordButton.getText().equals("Show Password")) {
                passwordHidden.set(false);
                showPasswordButton.setText("Hide Password");
                passwordPrompt.setVisible(false);
                passwordConfirmationPrompt.setVisible(false);
                passwordShownPrompt.setVisible(true);
                passwordConfirmationShownPrompt.setVisible(true);
                vBox.getChildren().set(vBox.getChildren().indexOf(passwordPrompt),passwordShownPrompt);
                vBox.getChildren().set(vBox.getChildren().indexOf(passwordConfirmationPrompt),passwordConfirmationShownPrompt);
            }
            String randomPasswordText = RegisterMenuController.randomPassword();
            passwordShownPrompt.setText(randomPasswordText);
            passwordConfirmationShownPrompt.setText("");
        });

        Button randomSloganButton = new Button("Random Slogan");
        randomSloganButton.setOnAction(actionEvent -> {
            String randomSloganText = RegisterMenuController.randomSlogan();
            sloganPrompt.setText(randomSloganText);
        });

        Button back = new Button("Back");
        back.setOnAction(actionEvent -> {
            try {
                new MainMenuGUI().start(stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        Button confirmButton = new Button("Confirm");
        confirmButton.setOnAction(actionEvent -> {
            boolean emptyField = false;
            boolean hasError = false;
            if (usernamePrompt.getText().trim().equals("")) {
                usernamePrompt.setStyle("-fx-background-color: #FFCCCC;");
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
            if (emailPrompt.getText().trim().equals("")) {
                emailPrompt.setStyle("-fx-background-color: #FFCCCC;");
                emptyField = true;
            }
            if (nicknamePrompt.getText().trim().equals("")) {
                nicknamePrompt.setStyle("-fx-background-color: #FFCCCC;");
                emptyField = true;
            }
            if (!usernameError.getText().equals("")
                    || !passwordError.getText().equals("")
                    || !emailError.getText().equals("")) hasError = true;
            if (!hasError && !emptyField) {
                System.out.println("Hooray");
                AtomicReference<String> answerText = new AtomicReference<>();
                int answerNumber = startSecurityQuestion(answerText);
                String username = usernamePrompt.getText();
                String password;
                if (passwordHidden.get()) password = passwordPrompt.getText();
                else password = passwordShownPrompt.getText();
                String email = emailPrompt.getText();
                String nickname = nicknamePrompt.getText();
                String slogan;
                if (sloganPrompt.getText() == null) slogan = "";
                slogan = sloganPrompt.getText();
                User.createUser(username,password,nickname,email,slogan,answerText.get(),answerNumber);

                //TODO add captcha
            }
        });

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(5);
        hBox.getChildren().addAll(confirmButton,back,randomSloganButton,randomPasswordButton,showPasswordButton);
        vBox.getChildren().addAll(usernameView,usernamePrompt,
                passwordView,passwordPrompt,passwordConfirmationPrompt,
                emailView,emailPrompt,
                nicknameView,nicknamePrompt,
                sloganView,sloganPrompt,
                hBox);
        root.setCenter(vBox);



        AtomicBoolean errorCheck = new AtomicBoolean(true);
        Thread errorCheckThread = new Thread(() -> {
            while (errorCheck.get()) {
                try {
                    // check username
                    String result = RegisterMenuController.checkUsernameError(usernamePrompt.getText());
                    if (result.equals("Success") || usernamePrompt.getText().equals("")) usernameError.setText("");
                    else usernameError.setText(result);

                    //check password
                    if (passwordHidden.get()) {
                        result = RegisterMenuController.checkPasswordError(passwordPrompt.getText(),passwordConfirmationPrompt.getText());
                    } else {
                        result = RegisterMenuController.checkPasswordError(passwordShownPrompt.getText(),passwordConfirmationShownPrompt.getText());
                    }
                    if (result.equals("Success")) {
                        passwordError.setText("");
                    } else passwordError.setText(result);

                    //check email
                    result = RegisterMenuController.checkEmailError(emailPrompt.getText());
                    if (result.equals("Success") || emailPrompt.getText().equals("")) emailError.setText("");
                    else emailError.setText(result);
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        errorCheckThread.start();
        stage.setOnCloseRequest(event -> errorCheck.set(false));

    }

    public int startSecurityQuestion (AtomicReference<String> answerText){
        Alert securityQuestion = new Alert(Alert.AlertType.CONFIRMATION);
        securityQuestion.setTitle("Security Question");
        securityQuestion.setHeaderText("pick your security question!");

        DialogPane dialogPane = securityQuestion.getDialogPane();
        ComboBox<String> choices= new ComboBox<>();
        choices.getItems().addAll("1. What is my father’s name?","2. What was my first pet’s name?","3. What is my mother’s last name?");
        TextField answer = new TextField();
        TextField answerConfirmation = new TextField();
        answer.setPromptText("answer");
        answerConfirmation.setPromptText("answer confirmation");
        Text error = new Text("");
        error.setFill(Color.RED);
        GridPane gridPane = new GridPane();
        gridPane.addRow(0, answer);
        gridPane.addRow(1, answerConfirmation);
        gridPane.addColumn(1,choices);
        gridPane.addColumn(1,error);
        dialogPane.setContent(gridPane);
        gridPane.setVgap(10);
        gridPane.setHgap(10);

        ButtonType buttonTypeYes = new ButtonType("Confirm");
        ButtonType buttonTypeNo = new ButtonType("Cancel");
        securityQuestion.getButtonTypes().setAll(buttonTypeNo, buttonTypeYes);

        AtomicBoolean deleteConfirmed = new AtomicBoolean(false);
        // AtomicBoolean answerFound = new AtomicBoolean(false);

        AtomicInteger answerNumber = new AtomicInteger();

        while (!deleteConfirmed.get()) {
            securityQuestion.showAndWait().ifPresent(buttonType -> {
                if (buttonType == buttonTypeYes) {
                    if (choices.getValue() == null){
                        error.setText("you haven't picked a question!");
                    } else if (answer.getText().trim().equals("")){
                        error.setText("empty field!");
                    } else if (!answer.getText().equals(answerConfirmation.getText())) {
                        error.setText("answer and confirmation do not match");
                    } else {
                        // System.out.println("ok");
                        deleteConfirmed.set(true);
                        if (choices.getValue().equals("1. What is my father’s name?")) answerNumber.set(1);
                        else if (choices.getValue().equals("2. What was my first pet’s name")) answerNumber.set(2);
                        else answerNumber.set(3);
                        answerText.set(answer.getText());
                    }
                } else if (buttonType == buttonTypeNo) {
                    // System.out.println("Deletion canceled");
                    deleteConfirmed.set(true);
                }
            });

        }
        return answerNumber.get();
        // System.out.println(answerFound);
    }
}
