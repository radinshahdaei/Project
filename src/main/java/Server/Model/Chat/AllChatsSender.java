package Server.Model.Chat;

import Client.Model.Chat.Chat;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;

@XmlRootElement
public class AllChatsSender {

    private ArrayList<Client.Model.Chat.Chat> chats;

    public AllChatsSender(){}

    public AllChatsSender(String nothing) {
        this.chats = Client.Model.Chat.Chat.allChats;
        System.out.println("Chats: "+chats);
    }

    @XmlElement
    public ArrayList<Client.Model.Chat.Chat> getChats() {
        return chats;
    }

    public void setChats(ArrayList<Chat> chats) {
        this.chats = chats;
    }
}
