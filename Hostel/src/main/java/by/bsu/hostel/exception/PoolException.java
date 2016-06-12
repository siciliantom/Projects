package by.bsu.hostel.exception;

/**
 * Created by Kate on 16.02.2016.
 */
public class PoolException extends Exception {
    public PoolException() {
    }

    public PoolException(String message) {
        super(message);
    }

    public PoolException(String message, Throwable cause) {
        super(message, cause);
    }

    public PoolException(Throwable cause) {
        super(cause);
    }
}

