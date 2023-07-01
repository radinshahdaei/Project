package Client;

import Client.Controller.Controller;
import Client.Model.Chat.AllChatsSender;
import Client.Model.Chat.Chat;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {
    Socket socket;
    public static void main(String[] args) throws IOException, JAXBException {
        Client client = new Client();
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
        System.out.println("Starting client...");
        Controller.setClient(client);
        this.socket = new Socket("localhost",8001);
        InputStream inputStream = socket.getInputStream();
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        Scanner scanner = new Scanner(new InputStreamReader(inputStream));
        String line;


        while (true) {
            StringBuilder xmlBuilder = new StringBuilder();
            while (!(line = in.readLine()).contains("<<CLASS>>==")) {
                xmlBuilder.append(line);
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
}
