package Server.View.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum StartMenuCommands {
    LOGOUT("\\s*logout\\s*"),
    EXIT("\\s*exit\\s*"),
    ENTER_PROFILE_MENU("\\s*enter\\s+profile\\s+menu\\s*"),
    START_GAME("\\s*start\\s+game\\s*");

    String regex;
    private StartMenuCommands(String regex) {
        this.regex = regex;
    }
    public static Matcher getMatcher(String input, StartMenuCommands loginMenuCommand) {
        Matcher matcher = Pattern.compile(loginMenuCommand.regex).matcher(input);
        if (matcher.matches()) {
            return matcher;
        }
        return null;
    }
}
