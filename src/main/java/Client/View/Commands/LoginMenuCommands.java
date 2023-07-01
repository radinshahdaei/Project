package Client.View.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum LoginMenuCommands {
    BACK("\\s*back\\s*"),
    LOGIN("\\s*user\\s+login(?=.*-u (?<username>\"[^\"]+\"|\\S*))(?=.*-p (?<password>\"[^\"]+\"|\\S*))(?=.*(?<stayLoggedIn>--stay-logged-in))?.*"),
    FORGOT_PASSWORD("\\s*forgot\\s+my\\s+password\\s*");
    String regex;

    private LoginMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String input, LoginMenuCommands loginMenuCommand) {
        Matcher matcher = Pattern.compile(loginMenuCommand.regex).matcher(input);
        if (matcher.matches()) {
            return matcher;
        }
        return null;
    }
}