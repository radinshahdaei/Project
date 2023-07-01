import Client.Controller.LoginMenuController;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;


public class TestLoginMenu {
    @Test
    public void testCheckEmptyField() {
        boolean result = LoginMenuController.checkEmptyField(null, "password");
        Assertions.assertEquals(false, result);
    }
    @Test
    public void testCheckEmptyField2() {
        boolean result = LoginMenuController.checkEmptyField("username", "password");
        Assertions.assertEquals(true, result);
    }
    @Test
    public void testCheckUserPass() throws InterruptedException {
        boolean result = LoginMenuController.checkUserPass("alireza", "Password123", null);
        Assertions.assertEquals(false, result);
    }

}
