package by.bsu.hostel.command.additional;

import by.bsu.hostel.command.factory.ActionCommand;
import by.bsu.hostel.exception.CommandException;
import by.bsu.hostel.manager.ConfigurationManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by Kate on 29.03.2016.
 *
 * Class for going back with the rules of system
 *
 * @implements ActionCommand
 * @author Kate
 * @version 1.0
 */
public class BackCommand implements ActionCommand {
    private static final String BEFORE_PAGE_ATTRIBUTE = "before_page";
    static Logger log = Logger.getLogger(BackCommand.class);

    /**
     *Returns previous logic page
     *
     * @param request
     * @return String
     * @throws CommandException
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        return ConfigurationManager.getProperty((String) session.getAttribute(BEFORE_PAGE_ATTRIBUTE));
    }
}
