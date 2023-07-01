package Server.View.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum GameMenuCommands {
    END("\\s*end\\s*"),
    DONE("\\s*Done\\s*"),
    ADD_USER("\\s*add\\s+(?<username>\\S+)\\s*"),
    DROP_BUILDING("\\s*dropbuilding\\s+(?=.*-x (?<X>\\d+))(?=.*-y (?<Y>\\d+))(?=.*-t (?<type>\"[^\"]+\"|\\S*)).*"),
    DROP_STAIRS("\\s*dropstairs\\s+(?=.*-x (?<X>\\d+))(?=.*-y (?<Y>\\d+)).*"),
    CLEAR("\\s*clear\\s+(?=.*-x (?<X>\\d+))(?=.*-y (?<Y>\\d+)).*"),
    NEXT_TURN("\\s*next\\s+turn\\s*"),
    SELECT_BUILDING("\\s*select\\s+building\\s+(?=.*-x (?<X>\\d+))(?=.*-y (?<Y>\\d+)).*"),
    DRAW_MAP("\\s*draw\\s+map\\s+(?=.*-x (?<X>\\d+))(?=.*-y (?<Y>\\d+)).*"),
    SHOW_RESOURCES("\\s*show\\s+resources\\s*"),
    DROP_UNIT("\\s*dropunit\\s+(?=.*-x (?<X>\\d+))(?=.*-y (?<Y>\\d+))(?=.*-t (?<type>\"[^\"]+\"|\\S*))(?=.*-c (?<count>\\d+)).*"),
    SELECT_UNIT("\\s*select\\s+unit\\s+(?=.*-x (?<X>\\d+))(?=.*-y (?<Y>\\d+))(?=.*-t (?<type>\"[^\"]+\"|\\S*)).*"),
    CANCEL_PATROL("\\s*cancel\\s+patrol\\s+(?=.*-x (?<X>\\d+))(?=.*-y (?<Y>\\d+)).*"),
    TRADE("\\s*enter\\s+trade\\s+menu\\s*"),
    STORE("\\s*enter\\s+store\\s+menu\\s*");
    String regex;

    private GameMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String input, GameMenuCommands loginMenuCommand) {
        Matcher matcher = Pattern.compile(loginMenuCommand.regex).matcher(input);
        if (matcher.matches()) {
            return matcher;
        }
        return null;
    }
}
