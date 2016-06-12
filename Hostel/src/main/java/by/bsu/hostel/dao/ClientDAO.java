package by.bsu.hostel.dao;

import by.bsu.hostel.domain.Authentication;
import by.bsu.hostel.domain.Client;
import by.bsu.hostel.domain.ConfirmationEnum;
import by.bsu.hostel.domain.RoleEnum;
import by.bsu.hostel.exception.DAOException;
import by.bsu.hostel.pool.ProxyConnection;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kate on 05.02.2016.
 *
 * Class for working with clients
 *
 * @author Kate
 * @version 1.0
 */
public class ClientDAO extends DAO<Client> {
    public static final String SQL_SELECT_ALL_CLIENTS = "SELECT client.id, client.name, surname, country, status.banned " +
            "FROM client JOIN status ON status.client_id = client.id";
    public static final String SQL_CHECK_CLIENT_BAN = "SELECT banned FROM status " +
            "WHERE status.client_id = ?";
    public static final String SQL_INSERT_AUTHENTICATION = "INSERT INTO authentication (login, password, role_id) " +
            "VALUES (?, ?, (SELECT role.id FROM role WHERE role.type = ?))";
    public static final String SQL_INSERT_CLIENT = "INSERT INTO client (name, surname, country, authentication_id) " +
            "VALUES (?, ?, ?, LAST_INSERT_ID())";
    public static final String SQL_INSERT_STATUS = "INSERT INTO status (client_id, banned) " +
            "VALUES (?, ?)";
    public static final String SQL_SELECT_AUTHENTICATION_DATA = "SELECT authentication.id, role.type " +
            "FROM authentication JOIN role ON authentication.role_id = role.id WHERE authentication.login = ?" +
            "AND authentication.password = ?";
    public static final String SQL_USER_BY_LOGPASS = "SELECT client.id, client.name, client.surname, client.country, " +
            "status.banned FROM client JOIN status ON status.client_id = (SELECT client.id FROM client " +
            "WHERE client.authentication_id = ?) WHERE client.authentication_id = ?";
    public static final String SQL_ADMIN_BY_LOGPASS = "SELECT client.id, client.name, client.surname, client.country " +
            "FROM client WHERE client.authentication_id = ?";
    public static final String SQL_SET_BAN = "UPDATE status SET banned = ? WHERE status.client_id = ?";
    public static final String SQL_LAST_INSERTED_ID = "SELECT LAST_INSERT_ID()";
    public static final int IS_BANNED = 1;
    public static final int NOT_BANNED = 0;
    static Logger log = Logger.getLogger(ClientDAO.class);

    public ClientDAO(ProxyConnection proxyConnection) {
        this.proxyConnection = proxyConnection;
    }

