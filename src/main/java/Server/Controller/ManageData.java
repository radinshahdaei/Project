package Server.Controller;

import Client.Controller.Controller;
import Client.Controller.GameMenuController;
import Client.Model.Game;
import Client.Model.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Scanner;

public class ManageData {
    public static void saveCurrentUser() {
        String json = new Gson().toJson(Client.Controller.Controller.currentUser);
        try (FileWriter writer = new FileWriter("src/main/java/Client/Controller/Data/currentUser.json")) {
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void saveUsers() {
        String json = new Gson().toJson(Controller.users);
        try (FileWriter writer = new FileWriter("src/main/java/Client/Controller/Data/users.json")) {
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void saveGame() {
        String json = new Gson().toJson(GameMenuController.game);
        try (FileWriter writer = new FileWriter("src/main/java/Client/Controller/Data/game.json")) {
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static Game loadGame() {
        File file = new File("src/main/java/Controller/Data/game.json");
        Scanner reader = null;
        try {
            reader = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assert reader != null;
        String data = "";
        while (reader.hasNextLine()) {
            data += reader.nextLine();
        }
        return new Gson().fromJson(data, Game.class);
    }
    public static User loadCurrentUser() {
        File file = new File("src/main/java/Client/Controller/Data/currentUser.json");
        Scanner reader = null;
        try {
            reader = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assert reader != null;
        String data = "";
        while (reader.hasNextLine()) {
            data += reader.nextLine();
        }
        return new Gson().fromJson(data, User.class);
    }
    public static ArrayList<User> loadUsers() {
        File file = new File("src/main/java/Client/Controller/Data/users.json");
        Scanner reader = null;
        try {
            reader = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assert reader != null;
        String data = "";
        while (reader.hasNextLine()) {
            data += reader.nextLine();
        }
        Type type = new TypeToken<ArrayList<User>>(){}.getType();
        return new Gson().fromJson(data, type);
    }
    public static String encrypt(String originalString) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(originalString.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean decrypt(String encryptedString, String originalString) {
        String decryptedHash = encrypt(originalString);
        return decryptedHash.equals(encryptedString);
    }
}
