package View;

import View.Game.MapGUI;
import javafx.scene.control.Alert;
import javafx.stage.Modality;

import java.util.Scanner;

public class InputOutput {
    private static final Scanner scanner = new Scanner(System.in);

    public static String input() {
        return scanner.nextLine();
    }

    public static void output(String output) {
        System.out.println(output);
    }

    public static void output(String output, int checkNextLine) {
        System.out.print(output);
    }

    public static void output(String output, char checkAlert) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(output);
        switch (checkAlert){
            case 'e':
                alert.setTitle("Error in building drop");
                alert.showAndWait();
            case 'a':
                alert.setTitle("Error in changing government rates");
                alert.showAndWait();
        }


    }
}