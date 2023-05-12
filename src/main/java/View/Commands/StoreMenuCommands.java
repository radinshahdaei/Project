package View.Commands;

import View.Game.StoreMenu;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum StoreMenuCommands {
    BUY("\\s*buy\\s+(?=.*-i (?<name>\\\"[^\\\"]+\\\"|\\S*))(?=.*-a (?<amount>\\\"[^\\\"]+\\\"|\\S*)).*"),
    SELL("\\s*sell\\s+(?=.*-i (?<name>\\\"[^\\\"]+\\\"|\\S*))(?=.*-a (?<amount>\\\"[^\\\"]+\\\"|\\S*)).*");

    String regex;
    private StoreMenuCommands(String regex) {this.regex = regex;}

    public static Matcher getMatcher(String input , StoreMenuCommands storeMenuCommand) {
        Matcher matcher = Pattern.compile(storeMenuCommand.regex).matcher(input);
        return (matcher.matches() ? matcher : null);
    }
}
