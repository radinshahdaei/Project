import Controller.RegisterMenuController;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class TestRegisterMenu {
    @Test
    public void testCheckNumber() {
        boolean result = RegisterMenuController.checkNumber(2);
        Assertions.assertEquals(true, result);
    }
    @Test
    public void testCheckAnswer() {
        boolean result = RegisterMenuController.checkAnswer("Hello", "Hello1");
        Assertions.assertEquals(false, result);
    }
    @Test
    public void testCheckEmptyField() {
        boolean result = RegisterMenuController.checkEmptyField("Hello", null, "Hello", "Hello", "Hello",
                "Hello", "Hello", "Hello", "Hello");
        Assertions.assertEquals(false, result);
    }
    @Test
    public void testCheckEmptyField2() {
        boolean result = RegisterMenuController.checkEmptyField("Hello", "Hello", "Hello", null, null,
                "random", "Hello", null, "Hello");
        Assertions.assertEquals(true, result);
    }
    @Test
    public void testCheckUsername() {
        boolean result = RegisterMenuController.checkUsername("alir  eza");
        Assertions.assertEquals(false, result);
    }
    @Test
    public void testCheckPassword() {
        boolean result = RegisterMenuController.checkPassword("Password123@", "Password123@", null);
        Assertions.assertEquals(true, result);
    }
    @Test
    public void testCheckPassword2() {
        boolean result = RegisterMenuController.checkPassword("Password123", "Password123", null);
        Assertions.assertEquals(false, result);
    }
    @Test
    public void testCheckPassword3() {
        boolean result = RegisterMenuController.checkPassword("Password123!", "Password123@", null);
        Assertions.assertEquals(false, result);
    }
    @Test
    public void testCheckEmail() {
        boolean result = RegisterMenuController.checkEmail("mohaghegh.ar82@gmail.com");
        Assertions.assertEquals(false, result);
    }
    @Test
    public void testCheckEmail2() {
        boolean result = RegisterMenuController.checkEmail("mohaghegh");
        Assertions.assertEquals(false, result);
    }
    @Test
    public void testCheckSlogan() {
        boolean result = RegisterMenuController.checkSlogan("Hello", null, "Hello");
        Assertions.assertEquals(true, result);
    }
    @Test
    public void testCheckSlogan2() {
        boolean result = RegisterMenuController.checkSlogan("Hello", null, "");
        Assertions.assertEquals(true, result);
    }
    @Test
    public void testCheckUsernameFormat() {
        boolean result = RegisterMenuController.checkUsernameFormat("@alireza");
        Assertions.assertEquals(false, result);
    }
    @Test
    public void testCheckEmailFormat() {
        boolean result = RegisterMenuController.checkEmailFormat("radin@gmail.ir");
        Assertions.assertEquals(true, result);
    }
    @Test
    public void testCheckPasswordStrength() {
        String result = RegisterMenuController.checkPasswordStrength("Helo@");
        Assertions.assertEquals("weak password, errors: [short length][no numbers]!", result);
    }
    @Test
    public void testCheckPasswordStrength2() {
        String result = RegisterMenuController.checkPasswordStrength("Pass32@");
        Assertions.assertEquals("success", result);
    }
    @Test
    public void testCheckStrength() {
        String result = RegisterMenuController.checkStrength("hello@");
        Assertions.assertEquals("[no uppercase letters][no numbers]", result);
    }
}
