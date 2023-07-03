package Client.View;

import javafx.scene.control.Alert;

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
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setContentText(output);
        Alert inform = new Alert(Alert.AlertType.INFORMATION);
        inform.setContentText(output);
        switch (checkAlert){
            case 'a':
                error.setTitle("Error in changing government rates");
                error.showAndWait();
                return;
            case 'b':
                error.setTitle("Error in adding Governments");
                error.showAndWait();
                return;
            case 'c':
                inform.setTitle("Government addition");
                inform.showAndWait();
                return;
            case 'd':
                error.setTitle("Error in unit drop");
                error.showAndWait();
                return;
            case 'e':
                error.setTitle("Error in building drop");
                error.showAndWait();
                return;
            case 'f':
                error.setTitle("Error in Unit menu");
                error.showAndWait();
                return;
            case 'g':
                inform.setTitle("Attacking troops");
                inform.showAndWait();
                return;
            case 'h':
                inform.setTitle("Turn switch");
                inform.showAndWait();
                return;
            case 'i':
                error.setTitle("Error in unit selection");
                error.showAndWait();
                return;
            case 'j':
                error.setTitle("Error in editing map");
                error.showAndWait();
                return;
            case 'k':
                inform.setTitle("Editing map");
                inform.showAndWait();
                return;
            case 'l':
                error.setTitle("Error in copying buildings");
                error.showAndWait();
                return;
            case 'm':
                inform.setTitle("Building copy");
                inform.showAndWait();
                return;
            case 'n':
                inform.setTitle("Building paste");
                inform.showAndWait();
        }


    }
}