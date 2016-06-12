package by.bsu.tourism.creator;

import by.bsu.tourism.entity.*;
import by.bsu.tourism.exception.CityException;
import org.apache.log4j.Logger;

import java.util.ArrayList;

/**
 * Created by Kate on 25.11.2015.
 */
public class TravelAgencyCreator {
    static Logger logger = Logger.getLogger(TravelAgencyCreator.class);

    public static TravelAgency createAgency() throws CityException {
        ArrayList<TourismOffer.Meal> mealList1 = new ArrayList<TourismOffer.Meal>();
        mealList1.add(TourismOffer.Meal.BREAKFAST);
        mealList1.add(TourismOffer.Meal.DINNER);

        ArrayList<TourismOffer.Meal> mealList2 = new ArrayList<TourismOffer.Meal>();
        mealList2.add(TourismOffer.Meal.BREAKFAST);

        ArrayList<TourismOffer.Meal> mealList3 = new ArrayList<TourismOffer.Meal>();
        mealList3.add(TourismOffer.Meal.WITHOUT_MEAL);

        ArrayList<TourismOffer.Meal> mealList4 = new ArrayList<TourismOffer.Meal>();
        mealList4.add(TourismOffer.Meal.BREAKFAST);
        mealList4.add(TourismOffer.Meal.DINNER);
        mealList4.add(TourismOffer.Meal.LUNCH);
        mealList4.add(TourismOffer.Meal.SNACK);
        mealList4.add(TourismOffer.Meal.SUPPER);

        ArrayList<TourismOffer> offersList = new ArrayList<TourismOffer>();

        Shopping shopping1 = new Shopping(mealList1, TourismOffer.Transport.BUS, 5, "MUNICH");
        Shopping shopping2 = new Shopping(mealList2, TourismOffer.Transport.CAR, 3, "WARSAW");

        Cruise cruise1 = new Cruise(mealList3, TourismOffer.Transport.PLANE,
                14, Cruise.Route.TRANSATLANTIC );
        Excursion excursion1 = new Excursion(mealList2, TourismOffer.Transport.TRAIN,
                7, Excursion.Attraction.CARNIVAL);
        Excursion excursion2 = new Excursion(mealList3, TourismOffer.Transport.PLANE,
                4, Excursion.Attraction.GALLERY);
        MedicalTreatment treatment1 = new MedicalTreatment(mealList4, TourismOffer.Transport.LINER,
                12, MedicalTreatment.Treatment.PHYTOTHERAPY );
        offersList.add(shopping1);
        offersList.add(treatment1);
        offersList.add(cruise1);
        offersList.add(excursion1);
        offersList.add(shopping2);
        offersList.add(excursion2);

        TravelAgency travelAgency = new TravelAgency("Intourist", offersList);
        logger.debug("TravelAgency created");
        return travelAgency;
    }
}
