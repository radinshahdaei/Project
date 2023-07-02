package Server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import jakarta.xml.bind.JAXBException;

import java.io.*;
import java.lang.reflect.Type;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Server {
    public ArrayList<Socket> clients = new ArrayList<>();
    public HashMap<String,String> usernameTokenMap;
    public HashMap<String,Socket> usernameSocketMap = new HashMap<>();
    public HashMap<String,String> usernameLastSeenMap = new HashMap<>();

    public static void main(String[] args) {
        Server server = new Server(8002);
    }
    public Server(int port) {
        usernameTokenMap = loadTokens();
        if (usernameTokenMap == null) usernameTokenMap = new HashMap<>();
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
                        clients.remove(socket);
                        usernameSocketMap.remove(socket);
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

        String username = in.readLine();
        if (!usernameTokenMap.containsKey(username)) sendToken(socket,username);
        else receiveToken(socket,username,in);
        usernameSocketMap.put(username,socket);
        saveTokens(usernameTokenMap);

        LocalTime currentTime = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedTime = currentTime.format(formatter);
        usernameLastSeenMap.put(username,formattedTime);


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
                updateDatabase(inputStream,in);
            }
        }

    }

    public void receiveToken(Socket socket,String username,BufferedReader in) throws IOException {
        InputStream inputStream = socket.getInputStream();
        OutputStream outputStream = socket.getOutputStream();
        PrintWriter out = new PrintWriter(outputStream,true);
        while (true){
            out.println("<<SEND_TOKEN>>");
            if (in.readLine().equals(usernameTokenMap.get(username))) {
                out.println("<<SUCCESS>>");
                break;
            }
        }
    }

    public void sendToken(Socket socket,String username) throws IOException {
        InputStream inputStream = socket.getInputStream();
        OutputStream outputStream = socket.getOutputStream();
        PrintWriter out = new PrintWriter(outputStream,true);
        String token = String.valueOf(LocalTime.now().getNano());
        out.println("<<RECEIVE_TOKEN>>\n"+token+"\n<<SUCCESS>>");
        usernameTokenMap.put(username,token);
    }

    public void updateDatabase(InputStream inputStream,BufferedReader in) throws IOException {
        String jsonString = in.readLine();
        System.out.println(jsonString);
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

    public static void saveTokens(HashMap<String,String> usernameTokenMap){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonString = gson.toJson(usernameTokenMap);
        String filePath = "src/main/java/Server/Data/userToken.json";
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


}
