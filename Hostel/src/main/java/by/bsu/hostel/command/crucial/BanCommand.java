package by.bsu.hostel.command.crucial;

import by.bsu.hostel.command.factory.ActionCommand;
import by.bsu.hostel.domain.Application;
import by.bsu.hostel.domain.Client;
import by.bsu.hostel.domain.ConfirmationEnum;
import by.bsu.hostel.exception.CommandException;
import by.bsu.hostel.exception.ServiceException;
import by.bsu.hostel.manager.ConfigurationManager;
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
 * Class for banning or unbanning client
 *
 * @implements ActionCommand
 * @author Kate
 * @version 1.0
 */
public class BanCommand implements ActionCommand {
    private static final String BANNED_APPLICATIONS_ATTR = "bannedApplications";
    private static final String ADMIN_APPLICATION_LIST_ATTR = "applicationListAdmin";
    private static final String CLIENT_ID_PARAM = "client_id";
    private static final String CLIENTS_LIST_ATTRIBUTE = "clientsList";
    static Logger log = Logger.getLogger(BanCommand.class);

    /**
     * @Method for banning or unbanning client
     *
     * @param request
     * @return String
     * @throws CommandException
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String page = ConfigurationManager.getProperty("path.page.clients");
        String clientId = request.getParameter(CLIENT_ID_PARAM);
        HttpSession session = request.getSession();
        List<Client> clients = (List<Client>) session.getAttribute(CLIENTS_LIST_ATTRIBUTE);
        List<Application> applicationListAdmin = new ArrayList<>();
        List<Application> bannedApplications = new ArrayList<>();
        ClientService clientService = ClientService.getInstance();
        boolean doBan = false;

        if (clientId == null) {
            return page;
        }

        for (Client client : clients) {
            if (client.getId() == Long.parseLong(clientId)) {
                try {
                    if (client.getStatus().getBanned().equals(ConfirmationEnum.YES)) {
                        client.getStatus().setBanned(ConfirmationEnum.NO);
                        clientService.setUnban(clientId);
                    } else {
                        doBan = true;
                        client.getStatus().setBanned(ConfirmationEnum.YES);
                        clientService.setBan(clientId);
                    }
                } catch (ServiceException e) {
                    throw new CommandException(e);
                }
            }
        }

        if (doBan) {
            if (session.getAttribute(ADMIN_APPLICATION_LIST_ATTR) != null) {
                if (session.getAttribute(BANNED_APPLICATIONS_ATTR) != null) {
                    bannedApplications = (List<Application>) session.getAttribute(BANNED_APPLICATIONS_ATTR);
                }
                applicationListAdmin = (List<Application>) session.getAttribute(ADMIN_APPLICATION_LIST_ATTR);
                Iterator<Application> iterator = applicationListAdmin.iterator();
                Application currentApplication;
                while (iterator.hasNext()) {
                    currentApplication = iterator.next();
                    if (currentApplication.getClientId() == Long.parseLong(clientId)) {
                        bannedApplications.add(currentApplication);
                        iterator.remove();
                    }
                }
            }
        } else {
            if (session.getAttribute(BANNED_APPLICATIONS_ATTR) != null) {
                if (session.getAttribute(ADMIN_APPLICATION_LIST_ATTR) != null) {
                    applicationListAdmin = (List<Application>) session.getAttribute(ADMIN_APPLICATION_LIST_ATTR);
                }
                bannedApplications = (List<Application>) session.getAttribute(BANNED_APPLICATIONS_ATTR);
                Iterator<Application> iterator = bannedApplications.iterator();
                Application currentApplication;
                while (iterator.hasNext()) {
                    currentApplication = iterator.next();
                    if (currentApplication.getClientId() == Long.parseLong(clientId)) {
                        applicationListAdmin.add(currentApplication);
                        iterator.remove();
                    }
                }
            }
        }
        session.setAttribute(ADMIN_APPLICATION_LIST_ATTR, applicationListAdmin);//
        session.setAttribute(BANNED_APPLICATIONS_ATTR, bannedApplications);
        session.setAttribute(CLIENTS_LIST_ATTRIBUTE, clients);
        return page;
    }
}
