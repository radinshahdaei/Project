import Controller.RegisterMenuController;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

class Test1 {
    @Test
    void testCheckPassword() {
        RegisterMenuController obj = new RegisterMenuController();
        String result = obj.checkPasswordStrength("Hello213");
        assertEquals("[no special characters]", result);
    }
}
