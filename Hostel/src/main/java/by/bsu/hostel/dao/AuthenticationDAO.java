package by.bsu.hostel.dao;

import by.bsu.hostel.domain.Authentication;
import by.bsu.hostel.exception.DAOException;
import by.bsu.hostel.pool.ProxyConnection;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Kate on 05.02.2016.
 *
 * Class for working with authentications
 *
 * @author Kate
 * @version 1.0
 */
public class AuthenticationDAO extends DAO<Authentication> {
    public static final String SQL_SELECT_AUTHENTICATION_BY_LOGPASS = "SELECT id, role_id FROM authentication " +
            "WHERE authentication.login = ? AND authentication.password = ?";
    public static final String SQL_SELECT_AUTHENTICATION_BY_LOGIN = "SELECT id, role_id FROM authentication " +
            "WHERE authentication.login = ?";
    static Logger log = Logger.getLogger(AuthenticationDAO.class);

    public AuthenticationDAO(ProxyConnection proxyConnection) {
        this.proxyConnection = proxyConnection;
    }

    /**
     * Checks if client with such login
     * and password have already existed
     *
     * @param authentication
     * @return List<Application>
     * @throws DAOException
     */
    public Boolean checkAuthenticationExistence(Authentication authentication) throws DAOException {
        Boolean existsAlready = false;
        PreparedStatement statement = null;
        ResultSet resultSet;
        if(authentication == null){
            return existsAlready;
        }
        try {
            statement = proxyConnection.prepareStatement(SQL_SELECT_AUTHENTICATION_BY_LOGPASS);
            statement.setString(1, authentication.getLogin());
            statement.setString(2, authentication.getPassword());
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                existsAlready = true;
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(statement);
        }
        return existsAlready;
    }

    /**
     * Checks if there is a client with such login in db
     *
     * @param authentication
     * @return Boolean
     * @throws DAOException
     */
    public Boolean checkLoginExistence(Authentication authentication) throws DAOException {
        Boolean existsAlready = false;
        PreparedStatement statement = null;
        ResultSet resultSet;
        if(authentication == null){
            return existsAlready;
        }
        try {
            statement = proxyConnection.prepareStatement(SQL_SELECT_AUTHENTICATION_BY_LOGIN);
            statement.setString(1, authentication.getLogin());
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                existsAlready = true;
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(statement);
        }
        return existsAlready;
    }

    @Override
    public List<Authentication> findAll() throws DAOException {
        return null;
    }

    @Override
    public boolean add(Authentication entity) throws DAOException {
        return false;
    }
}
