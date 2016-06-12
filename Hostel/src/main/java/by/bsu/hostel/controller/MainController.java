package by.bsu.hostel.controller;

import by.bsu.hostel.command.factory.ActionCommand;
import by.bsu.hostel.command.factory.ActionFactory;
import by.bsu.hostel.exception.CommandException;
import by.bsu.hostel.manager.ConfigurationManager;
import by.bsu.hostel.pool.ConnectionPool;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Kate on 05.02.2016.
 *
 * Class for controlling commands
 *
 * @author Kate
 * @version 1.0
 */
@WebServlet("/controller")
public class MainController extends HttpServlet {
    private final String START_PAGE = "path.page.login";
    private static final String LAST_PAGE_ATTRIBUTE = "last_page";
    static Logger log = Logger.getLogger(MainController.class);

    @Override
    public void init() throws ServletException {
        super.init();
        ConnectionPool.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
        log.debug("In GET");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request,
                                HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String page = null;
        ActionCommand command = ActionFactory.defineCommand(request);
        if (session.getAttribute(LAST_PAGE_ATTRIBUTE) == null) {
            session.setAttribute(LAST_PAGE_ATTRIBUTE, ConfigurationManager.getProperty(START_PAGE));
        }
        try {
            page = command.execute(request);
        } catch (CommandException e) {
            log.error("Can't execute command!", e);
        }
        if (page != null) {
            if (request.getSession(false) != null) {
                session.setAttribute(LAST_PAGE_ATTRIBUTE, page);
            }
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
            dispatcher.forward(request, response);
        } else {
            log.error("No page to move!");
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        ConnectionPool.closePool();
    }
}
