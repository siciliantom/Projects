package by.bsu.tourism.action;

import by.bsu.tourism.entity.TourismOffer;
import by.bsu.tourism.entity.TravelAgency;

import java.util.ArrayList;

/**
 * Created by Kate on 29.11.2015.
 */
public class FindOffers {
    public ArrayList<TourismOffer> findOffersByTransport(TravelAgency travelAgency,
            String transport) {
        ArrayList<TourismOffer> foundOffers = new ArrayList<TourismOffer>();
        for (TourismOffer tourismOffer : travelAgency.getOfferList()) {
            if (transport.compareTo(tourismOffer.getTransport().toString()) == 0 ) {
                foundOffers.add(tourismOffer);
            }
        }
        return foundOffers;
    }

    public ArrayList<TourismOffer> findOffersByMeal(TravelAgency travelAgency,
            String meal) {
        ArrayList<TourismOffer> foundOffers = new ArrayList<TourismOffer>();
        for (TourismOffer tourismOffer : travelAgency.getOfferList()) {
            if(tourismOffer.getMealsIncluded().contains(TourismOffer.Meal.valueOf(meal))){
                foundOffers.add(tourismOffer);
            }
        }
        return foundOffers;
    }
    public ArrayList<TourismOffer> findOffersByDays(TravelAgency travelAgency, int amountOfDays) {
        ArrayList<TourismOffer> foundOffers = new ArrayList<TourismOffer>();
        for (TourismOffer tourismOffer : travelAgency.getOfferList()) {
            if(tourismOffer.getAmountOfDays() ==  amountOfDays){
                foundOffers.add(tourismOffer);
            }
        }
        return foundOffers;
    }
}
