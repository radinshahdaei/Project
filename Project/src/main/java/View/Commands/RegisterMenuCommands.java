package View.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum RegisterMenuCommands {
    BACK("\\s*back\\s*"),
    REGISTER("\\s*user\\s+create(?=.*-u \"?(?<username>[^\"]+|\\S*)\"?)(?=.*-n \"?(?<nickname>[^\"]+|\\S*)\"?)(?=.*-p ((?<passwordRandom>random)|\"?(?<password>[^\"]+|\\S*)\"? \"?(?<passwordConfirmation>[^\"]+|\\S*)\"?))(?=.*-email \"?(?<email>[^\"]+|\\S*)\"?)(?=.*(?<containsSlogan>-s ((?<sloganRandom>random)|\"?(?<slogan>[^\"]+|\\S*)\"?)))?.*"),
    SECURITY_QUESTION("\\s*question\\s+pick(?=.*-q \"?(?<questionNumber>[^\"]+|\\S*)\"?)(?=.*-a \"?(?<answer>[^\"]+|\\S*)\"? \"?(?<answerConfirmation>[^\"]+|\\S*)\"?).*");
    String regex;

    private RegisterMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String input, RegisterMenuCommands loginMenuCommand) {
        Matcher matcher = Pattern.compile(loginMenuCommand.regex).matcher(input);
        if (matcher.matches()) {
            return matcher;
        }
        return null;
    }
}