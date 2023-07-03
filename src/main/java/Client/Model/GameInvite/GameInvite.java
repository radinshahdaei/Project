package Client.Model.GameInvite;

import java.util.ArrayList;

public class GameInvite {
    String adminId;
    ArrayList<String> allUsersId = new ArrayList<>();

    public static ArrayList<GameInvite> allGameInvites = new ArrayList<>();

    public GameInvite(){}

    public GameInvite(String adminId) {
        this.adminId = adminId;
        allUsersId.add(adminId);
        allGameInvites.add(this);
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public ArrayList<String> getAllUsersId() {
        return allUsersId;
    }

    public void setAllUsersId(ArrayList<String> allUsersId) {
        this.allUsersId = allUsersId;
    }

    public static ArrayList<GameInvite> getAllGameInvites() {
        return allGameInvites;
    }

    public static void setAllGameInvites(ArrayList<GameInvite> allGameInvites) {
        GameInvite.allGameInvites = allGameInvites;
    }

    public void joinGame(String id){
        if (allUsersId.size() < 5 && !allUsersId.contains(id)) allUsersId.add(id);
    }
}
