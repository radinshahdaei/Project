package Server.View.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ProfileMenuCommands {
    BACK("\\s*back\\s*"),
    EXIT("\\s*exit\\s*"),
    CHANGE_USERNAME("\\s*profile\\s+change\\s+-u\\s+(?<username>\"[^\"]+\"|\\S*)"),
    CHANGE_NICKNAME("\\s*profile\\s+change\\s+-n\\s+(?<nickname>\"[^\"]+\"|\\S*)"),
    CHANGE_PASSWORD("\\s*profile\\s+change\\s+password\\s+(?=.*-o (?<oldPassword>\"[^\"]+\"|\\S*))(?=.*-n (?<newPassword>\"[^\"]+\"|\\S*)).*"),
    CHANGE_EMAIL("\\s*profile\\s+change\\s+-e\\s+(?<email>\"[^\"]+\"|\\S*)"),
    CHANGE_SLOGAN("\\s*profile\\s+change\\s+slogan\\s+-s\\s+(?<slogan>\"[^\"]+\"|\\S*)"),
    REMOVE_SLOGAN("\\s*profile\\s+remove\\s+slogan\\s*"),
    DISPLAY_HIGHSCORE("\\s*profile\\s+display\\s+highscore\\s*"),
    DISPLAY_SLOGAN("\\s*profile\\s+display\\s+slogan\\s*"),
    DISPLAY_RANK("\\s*profile\\s+display\\s+rank\\s*"),
    DISPLAY("\\s*profile\\s+display\\s*");

    String regex;
    private ProfileMenuCommands(String regex) {
        this.regex = regex;
    }
    public static Matcher getMatcher(String input, ProfileMenuCommands loginMenuCommand) {
        Matcher matcher = Pattern.compile(loginMenuCommand.regex).matcher(input);
        if (matcher.matches()) {
            return matcher;
        }
        return null;
    }
}
