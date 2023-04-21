package View.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum GameMenuCommands {
    END("\\s*end\\s*");
    String regex;
    private GameMenuCommands(String regex) {this.regex = regex;}
    public static Matcher getMatcher(String input, GameMenuCommands loginMenuCommand) {
        Matcher matcher = Pattern.compile(loginMenuCommand.regex).matcher(input);
        if (matcher.matches()) {
            return matcher;
        }
        return null;
    }
}
