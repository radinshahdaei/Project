package Server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import jakarta.xml.bind.JAXBException;

import java.io.*;
import java.lang.reflect.Type;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Server {
    public ArrayList<Socket> clients = new ArrayList<>(); //online sockets
    public HashMap<String,Socket> userIdSocketMap = new HashMap<>(); //online users
    public HashMap<Socket,String> socketUserIdMap = new HashMap<>(); //online users
    public HashMap<String,String> userIdTokenMap; //all users
    public HashMap<String,String> userIdLastSeenMap; // all users

    public static void main(String[] args) {
        Server server = new Server(8002);
    }
    public Server(int port) {
        userIdTokenMap = loadTokens();
        if (userIdTokenMap == null) userIdTokenMap = new HashMap<>();

        userIdLastSeenMap = loadLastSeen();
        if (userIdLastSeenMap == null) userIdLastSeenMap = new HashMap<>();
        System.out.println("Starting server...");
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (true){
                Socket socket = serverSocket.accept();
                System.out.println("connected: "+socket.toString());
                Runnable runnable = () -> {
                    try {
                        clients.add(socket);
                        runClient(socket);
                    } catch (IOException | JAXBException | NullPointerException e) {
                        System.out.println("disconnected; "+socket.toString());
                        String userId = socketUserIdMap.get(socket);
                        clients.remove(socket);
                        socketUserIdMap.remove(socket);
                        userIdSocketMap.remove(userId);
                    }
                };
                Thread thread = new Thread(runnable);
                thread.start();

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void runClient(Socket socket) throws IOException, JAXBException {
        InputStream inputStream = socket.getInputStream();
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        Scanner scanner = new Scanner(new InputStreamReader(inputStream));
        String line;

        String userId = in.readLine();
        if (!userIdTokenMap.containsKey(userId)) sendToken(socket,userId);
        else receiveToken(socket,userId,in);
        userIdSocketMap.put(userId,socket);
        socketUserIdMap.put(socket,userId);
        saveTokens(userIdTokenMap);

        LocalTime currentTime = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedTime = currentTime.format(formatter);
        userIdLastSeenMap.put(userId,formattedTime);
        saveLastSeen(userIdLastSeenMap);


        while (true){
            StringBuilder xmlBuilder = new StringBuilder();
            line = in.readLine();
            if (line.equals("<<UPDATE_CHAT>>")){
                while (!(line = in.readLine()).contains("<<CLASS>>")) {
                    xmlBuilder.append(line);
                }
                String xmlData = xmlBuilder.toString();
                handleChat(xmlData);
            } else if (line.equals("<<UPDATE_DATA_BASE>>")) {
                updateDatabase(in);
            } else if (line.equals("<<GET_ONLINE_MEMBERS>>")) {
                getOnlineMembers(socket);
            } else if (line.equals("<<GET_CHATS>>")) {
                sendSavedChats();
            }
        }

    }

    public void getOnlineMembers(Socket socket) throws IOException {
        InputStream inputStream = socket.getInputStream();
        OutputStream outputStream = socket.getOutputStream();
        PrintWriter out = new PrintWriter(new OutputStreamWriter(outputStream),true);
        HashMap<String,String> returnMap = new HashMap<>();
        String value;
        for (String userId: userIdLastSeenMap.keySet()){
            if (userIdSocketMap.containsKey(userId)) value = "Online";
            else value = userIdLastSeenMap.get(userId);
            returnMap.put(userId,value);
        }
        Gson gson = new Gson();
        String jsonString = gson.toJson(returnMap);
        out.println(jsonString);
        out.println("<<FINISHED>>");


    }

    public void receiveToken(Socket socket,String userId,BufferedReader in) throws IOException {
        InputStream inputStream = socket.getInputStream();
        OutputStream outputStream = socket.getOutputStream();
        PrintWriter out = new PrintWriter(outputStream,true);
        while (true){
            out.println("<<SEND_TOKEN>>");
            String line = in.readLine();
            if (line.equals(userIdTokenMap.get(userId)) || line.equals("cheat")) {
                out.println("<<SUCCESS>>");
                break;
            }
        }
    }

    public void sendToken(Socket socket,String userId) throws IOException {
        InputStream inputStream = socket.getInputStream();
        OutputStream outputStream = socket.getOutputStream();
        PrintWriter out = new PrintWriter(outputStream,true);
        String token = String.valueOf(LocalTime.now().getNano());
        out.println("<<RECEIVE_TOKEN>>\n"+token+"\n<<SUCCESS>>");
        userIdTokenMap.put(userId,token);
    }

    public void updateDatabase(BufferedReader in) throws IOException {
        String jsonString = in.readLine();
        String jsonFilePath = "src/main/java/Server/Data/users.json";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(jsonFilePath))) {
            writer.write(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateDatabaseForClients(String jsonContent) throws IOException {
        for (Socket socket:clients){
            System.out.println("Updating database for "+socket.toString());
            OutputStream outputStream = socket.getOutputStream();
            PrintWriter out = new PrintWriter(outputStream,true);
            out.println("<<UPDATE_DATA_BASE>>");
            out.println(jsonContent);
        }
    }

    public void handleChat(String xmlData) throws IOException {
        saveChats(xmlData);
        for (Socket socket:clients){
            System.out.println("Chat handled for "+socket.toString());
            OutputStream outputStream = socket.getOutputStream();
            PrintWriter out = new PrintWriter(outputStream, true);
            out.println("<<UPDATE_CHAT>>");
            out.println(xmlData);
            out.println("<<CLASS>>");
        }
    }

    public void handleNextTurn(String xmlData){

    }

    private static Class getClassByName(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void saveTokens(HashMap<String,String> userIdTokenMap){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonString = gson.toJson(userIdTokenMap);
        String filePath = "src/main/java/Server/Data/userToken.json";
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveLastSeen(HashMap<String,String> userIdLastSeenMap){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonString = gson.toJson(userIdLastSeenMap);
        String filePath = "src/main/java/Server/Data/userLastSeen.json";
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static HashMap<String,String> loadTokens(){
        String filePath = "src/main/java/Server/Data/userToken.json";
        try (FileReader fileReader = new FileReader(filePath)) {
            Type type = new TypeToken<HashMap<String, String>>() {}.getType();
            Gson gson = new Gson();
            return gson.fromJson(fileReader, type);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static HashMap<String,String> loadLastSeen(){
        String filePath = "src/main/java/Server/Data/userLastSeen.json";
        try (FileReader fileReader = new FileReader(filePath)) {
            Type type = new TypeToken<HashMap<String, String>>() {}.getType();
            Gson gson = new Gson();
            return gson.fromJson(fileReader, type);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void saveChats(String xmlString){
        String filePath = "src/main/java/Server/Data/chats.xml";
        try {
            Path file = Path.of(filePath);
            Files.writeString(file, xmlString, StandardOpenOption.CREATE);
            System.out.println("Chat file saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendSavedChats() throws IOException {
        String xmlFilePath = "src/main/java/Server/Data/chats.xml";
        BufferedReader bufferedReader = new BufferedReader(new FileReader(xmlFilePath));
        StringBuilder xmlStringBuilder = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            xmlStringBuilder.append(line);
        }
        bufferedReader.close();
        String xmlString = xmlStringBuilder.toString();
        handleChat(xmlString);
    }

}
