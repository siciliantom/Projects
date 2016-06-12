package by.bsu.tourism.entity;

import by.bsu.tourism.exception.CityException;
import org.apache.log4j.Logger;

import java.util.ArrayList;

/**
 * Created by Kate on 25.11.2015.
 */
public class Shopping extends TourismOffer {
    static Logger logger = Logger.getLogger(Shopping.class);
    public enum City {MILAN, MOSCOW, BARCELONA, MUNICH, BELOSTOK, VIENNA, BERLIN,
                     KIEV, WARSAW}
    private City city;

    public Shopping(ArrayList<Meal> mealsIncluded, Transport transport, int amountOfDays,
                    String city) throws CityException {
        super(mealsIncluded, transport, amountOfDays);
        boolean isValidCity = false;
        for (City currentCity : City.values()) {
            if (currentCity.name().equals(city.toString())) {
                this.city = currentCity;
                isValidCity = true;
            }
        }
        if(!isValidCity) {
            throw new CityException("This city is not available!");
        }
        logger.debug("Shopping entity was created");
    }

    @Override
    public String toString() {
        StringBuilder shoppingOutput = new StringBuilder(super.toString());
        shoppingOutput.append(" City: " + city + '\n');
        return shoppingOutput.toString();
    }
}
