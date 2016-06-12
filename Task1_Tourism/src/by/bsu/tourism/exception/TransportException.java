package by.bsu.tourism.exception;

/**
 * Created by Kate on 01.12.2015.
 */
public class TransportException extends Exception {
    public TransportException() {
    }

    public TransportException(String message) {
        super(message);
    }

    public TransportException(String message, Throwable cause) {
        super(message, cause);
    }
}
