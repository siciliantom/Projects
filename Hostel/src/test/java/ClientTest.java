import by.bsu.hostel.dao.ClientDAO;
import by.bsu.hostel.domain.Client;
import by.bsu.hostel.exception.DAOException;
import by.bsu.hostel.exception.ServiceException;
import by.bsu.hostel.pool.ConnectionPool;
import by.bsu.hostel.pool.ProxyConnection;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assume.assumeTrue;

/**
 * Created by Kate on 13.04.2016.
 */
public class ClientTest {
    private static ProxyConnection conn;

    @BeforeClass
    public static void initPool() {
        ConnectionPool.getInstance();
        conn = ConnectionPool.getConnection();
    }

    @Test
    public void testGetClients() throws ServiceException {
        ClientDAO clientDAO = new ClientDAO(conn);
        List<Client> clients;
        try {
            clients = clientDAO.findAll();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        assertNotNull(clients);
        assumeTrue(clients.size() > 0);
    }

    @Test(expected = ServiceException.class)
    public void testClientBan() throws ServiceException {
        ClientDAO clientDAO = new ClientDAO(conn);
        try {
            assertTrue("Exception doesn't catch this", !clientDAO.isBanned("Not number"));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
