package View.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum MapMenuCommands{
    END_MAP("\\s*end\\s+map\\s*"),
    SET_TEXTURE("\\s*settexture\\s+(?=.*-x (?<X>\\d+))(?=.*-y (?<Y>\\d+))(?=.*-t (?<type>\\S+)).*"),
    SET_TEXTURE_REC("\\s*settexture\\s+(?=.*-x1 (?<X1>\\d+))(?=.*-x2 (?<X2>\\d+))(?=.*-y1 (?<Y1>\\d+))" +
            "(?=.*-y2 (?<Y2>\\d+))(?=.*-t (?<type>\\S+)).*"),
    CLEAR("\\s*clear\\s+(?=.*-x (?<X>\\d+))(?=.*-y (?<Y>\\d+)).*"),
    DRAW("\\s*draw\\s+map\\s*"),
    END_SHOW_MAP("\\s*end\\s+show\\s+map\\s*"),
    MAP_MOVE("\\s*map\\s+move\\s+(?=.*(?<up>up\\s*(?<amountUp>\\d+)?))?(?=.*(?<down>down\\s*(?<amountDown>" +
            "\\d+)?))?(?=.*(?<left>left\\s*(?<amountLeft>\\d+)?))?(?=.*(?<right>right\\s*(?<amountRight>\\d+)?))?.*"),
    SHOW_DETAILS("\\s*show\\s+details\\s+(?=.*-x (?<X>\\d+))(?=.*-y (?<Y>\\d+)).*"),
    DROP_BUILDING("\\s*dropbuilding\\s+(?=.*-x (?<X>\\d+))(?=.*-y (?<Y>\\d+))(?=.*-t (?<type>\"[^\"]+\"|\\S*)).*"),
    DROP_ROCK("\\s*droprock\\s+(?=.*-x (?<X>\\d+))(?=.*-y (?<Y>\\d+))(?=.*-d (?<direction>\\w)).*"),
    DROP_TREE("\\s*droptree\\s+(?=.*-x (?<X>\\d+))(?=.*-y (?<Y>\\d+))(?=.*-t (?<type>\"[^\"]+\"|\\S*)).*");
    String regex;
    private MapMenuCommands(String regex) {this.regex = regex;}
    public static Matcher getMatcher(String input, MapMenuCommands loginMenuCommand) {
        java.util.regex.Matcher matcher = Pattern.compile(loginMenuCommand.regex).matcher(input);
        if (matcher.matches()) {
            return matcher;
        }
        return null;
    }
}
