package by.bsu.hostel.command.factory;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
/**
 * Created by Kate on 05.02.2016.
 *
 * Class for generating commands
 *
 * @implements ActionCommand
 * @author Kate
 * @version 1.0
 */
public class ActionFactory {
    private static final String COMMAND_PARAM = "command";
    static Logger log = Logger.getLogger(ActionFactory.class);

    public static ActionCommand defineCommand(HttpServletRequest request) {
        ActionCommand current = new EmptyCommand();;
        String action = request.getParameter(COMMAND_PARAM);
        if (action == null || action.isEmpty()) {
            return current;
        }
        CommandEnum currentEnum = CommandEnum.valueOf(action.toUpperCase());
        current = currentEnum.getCurrentCommand();
        return current;
    }
}
