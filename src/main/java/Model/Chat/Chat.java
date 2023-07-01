package Model.Chat;

import Controller.Controller;
import Model.User;

import java.util.ArrayList;

public class Chat {
    public String name;
    ArrayList<Message> messages = new ArrayList<>();
    ArrayList<User> users  = new ArrayList<>();

    public static ArrayList<Chat> allChats = new ArrayList<>();

    public static Chat publicChat = new Chat();
    static {
        publicChat.setUsers(Controller.users);
        publicChat.name = "public chat";
        allChats.add(publicChat);
    }




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
