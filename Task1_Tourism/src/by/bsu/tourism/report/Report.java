package by.bsu.tourism.report;

import by.bsu.tourism.entity.TourismOffer;
import by.bsu.tourism.entity.TravelAgency;

import java.util.ArrayList;

/**
 * Created by Kate on 26.11.2015.
 */
public class Report {
    public static void reportOffers(TravelAgency travelAgency) {
        System.out.println("Offers of "+ travelAgency);
    }
    public static void reportFoundOffers(ArrayList<TourismOffer> tourismOffers) {
        System.out.println("Suitable offers:");
        for (TourismOffer tourismOffer : tourismOffers) {
            System.out.println(tourismOffer.toString());
        }
    }
}
