package Model.Chat;

import Model.User;

import java.util.ArrayList;

public class Chat {
    ArrayList<Message> messages = new ArrayList<>();
    ArrayList<User> users  = new ArrayList<>();

    public static ArrayList<Chat> allChats = new ArrayList<>();

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
}
