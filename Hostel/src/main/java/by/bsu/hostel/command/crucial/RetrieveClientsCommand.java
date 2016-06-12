package by.bsu.hostel.command.crucial;

import by.bsu.hostel.command.factory.ActionCommand;
import by.bsu.hostel.domain.Client;
import by.bsu.hostel.exception.CommandException;
import by.bsu.hostel.exception.ServiceException;
import by.bsu.hostel.manager.ConfigurationManager;
import by.bsu.hostel.service.ClientService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Kate on 05.02.2016.
 *
 * Class for getting clients
 *
 * @implements ActionCommand
 * @author Kate
 * @version 1.0
 */
public class RetrieveClientsCommand implements ActionCommand {
    private static final String BEFORE_PAGE_ATTR = "before_page";
    private static final String CLIENTS_LIST_ATTR = "clientsList";
    static Logger log = Logger.getLogger(RetrieveClientsCommand.class);

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        ClientService clientService = ClientService.getInstance();
        List<Client> clientsList;
        try {
            clientsList = clientService.findAll();
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        if (clientsList != null) {
            session.setAttribute(CLIENTS_LIST_ATTR, clientsList);
        }
        session.setAttribute(BEFORE_PAGE_ATTR, "path.page.main_admin");
        return ConfigurationManager.getProperty("path.page.clients");
    }
}
