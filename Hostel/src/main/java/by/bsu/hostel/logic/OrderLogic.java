package by.bsu.hostel.logic;

import by.bsu.hostel.domain.Application;
import org.apache.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class for checking the order form for validness
 *
 * @author Kate
 * @version 1.0
 */
public class OrderLogic {
    private static final String REGEX_NUMBERS = "[1-8]{1}";
    static Logger log = Logger.getLogger(OrderLogic.class);

    public static String checkApplication(Application application) {
        String message = null;
        Matcher placesMatcher;
        Pattern numbersPattern = Pattern.compile(REGEX_NUMBERS);
        if (application != null) {
             placesMatcher = numbersPattern.matcher(Integer.toString(application.getPlacesAmount()));
            if (!placesMatcher.matches()) {
                message = "message.places_error";
            }
            if (application.getDepartureDate().before(application.getArrivalDate())) {
                message = "message.date_error";
            }
        } else {
            message = "message.make_application";
        }
        return message;
    }
}
