package by.bsu.hostel.command.factory;

import by.bsu.hostel.exception.CommandException;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Kate on 08.02.2016.
 */
public interface ActionCommand {
    String execute(HttpServletRequest request) throws CommandException;
}
