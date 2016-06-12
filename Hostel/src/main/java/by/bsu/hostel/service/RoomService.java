package by.bsu.hostel.service;

import by.bsu.hostel.dao.RoomDAO;
import by.bsu.hostel.domain.Room;
import by.bsu.hostel.exception.DAOException;
import by.bsu.hostel.exception.ServiceException;
import by.bsu.hostel.pool.ConnectionPool;
import by.bsu.hostel.pool.ProxyConnection;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kate on 07.04.2016.
 */
public class RoomService {
    private static RoomService roomService = null;
    static Logger log = Logger.getLogger(RoomService.class);

    private RoomService() {
    }

    public static RoomService getInstance() {
        if (roomService == null) {
            roomService = new RoomService();
        }
        return roomService;
    }

    public List<Room> getAppropriateRooms(String applicationId) throws ServiceException {
        ProxyConnection conn = ConnectionPool.getConnection();
        List<Room> rooms = new ArrayList<>();
        if (conn != null) {
            RoomDAO roomDAO = new RoomDAO(conn);
            try {
                rooms = roomDAO.findAppropriate(applicationId);
            } catch (DAOException e) {
                throw new ServiceException(e);
            } finally {
                ConnectionPool.returnConnection(conn);
            }
        } else {
            log.error("Can't get connection!");
        }
        return rooms;
    }
}

