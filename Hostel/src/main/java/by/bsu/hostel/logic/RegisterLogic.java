package by.bsu.hostel.logic;

import by.bsu.hostel.domain.Client;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class for checking the register form for validness
 *
 * @author Kate
 * @version 1.0
 */
public class RegisterLogic {
    private static final String REGEX_LETTERS = "[a-zA-ZА-Яа-я]{2,24}";
    private static final String REGEX_LOGIN = "[\\wА-Яа-я1-9]{4,14}";
    private static final String REGEX_PASSWORD = "[\\wА-Яа-я1-9]{4,12}";

    public static String checkRegistration(Client client) {
        String message = null;
        if (client == null) {
            return "message.make_application";
        }
        Pattern lettersPattern = Pattern.compile(REGEX_LETTERS);
        Pattern loginPattern = Pattern.compile(REGEX_LOGIN);
        Pattern passwordPattern = Pattern.compile(REGEX_PASSWORD);

        Matcher loginMatcher = loginPattern.matcher(client.getAuthentication().getLogin());
        Matcher passwordMatcher = passwordPattern.matcher(client.getAuthentication().getPassword());
        Matcher nameMatcher = lettersPattern.matcher(client.getName());
        Matcher surnameMatcher = lettersPattern.matcher(client.getSurname());
        Matcher countryMatcher = lettersPattern.matcher(client.getCountry());

        if (!nameMatcher.matches()) {
            client.setName(null);
            message = "message.wrong_name";
        }
        if (!surnameMatcher.matches() && message == null) {
            client.setSurname(null);
            message = "message.wrong_surname";
        }
        if (!countryMatcher.matches() && message == null) {
            client.setCountry(null);
            message = "message.wrong_country";
        }
        if (!loginMatcher.matches() && message == null) {
            client.getAuthentication().setLogin(null);
            message = "message.login_reg_error";
        }
        if (!passwordMatcher.matches() && message == null) {
            client.getAuthentication().setPassword(null);
            message = "message.password_error";
        }
        return message;
    }
}
