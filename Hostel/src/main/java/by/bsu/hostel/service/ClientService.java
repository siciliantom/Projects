package by.bsu.hostel.service;

import by.bsu.hostel.dao.ClientDAO;
import by.bsu.hostel.domain.Authentication;
import by.bsu.hostel.domain.Client;
import by.bsu.hostel.exception.DAOException;
import by.bsu.hostel.exception.ServiceException;
import by.bsu.hostel.pool.ConnectionPool;
import by.bsu.hostel.pool.ProxyConnection;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created by Kate on 15.02.2016.
 */
public class ClientService {
    private static ClientService clientService = null;
    static Logger log = Logger.getLogger(ClientService.class);

    private ClientService() {
    }

    public static ClientService getInstance() {
        if (clientService == null) {
            clientService = new ClientService();
        }
        return clientService;
    }

    public List<Client> findAll() throws ServiceException {
        ProxyConnection conn = ConnectionPool.getConnection();
        List<Client> clients = null;
        if (conn != null) {
            ClientDAO clientDAO = new ClientDAO(conn);
            try {
                clients = clientDAO.findAll();
            } catch (DAOException e) {
                throw new ServiceException(e);
            } finally {
                ConnectionPool.returnConnection(conn);
            }
        } else {
            log.error("Can't get connection!");
        }
        return clients;
    }

    public boolean setBan(String clientId) throws ServiceException {
        ProxyConnection conn = ConnectionPool.getConnection();
        boolean banned = false;
        if (conn != null) {
            ClientDAO clientDAO = new ClientDAO(conn);
            try {
                banned = clientDAO.setBan(clientId);
            } catch (DAOException e) {
                throw new ServiceException(e);
            } finally {
                ConnectionPool.returnConnection(conn);
            }
        } else {
            log.error("Can't get connection!");
        }
        return banned;
    }

    public boolean setUnban(String clientId) throws ServiceException {
        ProxyConnection conn = ConnectionPool.getConnection();
        boolean unbanned = false;
        if (conn != null) {
            ClientDAO clientDAO = new ClientDAO(conn);
            try {
                unbanned = clientDAO.setUnban(clientId);
            } catch (DAOException e) {
                throw new ServiceException(e);
            } finally {
                ConnectionPool.returnConnection(conn);
            }
        } else {
            log.error("Can't get connection!");
        }
        return unbanned;
    }

    public Client logIn(Authentication authentication) throws ServiceException {
        ProxyConnection conn = ConnectionPool.getConnection();
        Client client = null;
        if (conn != null) {
            ClientDAO clientDAO = new ClientDAO(conn);
            try {
                client = clientDAO.findByLogPass(authentication);
            } catch (DAOException e) {
                throw new ServiceException(e);
            } finally {
                ConnectionPool.returnConnection(conn);
            }
        } else {
            log.error("Can't get connection!");
        }
        return client;
    }

    public boolean checkBan(String rowId) throws ServiceException {
        ProxyConnection conn = ConnectionPool.getConnection();
        boolean banned = false;
        if (conn != null) {
            ClientDAO clientDAO = new ClientDAO(conn);
            try {
                banned = clientDAO.isBanned(rowId);
            } catch (DAOException e) {
                throw new ServiceException(e);
            } finally {
                ConnectionPool.returnConnection(conn);
            }
        } else {
            log.error("Can't get connection!");
        }
        return banned;
    }

    public boolean register(Client client) throws ServiceException {
        ProxyConnection conn = ConnectionPool.getConnection();
        boolean isRegistered = false;
        if (conn != null) {
            ClientDAO clientDAO = new ClientDAO(conn);
            try {
                isRegistered = clientDAO.add(client);
                return isRegistered;
            } catch (DAOException e) {
                throw new ServiceException(e);
            } finally {
                ConnectionPool.returnConnection(conn);
            }
        } else {
            log.error("Can't get connection!");
            return isRegistered;
        }
    }
}