    /**
     * Retrieves all clients
     *
     * @return List<Client>
     * @throws DAOException
     */
    @Override
    public List<Client> findAll() throws DAOException {
        List<Client> clients = new ArrayList<Client>();
        Statement statement = null;
        try {
            statement = proxyConnection.createStatement();
            ResultSet resultSet;
            resultSet = statement.executeQuery(SQL_SELECT_ALL_CLIENTS);
            while (resultSet.next()) {
                Client client = new Client();
                client.setId(resultSet.getLong(1));
                client.setName(resultSet.getString(2));
                client.setSurname(resultSet.getString(3));
                client.setCountry(resultSet.getString(4));
                client.getStatus().setBanned(ConfirmationEnum.valueOf(resultSet.getInt(5)));
                clients.add(client);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(statement);
        }
        return clients;
    }

    /**
     * Find definite client in db
     *
     * @param authentication
     * @return Client
     * @throws DAOException
     */
    public Client findByLogPass(Authentication authentication) throws DAOException {
        Client client = null;
        PreparedStatement statement = null;
        PreparedStatement authenticationDataStatement = null;
        ResultSet authenticationDataSet;
        ResultSet resultSet;
        try {
            authenticationDataStatement = proxyConnection.prepareStatement(SQL_SELECT_AUTHENTICATION_DATA);
            authenticationDataStatement.setString(1, authentication.getLogin());
            authenticationDataStatement.setString(2, authentication.getPassword());
            authenticationDataSet = authenticationDataStatement.executeQuery();
            if (authenticationDataSet.next()) {
                client = new Client();
                authentication.setId(authenticationDataSet.getLong(1));
                authentication.setRole(RoleEnum.valueOf(authenticationDataSet.getString(2).toUpperCase()));
                client.setAuthentication(authentication);
                if (authentication.getRole().equals(RoleEnum.USER)) {
                    statement = proxyConnection.prepareStatement(SQL_USER_BY_LOGPASS);
                    statement.setLong(1, authentication.getId());
                    statement.setLong(2, authentication.getId());
                }
                if (authentication.getRole().equals(RoleEnum.ADMIN)) {
                    statement = proxyConnection.prepareStatement(SQL_ADMIN_BY_LOGPASS);
                    statement.setLong(1, authentication.getId());
                }
                resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    client.setId(resultSet.getLong(1));
                    client.setName(resultSet.getString(2));
                    client.setSurname(resultSet.getString(3));
                    client.setCountry(resultSet.getString(4));
                    if (authentication.getRole().equals(RoleEnum.USER)) {
                        client.getStatus().setBanned(ConfirmationEnum.valueOf(resultSet.getInt(5)));
                    }
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(statement);
            close(authenticationDataStatement);
        }
        return client;
    }

    /**
     * Checks if client is banned
     *
     * @param rowId
     * @return boolean
     * @throws DAOException
     */
    public boolean isBanned(String rowId) throws DAOException {
        boolean isBanned = false;
        PreparedStatement statement = null;
        ResultSet resultSet;
        try {
            statement = proxyConnection.prepareStatement(SQL_CHECK_CLIENT_BAN);
            statement.setLong(1, Long.parseLong(rowId));
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                if (resultSet.getInt(1) == IS_BANNED) {
                    isBanned = true;
                }
            }
        } catch (SQLException | NumberFormatException e) {
            throw new DAOException(e);
        } finally {
            close(statement);
        }
        return isBanned;
    }

    /**
     * Make client banned
     *
     * @param clientId
     * @return boolean
     * @throws DAOException
     */
    public boolean setBan(String clientId) throws DAOException {
        boolean isBanned = false;
        PreparedStatement statement = null;
        try {
            statement = proxyConnection.prepareStatement(SQL_SET_BAN);
            statement.setInt(1, IS_BANNED);
            statement.setLong(2, Long.parseLong(clientId));
            statement.executeUpdate();
            isBanned = true;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(statement);
        }
        return isBanned;
    }

    /**
     * Make client unbanned
     *
     * @param clientId
     * @return boolean
     * @throws DAOException
     */
    public boolean setUnban(String clientId) throws DAOException {
        boolean isUnbanned = false;
        PreparedStatement statement = null;
        try {
            statement = proxyConnection.prepareStatement(SQL_SET_BAN);
            statement.setInt(1, NOT_BANNED);
            statement.setLong(2, Long.parseLong(clientId));
            statement.executeUpdate();
            isUnbanned = true;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(statement);
        }
        return isUnbanned;
    }

    /**
     * Add new client to db
     *
     * @param client
     * @return boolean
     * @throws DAOException
     */
    @Override
    public boolean add(Client client) throws DAOException {
        boolean clientAdded = false;
        PreparedStatement insertAuthentication = null;
        PreparedStatement insertClient = null;
        PreparedStatement insertStatus = null;
        Statement statement = null;
        try {
            proxyConnection.setAutoCommit(false);
            proxyConnection.setAutoCommit(false);
            statement = proxyConnection.createStatement();
            insertAuthentication = proxyConnection.prepareStatement(SQL_INSERT_AUTHENTICATION);
            insertAuthentication.setString(1, client.getAuthentication().getLogin());
            insertAuthentication.setString(2, client.getAuthentication().getPassword());
            insertAuthentication.setString(3, client.getAuthentication().getRole().name().toLowerCase());
            insertAuthentication.executeUpdate();

            insertClient = proxyConnection.prepareStatement(SQL_INSERT_CLIENT);
            insertClient.setString(1, client.getName());
            insertClient.setString(2, client.getSurname());
            insertClient.setString(3, client.getCountry());
            insertClient.executeUpdate();
            ResultSet resultSet = statement.executeQuery(SQL_LAST_INSERTED_ID);
            resultSet.next();
            client.setId(resultSet.getLong(1));

            if (client.getAuthentication().getRole().equals(RoleEnum.USER)) {
                insertStatus = proxyConnection.prepareStatement(SQL_INSERT_STATUS);
                insertStatus.setLong(1, client.getId());
                insertStatus.setInt(2, NOT_BANNED);
                client.getStatus().setBanned(ConfirmationEnum.NO);
                insertStatus.executeUpdate();
            }

            proxyConnection.commit();
            proxyConnection.setAutoCommit(true);
            clientAdded = true;
        } catch (SQLException e) {
            try {
                proxyConnection.rollback();
            } catch (SQLException ex) {
                log.error("Problems with rollback " + ex);
            }
            throw new DAOException(e);
        } finally {
            close(statement);
            close(insertStatus);
            close(insertAuthentication);
            close(insertClient);
        }
        return clientAdded;
    }
}
