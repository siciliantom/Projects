package by.bsu.hostel.command.factory;

/**
 * Created by Kate on 05.02.2016.
 */

import by.bsu.hostel.manager.ConfigurationManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class EmptyCommand implements ActionCommand {
    static Logger log = Logger.getLogger(EmptyCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        String page = ConfigurationManager.getProperty("path.page.login");
        return page;
    }
}
