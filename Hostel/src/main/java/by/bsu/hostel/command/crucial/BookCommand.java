package by.bsu.hostel.command.crucial;

import by.bsu.hostel.command.factory.ActionCommand;
import by.bsu.hostel.domain.Application;
import by.bsu.hostel.domain.Client;
import by.bsu.hostel.domain.ConfirmationEnum;
import by.bsu.hostel.exception.CommandException;
import by.bsu.hostel.exception.ServiceException;
import by.bsu.hostel.logic.OrderLogic;
import by.bsu.hostel.manager.ConfigurationManager;
import by.bsu.hostel.manager.MessageManager;
import by.bsu.hostel.service.ApplicationService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kate on 05.02.2016.
 *
 * Class for booking the room
 *
 * @implements ActionCommand
 * @author Kate
 * @version 1.0
 */
public class BookCommand implements ActionCommand {
    private static final String PARAM_NAME_PLACES = "places";
    private static final String PARAM_NAME_ARRIVAL = "arrival";
    private static final String PARAM_NAME_DEPARTURE = "departure";
    private static final String ROLE_ADMIN = "admin";
    private static final String ROLE_USER = "user";
    private static final String ROLE_ATTRIBUTE = "role";
    private static final String LAST_PAGE_ATTRIBUTE = "last_page";
    private static final String CLIENT_ATTRIBUTE = "client";
    private static final String LOCALE_ATTRIBUTE = "locale";
    private static final String USER_APPLICATION_LIST_ATTR = "applicationListUser";
    private static final String ERROR_APPLICATION_ATTRIBUTE = "errorApplication";
    private static final String EN = "en_US";
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final int CURRENT_PRICE = 25;
    private static final String PARAM_NAME_ROOM = "room_type";
    static Logger log = Logger.getLogger(BookCommand.class);

    /**
     * @Method for booking the room
     *
     * @param request
     * @return
     * @throws CommandException
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        MessageManager messageManager;
        HttpSession session = request.getSession();
        Application application = new Application();
        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        List<Application> currentApplicationList;
        String page = null;
        String message;
        Date arrivalDate;
        Date departureDate;
        Client currentClient = (Client) session.getAttribute(CLIENT_ATTRIBUTE);
        ApplicationService applicationService = ApplicationService.getInstance();

        if ((EN).equals(session.getAttribute(LOCALE_ATTRIBUTE))) {
            messageManager = MessageManager.EN;
        } else {
            messageManager = MessageManager.RU;
        }

        try {
            java.util.Date date = dateFormat.parse(request.getParameter(PARAM_NAME_ARRIVAL));
            arrivalDate = new Date(date.getTime());
            date = dateFormat.parse(request.getParameter(PARAM_NAME_DEPARTURE));
            departureDate = new Date(date.getTime());
        } catch (ParseException e) {
            throw new CommandException(e);
        }

        application.setArrivalDate(arrivalDate);
        application.setDepartureDate(departureDate);
        application.setPlacesAmount(Integer.parseInt(request.getParameter(PARAM_NAME_PLACES)));
        application.setClientId(currentClient.getId());
        application.setFinalPrice(application.getPlacesAmount() * CURRENT_PRICE);
        application.setConfirmed(ConfirmationEnum.NO);
        application.getRoom().setType(request.getParameter(PARAM_NAME_ROOM));
        message = OrderLogic.checkApplication(application);

        if (message != null) {
            page = ConfigurationManager.getProperty((String) session.getAttribute(LAST_PAGE_ATTRIBUTE));
            session.setAttribute(ERROR_APPLICATION_ATTRIBUTE, messageManager.getMessage(message));
            return page;
        }

        try {
            if (applicationService.makeOrder(application)) {
                currentApplicationList = (List<Application>) session.getAttribute(USER_APPLICATION_LIST_ATTR);
                if (currentApplicationList == null) {
                    currentApplicationList = new ArrayList<>();
                }
                currentApplicationList.add(application);
                session.setAttribute(USER_APPLICATION_LIST_ATTR, currentApplicationList);
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        if (ROLE_USER.equals(session.getAttribute(ROLE_ATTRIBUTE))) {
            page = ConfigurationManager.getProperty("path.page.main_user");
        }
        if (ROLE_ADMIN.equals(session.getAttribute(ROLE_ATTRIBUTE))) {
            page = ConfigurationManager.getProperty("path.page.main_admin");
        }
        return page;
    }
}
