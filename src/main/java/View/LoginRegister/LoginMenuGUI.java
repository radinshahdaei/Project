package View.LoginRegister;

import Controller.Controller;
import Controller.LoginMenuController;
import Controller.ManageData;
import Controller.RegisterMenuController;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class LoginMenuGUI extends Application {
    private static int time = 5;
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 400, 200);
        stage.setScene(scene);
        stage.setTitle("Login");
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
        usernameView.setMaxWidth(350);

        TextField usernamePrompt = new TextField();
        usernamePrompt.setMaxWidth(350);
        usernamePrompt.setPromptText("username");


        BorderPane passwordView = new BorderPane();
        Text passwordText = new Text("Password:");
        Text passwordError = new Text("");
        passwordError.setFill(Color.RED);
        passwordView.setLeft(passwordText);
        passwordView.setRight(passwordError);
        passwordView.setMaxWidth(350);

        PasswordField passwordPrompt = new PasswordField();
        passwordPrompt.setMaxWidth(350);
        passwordPrompt.setPromptText("password");
        passwordPrompt.textProperty().addListener(observable -> {
            passwordPrompt.setStyle("-fx-background-color: #FFFFFF;");
            passwordError.setText("");
        });

        TextField passwordShownPrompt = new TextField();
        passwordShownPrompt.setVisible(false);
        passwordShownPrompt.setMaxWidth(350);
        passwordShownPrompt.setPromptText("password");
        passwordShownPrompt.textProperty().addListener(observable -> {
            passwordShownPrompt.setStyle("-fx-background-color: #FFFFFF;");
            passwordError.setText("");
        });

        AtomicBoolean passwordHidden = new AtomicBoolean(true);
        Button showPasswordButton = new Button("Show Password");
        showPasswordButton.setOnAction(event -> {
            passwordHidden.set(!passwordHidden.get());
            if (showPasswordButton.getText().equals("Show Password")) {
                showPasswordButton.setText("Hide Password");
                passwordPrompt.setVisible(false);
                passwordShownPrompt.setVisible(true);
                passwordShownPrompt.setText(passwordPrompt.getText());
                vBox.getChildren().set(vBox.getChildren().indexOf(passwordPrompt),passwordShownPrompt);
            } else {
                showPasswordButton.setText("Show Password");
                passwordShownPrompt.setVisible(false);
                passwordPrompt.setVisible(true);
                passwordPrompt.setText(passwordShownPrompt.getText());
                vBox.getChildren().set(vBox.getChildren().indexOf(passwordShownPrompt),passwordPrompt);
            }
        });

        Button forgetPassword = new Button("Forgot my password");
        forgetPassword.setOnAction(actionEvent -> forgetPassword());

        Button confirm = new Button("Confirm");
        confirm.setOnAction(actionEvent -> {
            boolean emptyField = false;
            if (usernamePrompt.getText().trim().equals("")) {
                usernamePrompt.setStyle("-fx-background-color: #FFCCCC;");
                emptyField = true;
            }

            if (passwordHidden.get() && passwordPrompt.getText().trim().equals("")) {
                passwordPrompt.setStyle("-fx-background-color: #FFCCCC;");
                emptyField = true;
            }

            if (!passwordHidden.get() && passwordShownPrompt.getText().trim().equals("")) {
                passwordShownPrompt.setStyle("-fx-background-color: #FFCCCC;");
                emptyField = true;
            }

            String result;
            if (passwordHidden.get()){
                result = LoginMenuController.checkUsernameAndPassword(usernamePrompt.getText(),passwordPrompt.getText());
            }
            else {
                result = LoginMenuController.checkUsernameAndPassword(usernamePrompt.getText(),passwordShownPrompt.getText());
            }

            if (result.equals("Success")) {
                passwordError.setText("");
                if (usernameError.getText().equals("") && !emptyField) {
                    if (RegisterMenuGUI.showCaptcha()){
                        Controller.setCurrentUser(Controller.findUserByUsername(usernamePrompt.getText()));
                        ManageData.saveCurrentUser();
                        try {
                            LoginMenuController.connectToServer();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        System.out.println("Hooray");
                    }

                    //TODO go to game menu
                }
            }

            else {
                passwordError.setText(result);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Wrong Password");
                alert.setContentText("You can not login for "+time+" seconds");
                time+=5;

                alert.initStyle(StageStyle.UNDECORATED);

                ButtonType okButton = alert.getButtonTypes().stream()
                        .filter(buttonType -> buttonType.getButtonData() == ButtonBar.ButtonData.OK_DONE)
                        .findFirst()
                        .orElse(null);
                if (okButton != null) {
                    alert.getDialogPane().lookupButton(okButton).setDisable(true);
                }
                alert.getDialogPane().lookupButton(okButton).setVisible(false);

                alert.show();

                new Thread(() -> {
                    try {
                        Thread.sleep(time*1000L);  // Sleep for 5 seconds
                        javafx.application.Platform.runLater(alert::close);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }).start();
            }
        });

        AtomicBoolean errorCheck = new AtomicBoolean(true);

        Button back = new Button("back");
        back.setOnAction(actionEvent -> {
            try {
                errorCheck.set(false);
                new MainMenuGUI().start(stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        usernamePrompt.textProperty().addListener(observable -> {
            usernamePrompt.setStyle("-fx-background-color: #FFFFFF;");
            passwordError.setText("");
        });

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(5);
        hBox.getChildren().addAll(confirm,back, forgetPassword, showPasswordButton);
        vBox.getChildren().addAll(usernameView,usernamePrompt,
                passwordView,passwordPrompt,
                hBox);
        root.setCenter(vBox);

        Thread errorCheckThread = new Thread(() -> {
            while (errorCheck.get()) {
                String result = LoginMenuController.checkUsername(usernamePrompt.getText());
                if (result.equals("Success") || usernamePrompt.getText().equals("")) {
                    usernameError.setText("");
                }
                else {
                    usernameError.setText(result);
                }
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        errorCheckThread.start();
        stage.setOnCloseRequest(event -> errorCheck.set(false));
    }

    public void forgetPassword(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Forget Password");

        DialogPane dialogPane = alert.getDialogPane();
        VBox vBox = new VBox();

        dialogPane.setMinWidth(450);
        dialogPane.setMinHeight(300);
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

        BorderPane questionView = new BorderPane();
        Text question = new Text("Question: ");
        Text error = new Text();
        error.setFill(Color.RED);
        questionView.setLeft(question);
        questionView.setRight(error);

        TextField answer = new TextField();
        answer.setPromptText("answer");
        answer.textProperty().addListener(observable -> {
            answer.setStyle("-fx-background-color: #FFFFFF;");
            error.setText("");
        });

        usernamePrompt.textProperty().addListener(observable -> {
            usernamePrompt.setStyle("-fx-background-color: #FFFFFF;");
            question.setText("Question: ");
        });

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
        passwordPrompt.setPromptText("password");
        passwordConfirmationPrompt.setPromptText("confirm password");
        passwordPrompt.textProperty().addListener(observable -> passwordPrompt.setStyle("-fx-background-color: #FFFFFF;"));
        passwordConfirmationPrompt.textProperty().addListener(observable -> passwordConfirmationPrompt.setStyle("-fx-background-color: #FFFFFF;"));

        TextField passwordShownPrompt = new TextField();
        TextField passwordConfirmationShownPrompt = new TextField();
        passwordShownPrompt.setVisible(false);
        passwordConfirmationShownPrompt.setVisible(false);
        passwordShownPrompt.setMaxWidth(450);
        passwordConfirmationShownPrompt.setMaxWidth(450);
        passwordShownPrompt.setPromptText("password");
        passwordConfirmationShownPrompt.setPromptText("confirm password");
        passwordShownPrompt.textProperty().addListener(observable -> passwordShownPrompt.setStyle("-fx-background-color: #FFFFFF;"));
        passwordConfirmationShownPrompt.textProperty().addListener(observable -> passwordConfirmationShownPrompt.setStyle("-fx-background-color: #FFFFFF;"));

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
            }
            else {
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

        showPasswordButton.setMinWidth(vBox.widthProperty().get());
        showPasswordButton.setMinWidth(450);
        vBox.setSpacing(5);
        vBox.getChildren().addAll(usernameView,usernamePrompt,questionView,answer,passwordView,passwordPrompt,passwordConfirmationPrompt,showPasswordButton);
        dialogPane.setContent(vBox);


        ButtonType buttonTypeYes = new ButtonType("Confirm");
        ButtonType buttonTypeNo = new ButtonType("Cancel");
        alert.getButtonTypes().setAll(buttonTypeNo, buttonTypeYes);


        AtomicBoolean errorCheck = new AtomicBoolean(true);
        Thread errorCheckThread = new Thread(() -> {
            while (errorCheck.get()) {
                try {
                    String result;
                    result = LoginMenuController.checkUsername(usernamePrompt.getText());
                    if (result.equals("Success") || usernamePrompt.getText().equals("")) {
                        usernameError.setText("");
                        if (result.equals("Success")){
                            int questionNumber = Objects.requireNonNull(Controller.findUserByUsername(usernamePrompt.getText())).getQuestionNumber();
                            if (questionNumber == 1) question.setText("Question: What is my father’s name?");
                            if (questionNumber == 2) question.setText("Question: What was my first pet’s name?");
                            if (questionNumber == 3) question.setText("Question: What is my mother’s last name?");
                        }
                    }
                    else usernameError.setText(result);

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
                    if (usernamePrompt.getText().trim().equals("")) {
                        usernamePrompt.setStyle("-fx-background-color: #FFCCCC;");
                        emptyField = true;
                    }

                    if (answer.getText().trim().equals("")) {
                        answer.setStyle("-fx-background-color: #FFCCCC;");
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

                    if (!usernamePrompt.getText().equals("") && usernameError.getText().equals("") &&
                            !answer.getText().equals(Objects.requireNonNull(Controller.findUserByUsername(usernamePrompt.getText())).getAnswer()) &&
                            !answer.getText().trim().equals("")) {
                        error.setText("Wrong answer");
                    }

                    if (error.getText().equals("") && passwordError.getText().equals("") && usernameError.getText().equals("") && !emptyField) {
                        String password;
                        if (passwordHidden.get()) password = passwordPrompt.getText();
                        else password = passwordShownPrompt.getText();
                        Objects.requireNonNull(Controller.findUserByUsername(usernamePrompt.getText())).setPassword(ManageData.encrypt(password));
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
}
