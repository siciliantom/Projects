package by.bsu.tourism.entity;

import org.apache.log4j.Logger;

import java.util.ArrayList;

/**
 * Created by Kate on 24.11.2015.
 */
public class Excursion extends TourismOffer {
    static Logger logger = Logger.getLogger(Excursion.class);
    public enum Attraction {GALLERY, MUSEUM, GARDEN, ZOO, MONUMENT, PARK, CASTLE,
                            BRIDGE, PRISON, FARM, CARNIVAL, FAIR}
    private  Attraction attraction;

    public Excursion(ArrayList<Meal> mealsIncluded, Transport transport, int amountOfDays,
            Attraction attraction) {
        super(mealsIncluded, transport, amountOfDays);
        this.attraction = attraction;
        logger.debug("Excursion entity was created");
    }

    @Override
    public String toString() {
        StringBuilder excursionOutput = new StringBuilder(super.toString());
        excursionOutput.append(" Attraction: " + attraction +'\n');
        return excursionOutput.toString();
    }
}
