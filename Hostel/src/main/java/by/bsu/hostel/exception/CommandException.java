package by.bsu.hostel.exception;

/**
* Created by Kate on 16.02.2016.
*/
public class CommandException extends Exception{
    public CommandException() {
    }

    public CommandException(String message) {
        super(message);
    }

    public CommandException(Throwable cause) {
        super(cause);
    }

    public CommandException(String message, Throwable cause) {
        super(message, cause);
    }
}
