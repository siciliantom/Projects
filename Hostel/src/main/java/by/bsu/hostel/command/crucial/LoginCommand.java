package by.bsu.hostel.command.crucial;

import by.bsu.hostel.command.factory.ActionCommand;
import by.bsu.hostel.domain.Application;
import by.bsu.hostel.domain.Authentication;
import by.bsu.hostel.domain.Client;
import by.bsu.hostel.domain.ConfirmationEnum;
import by.bsu.hostel.exception.CommandException;
import by.bsu.hostel.exception.ServiceException;
import by.bsu.hostel.manager.ConfigurationManager;
import by.bsu.hostel.manager.MessageManager;
import by.bsu.hostel.service.ApplicationService;
import by.bsu.hostel.service.ClientService;
import by.bsu.hostel.util.MD5Util;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Kate on 05.02.2016.
 *
 * Class for logging in
 *
 * @implements ActionCommand
 * @author Kate
 * @version 1.0
 */
public class LoginCommand implements ActionCommand {
    private static final String PARAM_NAME_LOGIN = "login";
    private static final String PARAM_NAME_PASSWORD = "password";
    private static final String ROLE_ADMIN = "admin";
    private static final String ROLE_USER = "user";
    private static final String CLIENT_ATTRIBUTE = "client";
    private static final String NAME_ATTRIBUTE = "name";
    private static final String LOCALE_ATTRIBUTE = "locale";
    private static final String EN = "en_US";
    private static final String ROLE_ATTRIBUTE = "role";
    private static final String BEFORE_PAGE_ATTRIBUTE = "before_page";
    private static final String CONFIRMED_ADMIN_APPLICATION_LIST_ATTR = "applicationConfirmedListAdmin";
    private static final String BANNED_APPLICATIONS_ATTR = "bannedApplications";
    private static final String ADMIN_APPLICATION_LIST_ATTR = "applicationListAdmin";
    private static final String USER_APPLICATION_LIST_ATTR = "applicationListUser";
    private static final String LOG_PASS_ERROR_MESSAGE = "errorLoginPassMessage";
    static Logger log = Logger.getLogger(LoginCommand.class);

    /**
     *
     * @param request
     * @return String
     * @throws CommandException
 */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        MessageManager messageManager;
        List<Application> applicationListAdmin = null;
        List<Application> bannedApplications = new ArrayList<>();
        List<Application> frozenApplications = new ArrayList<>();
        List<Application> applicationConfirmedListAdmin = new ArrayList<>();
        List<Application> applicationListUser = null;
        ClientService clientService = ClientService.getInstance();
        ApplicationService applicationService = ApplicationService.getInstance();
        Authentication authentication = new Authentication();
        Client client;
        String page = null;

        String login = request.getParameter(PARAM_NAME_LOGIN);
        String password = MD5Util.md5Apache(request.getParameter(PARAM_NAME_PASSWORD));
        authentication.setLogin(login);
        authentication.setPassword(password);

        if ((EN).equals(session.getAttribute(LOCALE_ATTRIBUTE))) {
            messageManager = MessageManager.EN;
        } else {
            messageManager = MessageManager.RU;
        }

        try {
            client = clientService.logIn(authentication);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        if (client != null) {
            session.setAttribute(ROLE_ATTRIBUTE, client.getAuthentication().getRole().name().toLowerCase());
            try {
                if (ROLE_USER.equals(session.getAttribute(ROLE_ATTRIBUTE))) {
                    applicationListUser = applicationService.currentApplicationsById(client.getId());
                    if (client.getStatus().getBanned().equals(ConfirmationEnum.YES)) {
                        Iterator<Application> iterator = applicationListUser.iterator();
                        Application currentApplication;
                        while (iterator.hasNext()) {
                            currentApplication = iterator.next();
                            if (currentApplication.getConfirmed().equals(ConfirmationEnum.NO)) {
                                frozenApplications.add(currentApplication);
                                iterator.remove();
                            }
                        }
                    }
                    session.setAttribute(BEFORE_PAGE_ATTRIBUTE, "path.page.login");
                    session.setAttribute("frozenApplications", frozenApplications);
                    page = ConfigurationManager.getProperty("path.page.main_user");
                }
                if (ROLE_ADMIN.equals(session.getAttribute(ROLE_ATTRIBUTE))) {
                    applicationListAdmin = applicationService.currentApplications();
                    session.setAttribute(BEFORE_PAGE_ATTRIBUTE, "path.page.login");
                    page = ConfigurationManager.getProperty("path.page.main_admin");
                }
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
                    } else {
                        try {
                            boolean banned = clientService.checkBan(String.valueOf(currentApplication.getClientId()));
                            if (banned) {
                                bannedApplications.add(currentApplication);
                                iterator.remove();
                            }
                        } catch (ServiceException e) {
                            throw new CommandException(e);
                        }
                    }
                }
                session.setAttribute(ADMIN_APPLICATION_LIST_ATTR, applicationListAdmin);
                session.setAttribute(BANNED_APPLICATIONS_ATTR, bannedApplications);
                session.setAttribute(CONFIRMED_ADMIN_APPLICATION_LIST_ATTR, applicationConfirmedListAdmin);
            }
            if (applicationListUser != null) {
                session.setAttribute(USER_APPLICATION_LIST_ATTR, applicationListUser);
            }
            session.setAttribute(CLIENT_ATTRIBUTE, client);
            session.setAttribute(NAME_ATTRIBUTE, client.getName());
        } else {
            session.setAttribute(LOG_PASS_ERROR_MESSAGE, messageManager.getMessage("message.login_error"));
            page = ConfigurationManager.getProperty("path.page.login");
        }
        return page;
    }
}
