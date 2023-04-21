package View.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum MainMenuCommands {
    EXIT("\\s*exit\\s*"),
    REGISTER_MENU("\\s*enter\\s+register\\s+menu\\s*"),
    LOGIN_MENU("\\s*enter\\s+login\\s+menu\\s*");
    String regex;

    private MainMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String input, MainMenuCommands loginMenuCommand) {
        Matcher matcher = Pattern.compile(loginMenuCommand.regex).matcher(input);
        if (matcher.matches()) {
            return matcher;
        }
        return null;
    }
}