package by.bsu.hostel.service;

import by.bsu.hostel.dao.AuthenticationDAO;
import by.bsu.hostel.domain.Authentication;
import by.bsu.hostel.exception.DAOException;
import by.bsu.hostel.exception.ServiceException;
import by.bsu.hostel.pool.ConnectionPool;
import by.bsu.hostel.pool.ProxyConnection;
import org.apache.log4j.Logger;

/**
 * Created by Kate on 28.03.2016.
 */
public class AuthenticationService {
    private static AuthenticationService authenticationService = null;
    static Logger log = Logger.getLogger(AuthenticationService.class);

    private AuthenticationService() {
    }

    public static AuthenticationService getInstance() {
        if (authenticationService == null) {
            authenticationService = new AuthenticationService();
        }
        return authenticationService;
    }

    public String checkExistence(Authentication authentication) throws ServiceException {
        ProxyConnection conn = ConnectionPool.getConnection();
        String message = null;
        if (conn != null) {
            AuthenticationDAO authenticationDAO = new AuthenticationDAO(conn);
            try {
                if (authenticationDAO.checkLoginExistence(authentication)) {
                    message = "message.login_exists";
                }
                if (authenticationDAO.checkAuthenticationExistence(authentication)) {
                    message = "message.client_exists";
                }
            } catch (DAOException e) {
                throw new ServiceException(e);
            } finally {
                ConnectionPool.returnConnection(conn);
            }
        } else {
            log.error("Can't get connection!");
        }
        return message;
    }
}
