import by.bsu.hostel.domain.Client;
import by.bsu.hostel.exception.ServiceException;
import by.bsu.hostel.logic.RegisterLogic;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;


/**
 * Created by Kate on 10.04.2016.
 */
public class RegisterLogicTest {
    @Test
    public void testRegistration() throws ServiceException {
        RegisterLogic registerLogic = new RegisterLogic();
        Client client = new Client();
        client.setName("George1234");
        client.setSurname("Soverin");
        client.setCountry("Germany");
        client.getAuthentication().setLogin("res");
        client.getAuthentication().setPassword("123");
        assertTrue("Wrong message", "message.wrong_name".equals(registerLogic.checkRegistration(client)));
    }
}
