package by.bsu.hostel.command.additional;
import by.bsu.hostel.command.factory.ActionCommand;
import by.bsu.hostel.manager.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
/**
 * Created by Kate on 05.02.2016.
*
 * Class for logging out
 *
 * @implements ActionCommand
 * @author Kate
 * @version 1.0
 */
public class LogoutCommand implements ActionCommand {
    /**
     * Method invalidates session
     *
     * @param request
     * @return String
     */
    @Override
    public String execute(HttpServletRequest request) {
        String page = ConfigurationManager.getProperty("path.page.login");
        if (request.getSession(false) != null) {
            request.getSession().invalidate();
        }
        return page;
    }
}
