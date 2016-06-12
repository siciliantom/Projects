package by.bsu.hostel.command.crucial;

import by.bsu.hostel.command.factory.ActionCommand;
import by.bsu.hostel.domain.Application;
import by.bsu.hostel.domain.Client;
import by.bsu.hostel.exception.CommandException;
import by.bsu.hostel.exception.ServiceException;
import by.bsu.hostel.manager.ConfigurationManager;
import by.bsu.hostel.service.ApplicationService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Kate on 05.02.2016.
 *
 * Class for getting history applications
 *
 * @implements ActionCommand
 * @author Kate
 * @version 1.0
 */
public class RetrieveHistoryCommand implements ActionCommand {
    private static final String ROLE_ADMIN = "admin";
    private static final String ROLE_USER = "user";
    private static final String ROLE_ATTRIBUTE = "role";
    private static final String BEFORE_PAGE_ATTRIBUTE = "before_page";
    private static final String CLIENT_ATTRIBUTE = "client";
    static Logger log = Logger.getLogger(RetrieveHistoryCommand.class);

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        ApplicationService applicationService = ApplicationService.getInstance();
        List<Application> applicationListAdmin = null;
        List<Application> applicationListUser = null;
        Client client = (Client) session.getAttribute(CLIENT_ATTRIBUTE);
        String page = null;
        try {
            if (ROLE_USER.equals(session.getAttribute(ROLE_ATTRIBUTE))) {
                applicationListUser = applicationService.applicationsHistoryById(client.getId());
                request.getSession().setAttribute(BEFORE_PAGE_ATTRIBUTE, "path.page.main_user");
                page = ConfigurationManager.getProperty("path.page.orders_history");
            }
            if (ROLE_ADMIN.equals(session.getAttribute(ROLE_ATTRIBUTE))) {
                applicationListAdmin = applicationService.applicationsHistory();
                request.getSession().setAttribute(BEFORE_PAGE_ATTRIBUTE, "path.page.main_admin");
                page = ConfigurationManager.getProperty("path.page.applications_history");
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        if (applicationListAdmin != null) {
            session.setAttribute("historyApplicationListAdmin", applicationListAdmin);
        }
        if (applicationListUser != null) {
            session.setAttribute("historyApplicationListUser", applicationListUser);
        }
        return page;
    }
}

