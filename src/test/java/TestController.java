import Client.Controller.Controller;
import Client.Model.User;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;


public class TestController {
    @Test
    public void testFindUserByUsername() {
        User user = Controller.findUserByUsername("blah blah blah");
        Assertions.assertEquals(user, null);
    }
    @Test
    public void testFindUserByUsername2() {
        assert Controller.currentUser != null;
        User user = Controller.findUserByUsername(Controller.currentUser.getUsername());
        Assertions.assertEquals(user.getUsername(), Controller.currentUser.getUsername());
    }
    @Test
    public void testFindUserByEmail() {
        User user = Controller.findUserByEmail("blah blah @blah.com");
        Assertions.assertEquals(user, null);
    }
    @Test
    public void testFindUserByEmail2() {
        assert Controller.currentUser != null;
        User user = Controller.findUserByEmail(Controller.currentUser.getEmail());
        Assertions.assertEquals(user.getEmail(), Controller.currentUser.getEmail());
    }
    @Test
    public void testRemoveDoubleQuote() {
        String result = Controller.removeDoubleQuote("\"Hello");
        Assertions.assertEquals("\"Hello", result);
    }
    @Test
    public void testRemoveDoubleQuote2() {
        String result = Controller.removeDoubleQuote("\"Hello\"");
        Assertions.assertEquals("Hello", result);
    }
    @Test
    public void testRemoveDoubleQuote3() {
        String result = Controller.removeDoubleQuote("Hello");
        Assertions.assertEquals("Hello", result);
    }
}
