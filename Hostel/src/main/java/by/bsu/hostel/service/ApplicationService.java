package by.bsu.hostel.service;

import by.bsu.hostel.dao.ApplicationDAO;
import by.bsu.hostel.domain.Application;
import by.bsu.hostel.exception.DAOException;
import by.bsu.hostel.exception.ServiceException;
import by.bsu.hostel.pool.ConnectionPool;
import by.bsu.hostel.pool.ProxyConnection;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created by Kate on 09.03.2016.
 */
public class ApplicationService {
    private static ApplicationService applicationService = null;
    static Logger log = Logger.getLogger(ApplicationService.class);

    private ApplicationService() {
    }

    public static ApplicationService getInstance() {
        if (applicationService == null) {
            applicationService = new ApplicationService();
        }
        return applicationService;
    }

    public boolean deleteById(List<String> idsList) throws ServiceException {
        boolean isDeleted = false;
        ProxyConnection conn = ConnectionPool.getConnection();
        if (conn != null) {
            ApplicationDAO applicationDAO = new ApplicationDAO(conn);
            try {
                isDeleted = applicationDAO.deleteById(idsList);
            } catch (DAOException e) {
                throw new ServiceException(e);
            } finally {
                ConnectionPool.returnConnection(conn);
            }
        } else {
            log.error("Can't get connection!");
        }
        return isDeleted;
    }

    public boolean confirmApplication(Application application) throws ServiceException {
        boolean isConfirmed = false;
        ProxyConnection conn = ConnectionPool.getConnection();
        if (conn != null) {
            ApplicationDAO applicationDAO = new ApplicationDAO(conn);
            try {
                isConfirmed = applicationDAO.setConfirmation(application);
            } catch (DAOException e) {
                throw new ServiceException(e);
            } finally {
                ConnectionPool.returnConnection(conn);
            }
        } else {
            log.error("Can't get connection!");
        }
        return isConfirmed;
    }

    public List<Application> currentApplications() throws ServiceException {
        ProxyConnection conn = ConnectionPool.getConnection();
        List<Application> applicationList = null;
        if (conn != null) {
            ApplicationDAO applicationDAO = new ApplicationDAO(conn);
            try {
                applicationList = applicationDAO.findCurrentApplications();
            } catch (DAOException e) {
                throw new ServiceException(e);
            } finally {
                ConnectionPool.returnConnection(conn);
            }
        } else {
            log.error("Can't get connection!");
        }
        return applicationList;
    }

    public List<Application> applicationsHistory() throws ServiceException {
        ProxyConnection conn = ConnectionPool.getConnection();
        List<Application> applicationList = null;
        if (conn != null) {
            ApplicationDAO applicationDAO = new ApplicationDAO(conn);
            try {
                applicationList = applicationDAO.findHistoryApplications();
            } catch (DAOException e) {
                throw new ServiceException(e);
            } finally {
                ConnectionPool.returnConnection(conn);
            }
        } else {
            log.error("Can't get connection!");
        }
        return applicationList;
    }

    public List<Application> currentApplicationsById(long clientId) throws ServiceException {
        ProxyConnection conn = ConnectionPool.getConnection();
        List<Application> applicationList = null;
        if (conn != null) {
            ApplicationDAO applicationDAO = new ApplicationDAO(conn);
            try {
                applicationList = applicationDAO.findCurrentApplicationsById(clientId);
            } catch (DAOException e) {
                throw new ServiceException(e);
            } finally {
                ConnectionPool.returnConnection(conn);
            }
        } else {
            log.error("Can't get connection!");
        }
        return applicationList;
    }

    public List<Application> applicationsHistoryById(long clientId) throws ServiceException {
        ProxyConnection conn = ConnectionPool.getConnection();
        List<Application> applicationList = null;
        if (conn != null) {
            ApplicationDAO applicationDAO = new ApplicationDAO(conn);
            try {
                applicationList = applicationDAO.findHistoryApplicationsById(clientId);
            } catch (DAOException e) {
                throw new ServiceException(e);
            } finally {
                ConnectionPool.returnConnection(conn);
            }
        } else {
            log.error("Can't get connection!");
        }
        return applicationList;
    }

    public boolean makeOrder(Application application) throws ServiceException {
        boolean isAdded = false;
        ProxyConnection conn = ConnectionPool.getConnection();
        if (conn != null) {
            ApplicationDAO applicationDAO = new ApplicationDAO(conn);
            try {
                isAdded = applicationDAO.add(application);
            } catch (DAOException e) {
                throw new ServiceException(e);
            } finally {
                ConnectionPool.returnConnection(conn);
            }
        } else {
            log.error("Can't get connection!");
        }
        return isAdded;
    }
}
