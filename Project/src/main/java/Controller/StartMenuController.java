package Controller;

public class StartMenuController {
    public static void logout() {
        Controller.currentUser = null;
        Controller.stayLoggedIn = false;
    }
    public static void exit() {
        if (!Controller.stayLoggedIn) Controller.currentUser = null;
        ManageData.saveUsers();
        ManageData.saveCurrentUser();
        return;
    }
}
