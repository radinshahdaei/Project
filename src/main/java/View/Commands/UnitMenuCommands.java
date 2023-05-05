package View.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum UnitMenuCommands {
    EXIT("\\s*exit\\s*"),
    MOVE_UNIT("\\s*move\\s+unit\\s+to\\s+(?=.*-x (?<X>\\d+))(?=.*-y (?<Y>\\d+)).*"),
    SET_STATUS("\\s*set\\s+(?=.*-s (?<status>standing|defensive|offensive)).*"),
    ATTACK_ENEMY("\\s*attack\\s+-e\\s+(?<X>\\d+)\\s+(?<Y>\\d+)\\s*"),
    ATTACH_ARCHER("\\s*attack\\s+(?=.*-x (?<X>\\d+))(?=.*-y (?<Y>\\d+)).*"),
    DISBAND("\\s*disband\\s+unit\\s*"),
    PATROL_UNIT("\\s*patrol\\s+unit\\s+(?=.*-x1 (?<X1>\\d+))(?=.*-y1 (?<Y1>\\d+))(?=.*-x2 (?<X2>\\d+))(?=.*-y2 (?<Y2>\\d+)).*");
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
