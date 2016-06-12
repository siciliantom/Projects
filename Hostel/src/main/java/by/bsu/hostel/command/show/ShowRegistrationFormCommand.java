package by.bsu.hostel.command.show;

import by.bsu.hostel.command.factory.ActionCommand;
import by.bsu.hostel.exception.CommandException;
import by.bsu.hostel.manager.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Kate on 03.03.2016.
 */
public class ShowRegistrationFormCommand implements ActionCommand {
    private static final String BEFORE_PAGE_ATTRIBUTE = "before_page";

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        request.getSession().setAttribute(BEFORE_PAGE_ATTRIBUTE, "path.page.login");
        return ConfigurationManager.getProperty("path.page.register");
    }
}
