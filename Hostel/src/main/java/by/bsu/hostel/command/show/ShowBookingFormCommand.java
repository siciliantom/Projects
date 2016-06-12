package by.bsu.hostel.command.show;

import by.bsu.hostel.command.factory.ActionCommand;
import by.bsu.hostel.domain.Client;
import by.bsu.hostel.domain.ConfirmationEnum;
import by.bsu.hostel.exception.CommandException;
import by.bsu.hostel.manager.ConfigurationManager;
import by.bsu.hostel.manager.MessageManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * Created by Kate on 05.02.2016.
 *
 * Class for showing booking form
 *
 * @implements ActionCommand
 * @author Kate
 * @version 1.0
 */
public class ShowBookingFormCommand implements ActionCommand {
    private static final int DAY_IN_MS = 24 * 60 * 60 * 1000;
    private static final String EN = "en_US";
    private static final String LOCALE_ATTRIBUTE = "locale";
    private static final String CLIENT_ATTRIBUTE = "client";
    private static final String TODAY_ATTRIBUTE = "today";
    private static final String TOMORROW_ATTRIBUTE = "tomorrow";
    private static final String BEFORE_PAGE_ATTRIBUTE = "before_page";

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        MessageManager messageManager;
        Date date = new Date();
        Client client = (Client) session.getAttribute(CLIENT_ATTRIBUTE);

        if ((EN).equals(session.getAttribute(LOCALE_ATTRIBUTE))) {
            messageManager = MessageManager.EN;
        } else {
            messageManager = MessageManager.RU;
        }

        session.setAttribute(TODAY_ATTRIBUTE, new java.sql.Date(date.getTime()));
        session.setAttribute(TOMORROW_ATTRIBUTE, new java.sql.Date(date.getTime() + DAY_IN_MS));

        if (client.getStatus().getBanned().equals(ConfirmationEnum.YES)) {
            request.setAttribute("accessPermissionError", messageManager.getMessage("message.ban_error"));
            return ConfigurationManager.getProperty("path.page.main_user");
        }
        session.setAttribute(BEFORE_PAGE_ATTRIBUTE, "path.page.main_user");
        return ConfigurationManager.getProperty("path.page.order");
    }
}
