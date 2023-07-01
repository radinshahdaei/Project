package Server.Controller;

import Client.Controller.Controller;
import Client.Controller.ManageData;

public class StartMenuController {
    public static void logout() {
        Client.Controller.Controller.currentUser = null;
        Client.Controller.Controller.stayLoggedIn = false;
    }
    public static void exit() {
        if (!Client.Controller.Controller.stayLoggedIn) Controller.currentUser = null;
        Client.Controller.ManageData.saveUsers();
        ManageData.saveCurrentUser();
        return;
    }
}
