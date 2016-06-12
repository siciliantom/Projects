package by.bsu.hostel.command.additional;

import by.bsu.hostel.command.factory.ActionCommand;
import by.bsu.hostel.exception.CommandException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by Kate on 29.03.2016.
 *
 * Class for changing the language of interface
 *
 * @implements ActionCommand
 * @author Kate
 * @version 1.0
 */
public class ChangeLangCommand implements ActionCommand {
    private static final String EN = "en_US";
    private static final String LAST_PAGE_ATTRIBUTE = "last_page";
    private static final String LOCALE_ATTRIBUTE = "locale";
    private static final String SELECT_RU_ATTRIBUTE = "selectRu";
    private static final String SELECT_EN_ATTRIBUTE = "selectEn";
    private static final String SELECTED = "selected";
    static Logger log = Logger.getLogger(ChangeLangCommand.class);

    /**
     *Method for changing the language of interface
     *
     * @param request
     * @return String
     * @throws CommandException
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        session.setAttribute(LOCALE_ATTRIBUTE, request.getParameter(LOCALE_ATTRIBUTE));
        if ((EN).equals(session.getAttribute(LOCALE_ATTRIBUTE))) {
            session.setAttribute(SELECT_RU_ATTRIBUTE, " ");
            session.setAttribute(SELECT_EN_ATTRIBUTE, SELECTED);
        } else {
            session.setAttribute(SELECT_RU_ATTRIBUTE, SELECTED);
            session.setAttribute(SELECT_EN_ATTRIBUTE, " ");
        }
        return session.getAttribute(LAST_PAGE_ATTRIBUTE).toString();
    }
}
