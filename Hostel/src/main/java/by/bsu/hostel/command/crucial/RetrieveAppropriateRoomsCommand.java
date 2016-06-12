package by.bsu.hostel.command.crucial;

import by.bsu.hostel.command.factory.ActionCommand;
import by.bsu.hostel.domain.Room;
import by.bsu.hostel.exception.CommandException;
import by.bsu.hostel.exception.ServiceException;
import by.bsu.hostel.manager.ConfigurationManager;
import by.bsu.hostel.service.RoomService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Kate on 05.02.2016.
 *
 * Class for getting vacant rooms for current application
 *
 * @implements ActionCommand
 * @author Kate
 * @version 1.0
 */
public class RetrieveAppropriateRoomsCommand implements ActionCommand {
    private static final String APPLICATION_ID_PARAM = "application_id";
    private static final String APPLICATION_ID_ATTR ="applicationId";
    private static final String BEFORE_PAGE_ATTRIBUTE = "before_page";
    private static final String APPROPRIATE_ROOM_LIST_ATTRIBUTE = "appropriateRoomList";
    static Logger log = Logger.getLogger(RetrieveAppropriateRoomsCommand.class);

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        RoomService roomService;
        List<Room> appropriateRoomList;
        String applicationId = request.getParameter(APPLICATION_ID_PARAM);
        if (applicationId != null) {
            session.setAttribute(APPLICATION_ID_ATTR, applicationId);
            roomService = RoomService.getInstance();
            try {
                appropriateRoomList = roomService.getAppropriateRooms(applicationId);
            } catch (ServiceException e) {
                throw new CommandException(e);
            }
            if (appropriateRoomList != null) {
                session.setAttribute(APPROPRIATE_ROOM_LIST_ATTRIBUTE, appropriateRoomList);
            }
            session.setAttribute(BEFORE_PAGE_ATTRIBUTE, "path.page.main_admin");
            return ConfigurationManager.getProperty("path.page.appropriate_rooms");
        }
        return ConfigurationManager.getProperty("path.page.main_admin");
    }
}