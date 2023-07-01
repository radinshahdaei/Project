package Client.Model.Chat;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;

@XmlRootElement
public class AllChatsSender {

    private ArrayList<Chat> chats;

    public AllChatsSender(){}

    public AllChatsSender(String nothing) {
        this.chats = Chat.allChats;
        System.out.println("Chats: "+chats);
    }

    @XmlElement
    public ArrayList<Chat> getChats() {
        return chats;
    }

    public void setChats(ArrayList<Chat> chats) {
        this.chats = chats;
    }
}
