package Controller;

import Model.User;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static View.InputOutput.input;
import static View.InputOutput.output;

public class RegisterMenuController {
    private static String finalUsername;
    private static String finalPassword;
    private static String finalNickname;
    private static String finalEmail;
    private static String finalSlogan;
    private static String finalAnswer;
    private static int finalQuestionNumber;

    private static void clearFinals() {
        finalUsername = null;
        finalPassword = null;
        finalNickname = null;
        finalEmail = null;
        finalSlogan = null;
        finalAnswer = null;
        finalQuestionNumber = 0;
    }

    // register methods

    public static boolean register(String username, String nickname, String password,
                                   String passwordConfirmation, String passwordRandom,
                                   String email, String slogan, String containsSlogan, String sloganRandom) {
        clearFinals();
        if (!checkEmptyField(nickname, username, email,
                password, passwordConfirmation, passwordRandom,
                containsSlogan, sloganRandom, slogan)) return false;
        if (!checkUsername(username)) return false;
        if (!checkPassword(password, passwordConfirmation, passwordRandom)) return false;
        if (!(checkEmail(email))) return false;
        if (!(checkSlogan(slogan, containsSlogan, sloganRandom))) return false;
        finalNickname = nickname;

        //user create -n nigga -p random -s random -u user -email radin@gmail.com

        return true; //move to security question
    }

    public static boolean securityQuestion(int questionNumber, String answer, String answerConfirmation) {
        if (!checkNumber(questionNumber)) return false;
        if (!checkAnswer(answer, answerConfirmation)) return false;
        captcha();
        User.createUser(finalUsername, finalPassword, finalNickname, finalEmail, finalSlogan, finalAnswer, finalQuestionNumber);
        output("User created successfully!");
        return true;
    }

    public static void captcha() {
        Random rand = new Random();
        String[] digitsAscii = {"  ___  \n / _ \\ \n| | | |\n| |_| |\n \\___/ \n", // ASCII art for 0
                "  __ \n /_ |\n  | |\n  | |\n  | |\n", // ASCII art for 1
                "  ___  \n |__ \\ \n   ) |\n  / / \n |____|\n", // ASCII art for 2
                "  ____  \n |___ \\ \n   __) |\n  |__ < \n  ___\\ \\\n |____/\n", // ASCII art for 3
                " _  _   \n| || |  \n| || |_ \n|__   _|\n   | |  \n   |_|  \n", // ASCII art for 4
                " _____ \n| ____|\n| |__  \n|___ \\ \n ___) |\n|____/ \n", // ASCII art for 5
                "  __  \n / /  \n| |__ \n| '_ \\ \n| | | |\n|_| |_|\n", // ASCII art for 6
                " _____ \n|___ / \n  |_ \\ \n ___) |\n|____/ \n", // ASCII art for 7
                "  ___  \n ( _ ) \n / _ \\ \n| (_) |\n \\___/ \n", // ASCII art for 8
                "  ___  \n / _ \\ \n| (_) |\n \\__, |\n   /_/ \n"}; // ASCII art for 9

        int[] digits = new int[4];


        while (true) {
            for (int i = 0; i < 4; i++) {
                digits[i] = rand.nextInt(10);
            }

            StringBuilder captcha = new StringBuilder();
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 4; j++) {
                    captcha.append(digitsAscii[digits[j]].split("\n")[i]).append(" "); // add the i-th line of the j-th digit's ASCII art
                }
                captcha.append("\n");
            }
            output(captcha.toString());


            output("Enter the captcha: ");
            String userInput = input();

