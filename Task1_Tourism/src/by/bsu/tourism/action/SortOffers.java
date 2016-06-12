package by.bsu.tourism.action;

import by.bsu.tourism.entity.TourismOffer;
import by.bsu.tourism.entity.TravelAgency;
import by.bsu.tourism.util.DaysComparator;
import by.bsu.tourism.util.OfferTypeComparator;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Kate on 25.11.2015.
 */
public class SortOffers {
    public TravelAgency sortOffersByDays(TravelAgency travelAgency) {
        DaysComparator daysComparator = new DaysComparator();
        ArrayList<TourismOffer> offersListCopy = travelAgency.getOfferList();
        Collections.sort(offersListCopy, daysComparator);
        return new TravelAgency("IntouristCopy", offersListCopy);
    }

    public TravelAgency sortOffersByType(TravelAgency travelAgency) {
        OfferTypeComparator offerTypeComparator = new OfferTypeComparator();
        ArrayList<TourismOffer> offersListCopy = travelAgency.getOfferList();
        Collections.sort(offersListCopy, offerTypeComparator);
        return new TravelAgency("IntouristCopy", offersListCopy);
    }
}
