package Client.Model.GameInvite;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;

@XmlRootElement
public class GameInvitesSender {
    ArrayList<GameInvite> gameInvites;

    public GameInvitesSender(){}

    public GameInvitesSender(String nothing) {
        this.gameInvites = GameInvite.allGameInvites;
    }

    @XmlElement
    public ArrayList<GameInvite> getGameInvites() {
        return gameInvites;
    }

    public void setGameInvites(ArrayList<GameInvite> gameInvites) {
        this.gameInvites = gameInvites;
    }
}
