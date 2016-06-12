package by.bsu.hostel.dao;

import by.bsu.hostel.domain.Application;
import by.bsu.hostel.domain.ConfirmationEnum;
import by.bsu.hostel.exception.DAOException;
import by.bsu.hostel.pool.ProxyConnection;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;


/**
 * Created by Kate on 05.02.2016.
 *
 * Class for working with applications
 *
 * @author Kate
 * @version 1.0
 */
public class ApplicationDAO extends DAO<Application> {
    public static final String SQL_LAST_INSERTED_ID = "SELECT LAST_INSERT_ID()";
    public static final String SQL_SELECT_ROOM_TYPE = "SELECT type FROM room_type WHERE room_type.id = ?";
    public static final String SQL_SET_CONFIRMATION = "UPDATE application SET application.confirmed = ? WHERE application.id = ?";
    public static final String SQL_SET_ROOM_ID = "UPDATE application SET room_id = ? WHERE application.id = ?";
    public static final String SQL_FIND_ROOM_ORDER = "SELECT room_type.type FROM room_type " +
            "WHERE room_type.id = (SELECT room_type_id FROM room_order WHERE application_id = ?)";
    public static final String SQL_INSERT_ROOM_ORDER = "INSERT INTO room_order (application_id, room_type_id) VALUES" +
            "(?, (SELECT room_type.id FROM room_type WHERE type = ?))";
    public static final String SQL_ADD_APPLICATION = "INSERT INTO application " +
            "(places_amount, arrival_date, departure_date, client_id, confirmed) VALUES (?, ?, ?, ?, ?)";
    public static final String SQL_DELETE_APPLICATIONS_BY_ID = "DELETE FROM application WHERE application.id = ?";
    public static final String SQL_SELECT_CURRENT_APPLICATIONS = "SELECT application.id, places_amount, " +
            " arrival_date, departure_date, client_id, confirmed, room_id FROM application WHERE arrival_date >= ?";
    public static final String SQL_SELECT_HISTORY_APPLICATIONS = "SELECT application.id, places_amount, " +
            " arrival_date, departure_date, client_id, confirmed, room_id FROM application " +
            "WHERE application.arrival_date < ?";
    public static final String SQL_SELECT_CURRENT_APPLICATIONS_BY_ID = "SELECT application.id, places_amount, " +
            "arrival_date, departure_date, confirmed FROM application " +
            "WHERE application.client_id = ? AND application.arrival_date >= ?";
    public static final String SQL_SELECT_HISTORY_APPLICATIONS_BY_ID = "SELECT application.id, places_amount, " +
            "arrival_date, departure_date, confirmed, room_order.room_type_id FROM application " +
            "JOIN room_order ON room_order.application_id = application.id  AND application.client_id = ? " +
            "AND application.arrival_date < ?";
    public static final int ROOM_COST = 25;
    public static final int CONFIRMATION = 1;
    static Logger log = Logger.getLogger(ApplicationDAO.class);

    public ApplicationDAO(ProxyConnection proxyConnection) {
        this.proxyConnection = proxyConnection;
    }

    /**
     *Add application to db
     *
     * @param application
     * @return boolean
     * @throws DAOException
     */
    @Override
    public boolean add(Application application) throws DAOException {
        boolean applicationAdded = false;
        PreparedStatement insertApplication = null;
        PreparedStatement insertRoomOrder = null;
        Statement statement = null;
        try {
            statement = proxyConnection.createStatement();
            proxyConnection.setAutoCommit(false);
            insertApplication = proxyConnection.prepareStatement(SQL_ADD_APPLICATION);
            insertApplication.setInt(1, application.getPlacesAmount());
            insertApplication.setDate(2, new java.sql.Date(application.getArrivalDate().getTime()));
            insertApplication.setDate(3, new java.sql.Date(application.getDepartureDate().getTime()));
            insertApplication.setLong(4, application.getClientId());
            insertApplication.setInt(5, application.getConfirmed().ordinal());
            insertApplication.executeUpdate();

            ResultSet resultSet = statement.executeQuery(SQL_LAST_INSERTED_ID);
            resultSet.next();
            application.setId(resultSet.getLong(1));

            insertRoomOrder = proxyConnection.prepareStatement(SQL_INSERT_ROOM_ORDER);
            insertRoomOrder.setLong(1, application.getId());
            insertRoomOrder.setString(2, application.getRoom().getType());
            insertRoomOrder.executeUpdate();

            proxyConnection.commit();
            proxyConnection.setAutoCommit(true);
            applicationAdded = true;
        } catch (SQLException e) {
            try {
                proxyConnection.rollback();
            } catch (SQLException ex) {
                log.error("Problems with rollback " + ex);
            }
            throw new DAOException(e);
        } finally {
            close(insertApplication);
            close(insertRoomOrder);
            close(statement);
        }
        return applicationAdded;
    }

