package Server.Model.Chat;

import Client.Controller.Controller;
import Client.Model.Chat.Message;
import Client.Model.User;
import jakarta.xml.bind.annotation.XmlElement;

import java.util.ArrayList;

public class Chat {
    public String name;
    private int id;
    ArrayList<Message> messages = new ArrayList<>();
    ArrayList<User> users  = new ArrayList<>();

    @XmlElement
    public void setId(int setterId) {
        this.id = setterId;
    }

    public int getId() {
        return id;
    }

    public static ArrayList<Chat> allChats = new ArrayList<>();

    public static Chat publicChat = new Chat();
    static {
        publicChat.setUsers(Controller.users);
        publicChat.name = "public chat";
        allChats.add(publicChat);
    }

    public Chat() {}

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public static void debugKonesh(){
        for (Chat chat:allChats){
            for (User user:chat.getUsers()){
                System.out.print(user.getUsername()+" ");
            }
        }
    }
}
