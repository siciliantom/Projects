package by.bsu.harbour.exception;

/**
 * Created by Kate on 12.12.2015.
 */
public class MoorageException extends Exception {
    public MoorageException(Throwable cause) {
        super(cause);
    }

    public MoorageException(String message, Throwable cause) {
        super(message, cause);
    }

    public MoorageException(String message) {
        super(message);
    }

    public MoorageException() {
    }
}