    /**
     * Retrieves all applications for valid date
     *
     * @return List<Application>
     * @throws DAOException
     */
    public List<Application> findCurrentApplications() throws DAOException {
        List<Application> applicationList = new ArrayList<Application>();
        PreparedStatement getCurrentApplications = null;
        Date date = new Date();

        try {
            getCurrentApplications = proxyConnection.prepareStatement(SQL_SELECT_CURRENT_APPLICATIONS);
            getCurrentApplications.setDate(1, new java.sql.Date(date.getTime()));
            ResultSet resultSet;
            resultSet = getCurrentApplications.executeQuery();
            while (resultSet.next()) {
                Application application = new Application();
                application.setId(resultSet.getLong(1));
                application.setPlacesAmount(resultSet.getInt(2));
                application.setArrivalDate(resultSet.getDate(3));
                application.setDepartureDate(resultSet.getDate(4));
                application.setClientId(resultSet.getLong(5));
                application.setConfirmed(ConfirmationEnum.valueOf(resultSet.getInt(6)));
                application.getRoom().setId(resultSet.getLong(7));
                application.setFinalPrice(application.getPlacesAmount() * ROOM_COST);
                applicationList.add(application);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(getCurrentApplications);
        }
        return applicationList;
    }



    /**
     * Retrieves all applications for past date
     *
     * @return List<Application>
     * @throws DAOException
     */
    public List<Application> findHistoryApplications() throws DAOException {
        List<Application> applicationList = new ArrayList<Application>();
        PreparedStatement getHistoryApplications = null;
        Date date = new Date();
        try {
            getHistoryApplications = proxyConnection.prepareStatement(SQL_SELECT_HISTORY_APPLICATIONS);
            getHistoryApplications.setDate(1, new java.sql.Date(date.getTime()));
            ResultSet resultSet;
            resultSet = getHistoryApplications.executeQuery();
            while (resultSet.next()) {
                Application application = new Application();
                application.setId(resultSet.getLong(1));
                application.setPlacesAmount(resultSet.getInt(2));
                application.setArrivalDate(resultSet.getDate(3));
                application.setDepartureDate(resultSet.getDate(4));
                application.setClientId(resultSet.getLong(5));
                application.setConfirmed(ConfirmationEnum.valueOf(resultSet.getInt(6)));
                application.getRoom().setId(resultSet.getLong(7));
                application.setFinalPrice(application.getPlacesAmount() * ROOM_COST);
                applicationList.add(application);

            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(getHistoryApplications);
        }
        return applicationList;
    }

    /**
     * Retrieves all applications for valid date for definite client
     *
     * @return List<Application>
     * @throws DAOException
     */
    public List<Application> findCurrentApplicationsById(Long clientId) throws DAOException {
        List<Application> applicationList = new ArrayList<Application>();
        PreparedStatement findCurrentApplications = null;
        PreparedStatement findRoomOrder = null;
        Date date = new Date();
        ResultSet resultSet;
        ResultSet roomResultSet;
        try {
            findCurrentApplications = proxyConnection.prepareStatement(SQL_SELECT_CURRENT_APPLICATIONS_BY_ID);
            findCurrentApplications.setLong(1, clientId);
            findCurrentApplications.setDate(2, new java.sql.Date(date.getTime()));
            resultSet = findCurrentApplications.executeQuery();

            while (resultSet.next()) {
                Application application = new Application();
                application.setId(resultSet.getLong(1));
                application.setPlacesAmount(resultSet.getInt(2));
                application.setArrivalDate(resultSet.getDate(3));
                application.setDepartureDate(resultSet.getDate(4));
                application.setClientId(clientId);
                application.setConfirmed(ConfirmationEnum.valueOf(resultSet.getInt(5)));

                findRoomOrder = proxyConnection.prepareStatement(SQL_FIND_ROOM_ORDER);
                findRoomOrder.setLong(1, application.getId());
                roomResultSet = findRoomOrder.executeQuery();
                roomResultSet.next();
                application.getRoom().setType(roomResultSet.getString(1));
                application.setFinalPrice(application.getPlacesAmount() * ROOM_COST);
                applicationList.add(application);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(findCurrentApplications);
            close(findRoomOrder);
        }
        return applicationList;
    }

    /**
     * Retrieves all applications for past date for definite client
     *
     * @return List<Application>
     * @throws DAOException
     */
    public List<Application> findHistoryApplicationsById(Long clientId) throws DAOException {
        List<Application> applicationList = new ArrayList<Application>();
        PreparedStatement findHistoryApplications = null;
        PreparedStatement getType = null;
        Date date = new Date();
        ResultSet resultSet;
        ResultSet typeResultSet;
        try {
            findHistoryApplications = proxyConnection.prepareStatement(SQL_SELECT_HISTORY_APPLICATIONS_BY_ID);
            getType = proxyConnection.prepareStatement(SQL_SELECT_ROOM_TYPE);
            findHistoryApplications.setLong(1, clientId);
            findHistoryApplications.setDate(2, new java.sql.Date(date.getTime()));
            resultSet = findHistoryApplications.executeQuery();

            while (resultSet.next()) {
                Application application = new Application();
                application.setId(resultSet.getLong(1));
                application.setPlacesAmount(resultSet.getInt(2));
                application.setArrivalDate(resultSet.getDate(3));
                application.setDepartureDate(resultSet.getDate(4));
                application.setClientId(clientId);
                application.setConfirmed(ConfirmationEnum.valueOf(resultSet.getInt(5)));
                getType.setLong(1, resultSet.getLong(6));
                typeResultSet = getType.executeQuery();
                if (typeResultSet.next()) {
                    application.getRoom().setType(typeResultSet.getString(1));
                }
                application.setFinalPrice(application.getPlacesAmount() * ROOM_COST);
                applicationList.add(application);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(findHistoryApplications);
            close(getType);
        }
        return applicationList;
    }

    /**
     * Delete applications by definite ids
     *
     * @return List<Application>
     * @throws DAOException
     */
    public boolean deleteById(List<String> idsList) throws DAOException {
        boolean applicationsDeleted = false;
        PreparedStatement deleteApplications = null;
        try {
            proxyConnection.setAutoCommit(false);
            deleteApplications = proxyConnection.prepareStatement(SQL_DELETE_APPLICATIONS_BY_ID);
            for (String id : idsList) {
                deleteApplications.setLong(1, Long.parseLong(id));
                deleteApplications.addBatch();
            }
            deleteApplications.executeBatch();
            proxyConnection.commit();
            proxyConnection.setAutoCommit(true);
            applicationsDeleted = true;
        } catch (SQLException e) {
            try {
                proxyConnection.rollback();
            } catch (SQLException ex) {
                log.error("Problems with rollback " + ex);
            }
            throw new DAOException(e);
        } finally {
            close(deleteApplications);
        }
        return applicationsDeleted;
    }

    /**
     * Confirms the order for client
     *
     * @return List<Application>
     * @throws DAOException
     */
    public boolean setConfirmation(Application application) throws DAOException {
        boolean applicationsUpdated = false;
        PreparedStatement setConfirmation = null;
        PreparedStatement setRoomId = null;
        try {
            proxyConnection.setAutoCommit(false);
            setConfirmation = proxyConnection.prepareStatement(SQL_SET_CONFIRMATION);
            setConfirmation.setInt(1, CONFIRMATION);
            setConfirmation.setLong(2, application.getId());
            setConfirmation.executeUpdate();

            setRoomId = proxyConnection.prepareStatement(SQL_SET_ROOM_ID);
            setRoomId.setLong(1, application.getRoom().getId());
            setRoomId.setLong(2, application.getId());
            setRoomId.executeUpdate();

            proxyConnection.commit();
            proxyConnection.setAutoCommit(true);
            applicationsUpdated = true;
        } catch (SQLException e) {
            try {
                proxyConnection.rollback();
            } catch (SQLException ex) {
                log.error("Problems with rollback " + ex);
            }
            throw new DAOException(e);
        } finally {
            close(setConfirmation);
            close(setRoomId);
        }
        return applicationsUpdated;
    }

    @Override
    public List<Application> findAll() throws DAOException {
        return null;
    }
}
