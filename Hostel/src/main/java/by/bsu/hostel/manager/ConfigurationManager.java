package by.bsu.hostel.manager;

/**
 * Created by Kate on 08.02.2016.
 */

import java.util.ResourceBundle;
/**
 * Created by Kate on 05.02.2016.
 *
 * Class for configurating the jsp path
 *
 * @author Kate
 * @version 1.0
 */
public class ConfigurationManager {
    private final static ResourceBundle resourceBundle = ResourceBundle.getBundle("config");

    private ConfigurationManager() {
    }

    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }
}
