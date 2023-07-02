package Client;

import Client.Controller.Controller;
import Client.Controller.ManageData;
import Client.Model.Chat.AllChatsSender;
import Client.Model.Chat.Chat;
import Client.Model.User;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {
    Socket socket;
    boolean authenticated = false;
    public static void main(String[] args) throws IOException, JAXBException {
        Client client = new Client();
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public Client() throws IOException, JAXBException {
        Runnable runnable = () -> {
            try {
                runClient(this);
            } catch (IOException | JAXBException e) {
                throw new RuntimeException(e);
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    public void runClient(Client client) throws IOException, JAXBException {
        System.out.println("Starting client..");
        Controller.setClient(client);
        this.socket = new Socket("localhost",8002);
        InputStream inputStream = socket.getInputStream();
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        Scanner scanner = new Scanner(new InputStreamReader(inputStream));
        String line;

        sendUser();


        while (true) {
            StringBuilder xmlBuilder = new StringBuilder();
            while (!(line = in.readLine()).contains("<<CLASS>>==")) {
                if (line.equals("<<UPDATE_DATA_BASE>>")) ;
                else xmlBuilder.append(line);
            }
            String xmlData = xmlBuilder.toString();
            String function = line.replaceAll("<<CLASS>>==","").replaceAll("\"","");
            if (function.equals("updateChat")) handleChat(xmlData);
            // else if (function.equals("nextTurn")) handleNextTurn(xmlData);
        }
    }

    public void handleChat(String xmlData) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(AllChatsSender.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        StringReader reader = new StringReader(xmlData);
        AllChatsSender allChatsSender = (AllChatsSender) unmarshaller.unmarshal(reader);
        checkAndSetChats(allChatsSender.getChats());
    }

    public void checkAndSetChats(ArrayList<Chat> updatedChats){
        boolean flag = false;
        ArrayList<Chat> chatsToAdd = new ArrayList<>();
        for (Chat updatedChat:updatedChats){
            for (Chat chat:Chat.allChats){
                if (chat.getId() == updatedChat.getId() || (updatedChat.name.equals("public chat") && chat.name.equals("public chat"))){
                    chat.setMessages(updatedChat.getMessages());
                    flag = true;
                }
            }
            if (!flag) chatsToAdd.add(updatedChat);
        }
        Chat.allChats.addAll(chatsToAdd);
    }

    public void handleNextTurn(String xmlData){}

    public void sendChats() throws IOException {
        OutputStream outputStream = socket.getOutputStream();
        PrintWriter out = new PrintWriter(outputStream, true);
        out.println(SaveAsXML.getWriter(new AllChatsSender("nothing")));
        out.println("<<CLASS>>==\"updateChat\"");
    }

    public void sendUser() throws IOException{
        InputStream inputStream = socket.getInputStream();
        OutputStream outputStream = socket.getOutputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        PrintWriter out = new PrintWriter(outputStream, true);
        out.println(Controller.currentUser.getUsername());
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String messageFromServer = bufferedReader.readLine();
            if (messageFromServer.equals("<<SEND_TOKEN>>")) {
                System.out.println("Type your private token:");
                out.println(scanner.nextLine());
            } else if (messageFromServer.equals("<<RECEIVE_TOKEN>>")) {
                System.out.println("Your private token: "+bufferedReader.readLine());
            } else if (messageFromServer.equals("<<SUCCESS>>")) break;
        }
        authenticated = true;
    }

    public void updateDatabase() throws IOException{
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String jsonString = bufferedReader.readLine();
        String jsonFilePath = "src/main/java/Client/Controller/Data/users.json";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(jsonFilePath))) {
            writer.write(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<User> updatedUsers = ManageData.loadUsers();
        ArrayList<User> usersToAdd = new ArrayList<>();
        for (User updatedUser : updatedUsers){
            boolean flag = false;
            for (User user : Controller.users){
                if (user.getUsername().equals(updatedUser.getUsername())) {
                    flag = true;
                    break;
                }
            }
            if (!flag) usersToAdd.add(updatedUser);
        }
        Controller.users.addAll(usersToAdd);
    }

    public void sendDatabase() throws IOException{
        OutputStream outputStream = socket.getOutputStream();
        String jsonFilePath = "src/main/java/Client/Controller/Data/users.json";
        String jsonContent = new String(Files.readAllBytes(Paths.get(jsonFilePath)));
        PrintWriter out = new PrintWriter(outputStream,true);
        out.println("<<UPDATE_DATA_BASE>>");
        out.println(jsonContent);
    }

    public void setOnlineClients() {

    }
}
