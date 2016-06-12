package by.bsu.tourism.exception;

/**
 * Created by Kate on 25.11.2015.
 */
public class CityException extends Exception {
    public CityException() {}

    public CityException(String message) {
        super(message);
    }

    public CityException(String message, Throwable cause) {
        super(message, cause);
    }

    public CityException(Throwable cause) {
        super(cause);
    }
}
