package View.LoginRegister;

import Controller.Controller;
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

import java.util.concurrent.atomic.AtomicBoolean;

import Controller.LoginMenuController;
import javafx.stage.StageStyle;

public class LoginMenuGUI extends Application {
    private static int time = 5;
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 500, 200);
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
        usernameView.setMaxWidth(450);

        TextField usernamePrompt = new TextField();
        usernamePrompt.setMaxWidth(450);
        usernamePrompt.setPromptText("username");


        BorderPane passwordView = new BorderPane();
        Text passwordText = new Text("Password:");
        Text passwordError = new Text("");
        passwordError.setFill(Color.RED);
        passwordView.setLeft(passwordText);
        passwordView.setRight(passwordError);
        passwordView.setMaxWidth(450);

        PasswordField passwordPrompt = new PasswordField();
        passwordPrompt.setMaxWidth(450);
        passwordPrompt.setPromptText("password");
        passwordPrompt.textProperty().addListener(observable -> {
            passwordPrompt.setStyle("-fx-background-color: #FFFFFF;");
            passwordError.setText("");
        });

        TextField passwordShownPrompt = new TextField();
        passwordShownPrompt.setVisible(false);
        passwordShownPrompt.setMaxWidth(450);
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
        usernamePrompt.textProperty().addListener(observable -> {
            usernamePrompt.setStyle("-fx-background-color: #FFFFFF;");
            passwordError.setText("");
        });

        Button forgetPassword = new Button("Forgot my password");
        forgetPassword.setOnAction(actionEvent -> {
            //TODO
        });


        Button confirm = new Button("Confirm");
        confirm.setOnAction(actionEvent -> {
            if (usernamePrompt.getText().trim().equals("")) {
                usernamePrompt.setStyle("-fx-background-color: #FFCCCC;");
            }
            if (passwordHidden.get() && passwordPrompt.getText().trim().equals("")) {
                passwordPrompt.setStyle("-fx-background-color: #FFCCCC;");
            }
            if (!passwordHidden.get() && passwordShownPrompt.getText().trim().equals("")) {
                passwordShownPrompt.setStyle("-fx-background-color: #FFCCCC;");
            }

            String result;
            if (passwordHidden.get()){
                result = LoginMenuController.checkUsernameAndPassword(usernamePrompt.getText(),passwordPrompt.getText());
            } else {
                result = LoginMenuController.checkUsernameAndPassword(usernamePrompt.getText(),passwordShownPrompt.getText());
            }
            if (result.equals("Success")) passwordError.setText("");
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
                    ((Button) alert.getDialogPane().lookupButton(okButton)).setDisable(true);
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

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(5);
        hBox.getChildren().addAll(confirm,forgetPassword, showPasswordButton);
        vBox.getChildren().addAll(usernameView,usernamePrompt,
                passwordView,passwordPrompt,
                hBox);
        root.setCenter(vBox);

        AtomicBoolean errorCheck = new AtomicBoolean(true);
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
    }
}
