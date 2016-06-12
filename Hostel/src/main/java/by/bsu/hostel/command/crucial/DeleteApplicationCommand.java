package by.bsu.hostel.command.crucial;

import by.bsu.hostel.command.factory.ActionCommand;
import by.bsu.hostel.domain.Application;
import by.bsu.hostel.domain.Client;
import by.bsu.hostel.exception.CommandException;
import by.bsu.hostel.exception.ServiceException;
import by.bsu.hostel.service.ApplicationService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Kate on 05.02.2016.
 *
 * Class for deleteing applications
 *
 * @implements ActionCommand
 * @author Kate
 * @version 1.0
 */
public class DeleteApplicationCommand implements ActionCommand {
    private static final String ROLE_ADMIN = "admin";
    private static final String ROLE_USER = "user";
    private static final String ROLE_ATTRIBUTE = "role";
    private static final String ROW_ID_PARAM= "rowid";
    private static final String CLIENT_ATTRIBUTE = "client";
    private static final String ADMIN_APPLICATION_LIST_ATTR = "applicationListAdmin";
    private static final String USER_APPLICATION_LIST_ATTR = "applicationListUser";
    private static final String LAST_PAGE_ATTRIBUTE = "last_page";
    static Logger log = Logger.getLogger(DeleteApplicationCommand.class);

    /**
     *
     * @param request
     * @return String
     * @throws CommandException
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        List<Application> applicationListAdmin = null;
        List<Application> applicationListUser = null;
        ApplicationService applicationService = null;
        boolean isdDeleted = false;
        HttpSession session = request.getSession();
        Client client = (Client) session.getAttribute(CLIENT_ATTRIBUTE);

        if (request.getParameterValues(ROW_ID_PARAM) != null) {
            List<String> rowIds = new ArrayList(Arrays.asList(request.getParameterValues(ROW_ID_PARAM)));
            applicationService = ApplicationService.getInstance();
            try {
                isdDeleted = applicationService.deleteById(rowIds);
            } catch (ServiceException e) {
                throw new CommandException(e);
            }
        }
        if (isdDeleted) {
            try {
                if (ROLE_USER.equals(session.getAttribute(ROLE_ATTRIBUTE))) {
                    applicationListUser = applicationService.currentApplicationsById(client.getId());
                }
                if (ROLE_ADMIN.equals(session.getAttribute(ROLE_ATTRIBUTE))) {
                    applicationListAdmin = applicationService.currentApplications();
                }
            } catch (ServiceException e) {
                throw new CommandException(e);
            }
            session.setAttribute(ADMIN_APPLICATION_LIST_ATTR, applicationListAdmin);
            session.setAttribute(USER_APPLICATION_LIST_ATTR, applicationListUser);
        }
        return session.getAttribute(LAST_PAGE_ATTRIBUTE).toString();
    }
}
