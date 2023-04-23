import Controller.RegisterMenuController;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
class Test2 {
    @Test
    void testUsernameFormat() {
        RegisterMenuController obj = new RegisterMenuController();
        boolean result = obj.checkPassword("Password123!", "Password123!", null);
        assertEquals(true, result);
    }
}
