package by.bsu.hostel.dao;

import by.bsu.hostel.domain.Room;
import by.bsu.hostel.exception.DAOException;
import by.bsu.hostel.pool.ProxyConnection;
import org.apache.log4j.Logger;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Kate on 05.02.2016.
 *
 * Class for working with rooms
 *
 * @author Kate
 * @version 1.0
 */
public class RoomDAO extends DAO<Room> {
    public static final String SQL_SELECT_ROOMS_FOR_APPLICATION = "SELECT room.id, max_places, price, room_type " +
            "FROM room WHERE room.room_type = (SELECT room_order.room_type_id FROM room_order " +
            "WHERE room_order.application_id = ?)";
    public static final String SQL_SELECT_ROOM_TYPE = "SELECT room_type.type FROM room_type WHERE room_type.id = ?";
    public static final String SQL_SELECT_APPLICATION_DATA = "SELECT places_amount, arrival_date, departure_date" +
            " FROM application WHERE application.id = ?";
    public static final String SQL_SELECT_APPLICATIONS_FOR_ROOM = "SELECT places_amount, TO_DAYS(arrival_date), " +
            "TO_DAYS(departure_date) FROM application WHERE confirmed = ? AND room_id = ? AND arrival_date <= ? " +
            "AND departure_date >= ?";
    static Logger log = Logger.getLogger(RoomDAO.class);
    public static final int CONFIRMED = 1;

    public RoomDAO(ProxyConnection proxyConnection) {
        this.proxyConnection = proxyConnection;
    }

    /**
     * Finding free rooms for the selected dates
     *
     * @param applicationId
     * @return List<Room>
     * @throws DAOException
     */
    public List<Room> findAppropriate(String applicationId) throws DAOException {
        List<Room> rooms = new ArrayList<Room>();
        Map<Integer, Integer> dayCompletionList;
        boolean isAvailable = true;
        int minPlacesAvailable;
        int neededPlacesAmount = 0;
        Date mainArrival = null;
        Date mainDeparture = null;
        PreparedStatement statement = null;
        PreparedStatement getApplicationData = null;
        PreparedStatement getRoomType = null;
        PreparedStatement getApplicationsForRoom = null;
        ResultSet applicationDataResultSet;
        ResultSet resultSet;
        ResultSet roomTypeResultSet;
        ResultSet applicationsForRoom;
        try {
            getApplicationData = proxyConnection.prepareStatement(SQL_SELECT_APPLICATION_DATA);
            getApplicationData.setLong(1, Long.parseLong(applicationId));

            applicationDataResultSet = getApplicationData.executeQuery();
            if (applicationDataResultSet.next()) {
                neededPlacesAmount = applicationDataResultSet.getInt(1);
                mainArrival = applicationDataResultSet.getDate(2);
                mainDeparture = applicationDataResultSet.getDate(3);
            }
            getRoomType = proxyConnection.prepareStatement(SQL_SELECT_ROOM_TYPE);
            getApplicationsForRoom = proxyConnection.prepareStatement(SQL_SELECT_APPLICATIONS_FOR_ROOM);
            getApplicationsForRoom.setInt(1, CONFIRMED);

            statement = proxyConnection.prepareStatement(SQL_SELECT_ROOMS_FOR_APPLICATION);
            statement.setLong(1, Long.parseLong(applicationId));

            resultSet = statement.executeQuery();
            while (resultSet.next()) {//all rooms of type
                dayCompletionList = new HashMap<>();
                Room room = new Room();
                room.setId(resultSet.getLong(1));
                room.setMaxPlaces(resultSet.getInt(2));
                room.setPrice(resultSet.getInt(3));
                getRoomType.setLong(1, Long.parseLong(resultSet.getString(4)));

                roomTypeResultSet = getRoomType.executeQuery();
                if (roomTypeResultSet.next()) {
                    room.setType(roomTypeResultSet.getString(1));
                }
                getApplicationsForRoom.setLong(2, room.getId());
                getApplicationsForRoom.setDate(3, mainDeparture);
                getApplicationsForRoom.setDate(4, mainArrival);

                applicationsForRoom = getApplicationsForRoom.executeQuery();
                //number of day from 0 year
                int firstDay;
                int lastDay;
                int placesAmount = 0;
                minPlacesAvailable = room.getMaxPlaces();
                //through all confirmed applications for current room (only in case smn has already booked it)
                while (applicationsForRoom.next()) {
                    placesAmount = applicationsForRoom.getInt(1);
                    firstDay = applicationsForRoom.getInt(2);
                    lastDay = applicationsForRoom.getInt(3);
                    Integer i = firstDay;
                    while (i <= lastDay) {
                        if (dayCompletionList.containsKey(i)) {
                            dayCompletionList.put(i, dayCompletionList.get(i) + placesAmount);
                        } else {
                            dayCompletionList.put(i, placesAmount);
                        }
                        i++;
                    }
                }
                //заполненность дней по подтвержденным заявкам (сколько уже занято)
                for (int amount : dayCompletionList.values()) {
                    if (room.getMaxPlaces() - amount < minPlacesAvailable) {
                        //сколько осталось мест на такой период
                        minPlacesAvailable = room.getMaxPlaces() - amount;
                    }
                    if (amount + neededPlacesAmount > room.getMaxPlaces()) {
                        isAvailable = false;
                    }
                }
                if (isAvailable) {
                    room.setFreePlaces(minPlacesAvailable);
                    rooms.add(room);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(statement);
            close(getApplicationData);
            close(getApplicationsForRoom);
            close(getRoomType);
        }
        return rooms;
    }

    @Override
    public List<Room> findAll() throws DAOException {
        return null;
    }

    @Override
    public boolean add(Room entity) throws DAOException {
        return false;
    }
}