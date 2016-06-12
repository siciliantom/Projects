package by.bsu.tourism.entity;

import org.apache.log4j.Logger;

import java.util.ArrayList;

/**
 * Created by Kate on 24.11.2015.
 */
public class Cruise extends TourismOffer {
    static Logger logger = Logger.getLogger(Cruise.class);
    public enum Route {ROUND_THE_WORLD, COASTAL, TRANSATLANTIC, EXPEDITIONARY}
    private Route route;

    public Cruise( ArrayList<Meal> mealsIncluded, Transport transport, int amountOfDays,
            Route route) {
        super(mealsIncluded, transport, amountOfDays);
        this.route = route;
        logger.debug("Cruise entity was created");
    }

    @Override
    public String toString() {
        StringBuilder cruiseOutput = new StringBuilder(super.toString());
        cruiseOutput.append(" Route: " + route + '\n');
        return cruiseOutput.toString();
    }
}
