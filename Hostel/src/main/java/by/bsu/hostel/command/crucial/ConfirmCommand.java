package by.bsu.hostel.command.crucial;

import by.bsu.hostel.command.factory.ActionCommand;
import by.bsu.hostel.domain.Application;
import by.bsu.hostel.domain.ConfirmationEnum;
import by.bsu.hostel.exception.CommandException;
import by.bsu.hostel.exception.ServiceException;
import by.bsu.hostel.manager.ConfigurationManager;
import by.bsu.hostel.service.ApplicationService;
import by.bsu.hostel.service.ClientService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Kate on 05.02.2016.
 *
 * Class for confirming order
 *
 * @implements ActionCommand
 * @author Kate
 * @version 1.0
 */
public class ConfirmCommand implements ActionCommand {
    private static final String ADMIN_APPLICATION_LIST_ATTR = "applicationListAdmin";
    private static final String CONFIRMED_ADMIN_APPLICATION_LIST_ATTR = "applicationConfirmedListAdmin";
    private static final String ROOM_ID_PARAM = "room_id";
    private static final String APPLICATION_ID_ATTR = "applicationId";
    static Logger log = Logger.getLogger(ConfirmCommand.class);

    /**
     * @Method for confirming order
     *
     * @param request
     * @return
     * @throws CommandException
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        ClientService clientService;
        HttpSession session = request.getSession();
        List<Application> applicationListAdmin;
        List<Application> applicationConfirmedListAdmin = new ArrayList<>();
        ApplicationService applicationService = ApplicationService.getInstance();
        boolean confirmed = false;
        String roomId = request.getParameter(ROOM_ID_PARAM);

        if (roomId != null) {
            clientService = ClientService.getInstance();
            try {
                if (!clientService.checkBan(roomId)) {
                    Application application = new Application();
                    application.setId(Long.parseLong((String) session.getAttribute(APPLICATION_ID_ATTR)));
                    application.getRoom().setId(Long.parseLong(roomId));
                    confirmed = applicationService.confirmApplication(application);
                }
            } catch (ServiceException e) {
                throw new CommandException(e);
            }
        }
        if (confirmed) {
            try {
                applicationListAdmin = applicationService.currentApplications();
            } catch (ServiceException e) {
                throw new CommandException(e);
            }
            if (applicationListAdmin != null) {
                Iterator<Application> iterator = applicationListAdmin.iterator();
                Application currentApplication;
                while (iterator.hasNext()) {
                    currentApplication = iterator.next();
                    if (currentApplication.getConfirmed().equals(ConfirmationEnum.YES)) {
                        applicationConfirmedListAdmin.add(currentApplication);
                        iterator.remove();
                    }
                }
                session.setAttribute(ADMIN_APPLICATION_LIST_ATTR, applicationListAdmin);
                session.setAttribute(CONFIRMED_ADMIN_APPLICATION_LIST_ATTR, applicationConfirmedListAdmin);
            }
        }
        return ConfigurationManager.getProperty("path.page.main_admin");
    }
}

