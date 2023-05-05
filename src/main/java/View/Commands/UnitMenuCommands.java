package View.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum UnitMenuCommands {
    EXIT("\\s*exit\\s*"),
    MOVE_UNIT("\\s*move\\s+unit\\s+to\\s+(?=.*-x (?<X>\\d+))(?=.*-y (?<Y>\\d+)).*");
    String regex;
    private UnitMenuCommands(String regex) {this.regex = regex;}
    public static Matcher getMatcher(String input, UnitMenuCommands loginMenuCommand) {
        Matcher matcher = Pattern.compile(loginMenuCommand.regex).matcher(input);
        if (matcher.matches()) {
            return matcher;
        }
        return null;
    }
}
