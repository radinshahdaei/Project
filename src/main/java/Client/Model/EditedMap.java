package Client.Model;

import java.util.ArrayList;

public class EditedMap {
    private User user;
    private ArrayList<String> commands;

    public EditedMap(User user, ArrayList<String> commands) {
        this.user = user;
        this.commands = commands;
    }

    public User getUser() {
        return user;
    }

    public ArrayList<String> getCommands() {
        return commands;
    }
}
