package Client.View.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
public enum GovernmentMenuCommands {
    FOODRATE("\\s*food\\s+rate\\s+-r\\s+(?<rate>-?\\d+)\\s*"),
    TAXRATE("\\s*tax\\s+rate\\s+-r\\s+(?<rate>-?\\d+)\\s*"),
    FEARRATE("\\s*fear\\s+rate\\s+-r\\s+(?<rate>-?\\d+)\\s*");

    String regex;
    private GovernmentMenuCommands(String regex) {this.regex = regex;}

    public static Matcher getMatcher(String input , GovernmentMenuCommands govermentMenuCommand) {
        Matcher matcher = Pattern.compile(govermentMenuCommand.regex).matcher(input);
        return (matcher.matches() ? matcher : null);
    }
}
