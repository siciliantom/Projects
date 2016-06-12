package by.bsu.hostel.command.crucial;

import by.bsu.hostel.command.factory.ActionCommand;
import by.bsu.hostel.domain.*;
import by.bsu.hostel.exception.CommandException;
import by.bsu.hostel.exception.ServiceException;
import by.bsu.hostel.logic.RegisterLogic;
import by.bsu.hostel.manager.ConfigurationManager;
import by.bsu.hostel.manager.MessageManager;
import by.bsu.hostel.service.ApplicationService;
import by.bsu.hostel.service.AuthenticationService;
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
 * Class for register client
 *
 * @implements ActionCommand
 * @author Kate
 * @version 1.0
 */
public class RegisterCommand implements ActionCommand {
    private static final String PARAM_NAME_FIRSTNAME = "name";
    private static final String PARAM_NAME_LASTNAME = "surname";
    private static final String PARAM_NAME_COUNTRY = "country";
    private static final String PARAM_NAME_LOGIN = "login";
    private static final String PARAM_NAME_PASSWORD = "password";
    private static final String PARAM_NAME_ROLE = "client_role";
    private static final String ROLE_ADMIN = "admin";
    private static final String ROLE_USER = "user";
    private static final String ROLE_ATTRIBUTE = "role";
    private static final String CLIENT_ROLE_PARAM = "client_role";
    private static final String CLIENT_ATTRIBUTE = "client";
    private static final String NAME_ATTRIBUTE = "name";
    private static final String LOCALE_ATTRIBUTE = "locale";
    private static final String SELECT_USER_ATTR = "selectUser";
    private static final String SELECT_ADMIN_ATTR = "selectAdmin";
    private static final String REGISTRATION_CLIENT_ATTR = "registration_client";
    private static final String SELECTED = "selected";
    private static final String CONFIRMED_ADMIN_APPLICATION_LIST_ATTR = "applicationConfirmedListAdmin";
    private static final String BANNED_APPLICATIONS_ATTR = "bannedApplications";
    private static final String ADMIN_APPLICATION_LIST_ATTR = "applicationListAdmin";
    private static final String USER_APPLICATION_LIST_ATTR = "applicationListUser";
    private static final String EN = "en_US";
    static Logger log = Logger.getLogger(RegisterCommand.class);

    /**
     *
     *
     * @param request
     * @return String
     * @throws CommandException
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        MessageManager messageManager;
        List<Application> bannedApplications = new ArrayList<>();
        List<Application> applicationConfirmedListAdmin = new ArrayList<>();
        ClientService clientService = ClientService.getInstance();
        AuthenticationService authenticationService = AuthenticationService.getInstance();
        ApplicationService applicationService = ApplicationService.getInstance();
        List<Application> applicationListAdmin = null;
        List<Application> applicationListUser = null;
        String clientExistMessage;
        String page = null;
        String message;
        Client client;
        Authentication authentication = new Authentication();
        String password = request.getParameter(PARAM_NAME_PASSWORD);
        String login = request.getParameter(PARAM_NAME_LOGIN);
        authentication.setLogin(login);
        authentication.setPassword(password);

        if ((EN).equals(request.getSession().getAttribute(LOCALE_ATTRIBUTE))) {
            messageManager = MessageManager.EN;
        } else {
            messageManager = MessageManager.RU;
        }

        try {
            clientExistMessage = authenticationService.checkExistence(authentication);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        if (clientExistMessage == null) {
            client = new Client();
            client.setName(request.getParameter(PARAM_NAME_FIRSTNAME));
            client.setSurname(request.getParameter(PARAM_NAME_LASTNAME));
            client.setCountry(request.getParameter(PARAM_NAME_COUNTRY));
            client.getAuthentication().setLogin(login);
            client.getAuthentication().setPassword(password);
            client.getAuthentication().setRole(RoleEnum.valueOf(request.getParameter(PARAM_NAME_ROLE).toUpperCase()));
            message = RegisterLogic.checkRegistration(client);

            if (message == null) {
                client.getAuthentication().setPassword(MD5Util.md5Apache(request.getParameter(PARAM_NAME_PASSWORD)));
                try {
                    clientService.register(client);
                    session.setAttribute(CLIENT_ATTRIBUTE, client);
                    session.setAttribute(ROLE_ATTRIBUTE, client.getAuthentication().getRole().name().toLowerCase());
                    session.setAttribute(NAME_ATTRIBUTE, client.getName());

                    if (ROLE_USER.equals(session.getAttribute(ROLE_ATTRIBUTE))) {
                        page = ConfigurationManager.getProperty("path.page.main_user");
                    }
                    if (ROLE_ADMIN.equals(session.getAttribute(ROLE_ATTRIBUTE))) {
                        applicationListAdmin = applicationService.currentApplications();
                        page = ConfigurationManager.getProperty("path.page.main_admin");
                    }
                } catch (ServiceException e) {
                    log.error("can't add to database");
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
                        session.setAttribute(ADMIN_APPLICATION_LIST_ATTR, applicationListAdmin);
                        session.setAttribute(BANNED_APPLICATIONS_ATTR, bannedApplications);
                        session.setAttribute(CONFIRMED_ADMIN_APPLICATION_LIST_ATTR, applicationConfirmedListAdmin);
                    }
                }
                if (applicationListUser != null) {
                    session.setAttribute(USER_APPLICATION_LIST_ATTR, applicationListUser);
                }
            } else {
                if (RoleEnum.ADMIN.name().toLowerCase().equals(request.getParameter(CLIENT_ROLE_PARAM))) {
                    session.setAttribute(SELECT_USER_ATTR, " ");
                    session.setAttribute(SELECT_ADMIN_ATTR, SELECTED);
                } else {
                    session.setAttribute(SELECT_USER_ATTR, SELECTED);
                    session.setAttribute(SELECT_ADMIN_ATTR, " ");
                }
                session.setAttribute(REGISTRATION_CLIENT_ATTR, client);
                session.setAttribute("wrongRegistration", messageManager.getMessage(message));
                page = ConfigurationManager.getProperty("path.page.register");
            }
        } else {
            request.setAttribute("warningClientExists", messageManager.getMessage(clientExistMessage));
            page = ConfigurationManager.getProperty("path.page.register");
        }
        return page;
    }
}
