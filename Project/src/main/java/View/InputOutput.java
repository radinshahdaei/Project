package View;

import java.util.Scanner;

public class InputOutput {
    private static final Scanner scanner = new Scanner(System.in);

    public static String input() {
        return scanner.nextLine();
    }

    public static void output(String output) {
        System.out.println(output);
    }
}