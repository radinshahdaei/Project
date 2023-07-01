package Server.View.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum TradeMenuCommands {
    TRADE("\\s*trade\\s+(?=.*-t (?<resourceType>\"[^\"]+\"|\\S*))(?=.*-a (?<resourceAmount>\"[^\"]+\"|\\S*))(?=.*-p (?<price>\"[^\"]+\"|\\S*))(?=.*-m (?<message>\"[^\"]+\"|\\S*))(?=.*-u (?<username>\"[^\"]+\"|\\S*)).*"),
    LIST("\\s*trade\\s+list\\s*"),
    ACCEPT("\\s*trade\\s+accept\\s+(?=.*-i (?<id>\"[^\"]+\"|\\S*))(?=.*-m (?<message>\"[^\"]+\"|\\S*)).*"),
    HISTORY("\\s*trade\\s+history\\s*");

    String regex;
    private TradeMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String input , TradeMenuCommands tradeMenuCommand) {
        Matcher matcher = Pattern.compile(tradeMenuCommand.regex).matcher(input);
        return (matcher.matches() ? matcher : null);
    }
}
