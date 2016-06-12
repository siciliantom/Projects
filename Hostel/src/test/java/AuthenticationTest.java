import by.bsu.hostel.dao.AuthenticationDAO;
import by.bsu.hostel.domain.Authentication;
import by.bsu.hostel.exception.DAOException;
import by.bsu.hostel.exception.ServiceException;
import by.bsu.hostel.pool.ConnectionPool;
import by.bsu.hostel.pool.ProxyConnection;
import org.junit.BeforeClass;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

/**
 * Created by Kate on 13.04.2016.
 */
public class AuthenticationTest {
    private static ProxyConnection conn;

    @BeforeClass
    public static void initPool() {
        ConnectionPool.getInstance();
        conn = ConnectionPool.getConnection();
    }

    @Test
    public void testGetClients() throws ServiceException {
        AuthenticationDAO authenticationDAO = new AuthenticationDAO(conn);
        Authentication authentication = null;
        boolean exist;
        try {
            exist = authenticationDAO.checkLoginExistence(authentication);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        assertTrue("Null authentication is not valid!", !exist);
    }
}