            if (userInput.equals(String.valueOf(digits[0]) + String.valueOf(digits[1]) + String.valueOf(digits[2]) + String.valueOf(digits[3]))) {
                output("Captcha verified.");
                return;
            }
            output("Incorrect captcha.");
        }
    }

    public static boolean checkNumber(int questionNumber) {
        if (questionNumber > 3 || questionNumber < 1) {
            output("Invalid question number!");
            return false;
        }
        finalQuestionNumber = questionNumber;
        return true;
    }

    public static boolean checkAnswer(String answer, String answerConfirmation) {
        if (!answer.equals(answerConfirmation)) {
            output("Answer and it's confirmation do not match!");
            return false;
        }
        finalAnswer = answer;
        return true;
    }

    public static boolean checkEmptyField(String nickname, String username, String email,
                                          String password, String passwordConfirmation, String passwordRandom,
                                          String containsSlogan, String sloganRandom, String slogan) {
        if (nickname == null || username == null || email == null) {
            output("Empty field!");
            return false;
        } else if ((password == null || passwordConfirmation == null) && passwordRandom == null) {
            output("Empty field!");
            return false;
        } else if (containsSlogan != null && sloganRandom == null && slogan == null) {
            output("Empty field!");
            return false;
        }
        return true;
    }

    public static boolean checkUsername(String username) {
        if (!checkUsernameFormat(username)) {
            output("Invalid username format");
            return false;
        }

        if (Controller.findUserByUsername(username) != null) {
            String newUsername = newUsername(username);
            output("Username already exists, you can use " + newUsername + " instead!\n" +
                    "chose an option: 1) confirm    2) decline");
            String input = input();
            if (input.equals("1")) {
                output("Confirmed!");
                username = newUsername;
            } else {
                output("Declined!");
                return false;
            }
        }
        finalUsername = username;
        return true;
    }

    public static boolean checkPassword(String password, String passwordConfirmation, String passwordRandom) {
        if (passwordRandom != null) {
            password = randomPassword();
            output("Your random password is: " + password + "\n" +
                    "Please re-enter your password here:");
            passwordConfirmation = input();
        }
        String passwordStrength = checkPasswordStrength(password);
        if (!passwordStrength.equals("success")) {
            output(passwordStrength);
            return false;
        }
        if (!password.equals(passwordConfirmation)) {
            output("Password and it's confirmation do not match!");
            return false;
        }
        finalPassword = password;
        return true;
    }

    public static boolean checkEmail(String email) {
        if (Controller.findUserByEmail(email) != null) {
            output("A user with this email already exists!");
            return false;
        }
        if (!checkEmailFormat(email)) {
            output("Invalid email!");
            return false;
        }
        finalEmail = email;
        return true;
    }

    public static boolean checkSlogan(String slogan, String sloganRandom, String containsSlogan) {
        if (sloganRandom != null && slogan == null) {
            slogan = randomSlogan();
            output("Your slogan is \"" + slogan + "\"");
        }

        if (containsSlogan == null) {
            slogan = "";
        }

        finalSlogan = slogan;
        System.out.println(slogan);
        return true;
    }

    // secondary methods

    public static boolean checkUsernameFormat(String username) {
        Matcher matcher = Pattern.compile("[a-zA-Z0-9_]+").matcher(username);
        return matcher.matches();
    }

    public static boolean checkEmailFormat(String email) {
        Matcher matcher = Pattern.compile("[\\w._]+@[\\w._]+\\.[\\w._]{2,}").matcher(email);
        return matcher.matches();
    }

    public static String newUsername(String username) {
        double buffer;
        int number;
        String newUsername = username;
        while (true) {
            buffer = Math.random() * 10;
            number = (int) buffer;
            newUsername += String.valueOf(number);
            if (Controller.findUserByUsername(newUsername) == null) break;
        }
        return newUsername;
    }

    public static String checkPasswordStrength(String password) {
        String strength = checkStrength(password);
        if (!strength.equals("")) return ("weak password, errors: " + strength + "!");
        return "success";
    }

    public static String checkStrength(String password) {
        String output = "";
        if (password.length() < 6) output += "[short length]";
        if (!password.matches(".*[a-z].*")) output += "[no lowercase letters]";
        if (!password.matches(".*[A-Z].*")) output += "[no uppercase letters]";
        if (!password.matches(".*[0-9].*")) output += "[no numbers]";
        if (!password.matches(".*[?!@#$%^&*].*")) output += "[no special characters]";
        return output;
    }

    public static String randomPassword() {
        SecureRandom random = new SecureRandom();
        String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789?!@#$%^&*";
        StringBuilder sb = new StringBuilder();

        while (!checkPasswordStrength(sb.toString()).equals("success")) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            sb.append(randomChar);
        }

        return sb.toString();
    }

    public static String randomSlogan() {
        Random random = new Random();
        int index = random.nextInt(5);
        ArrayList<String> slogans = new ArrayList<>();
        slogans.add("Answer the call of the Holy Land and join the Crusade.");
        slogans.add("Experience the thrill of medieval warfare as a Crusader.");
        slogans.add("Fight for faith and glory in the name of the Cross.");
        slogans.add("Become a legend in a battle for Jerusalem and beyond.");
        slogans.add("Join the ranks of the brave and the faithful on a journey to the Holy Land.");
        return slogans.get(index);
    }
}