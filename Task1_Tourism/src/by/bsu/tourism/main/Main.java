package by.bsu.tourism.main;

import by.bsu.tourism.action.FindOffers;
import by.bsu.tourism.action.SortOffers;
import by.bsu.tourism.creator.TravelAgencyCreator;
import by.bsu.tourism.entity.TourismOffer;
import by.bsu.tourism.entity.TravelAgency;
import by.bsu.tourism.exception.CityException;
import by.bsu.tourism.report.Report;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import java.util.ArrayList;

/**
 * Created by Kate on 24.11.2015.
 */
public class Main {
    static {
        new DOMConfigurator().doConfigure("log4j.xml", LogManager.getLoggerRepository());
    }
    static Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args){
        logger.info("Application started");
        TravelAgency travelAgency = null;
        TravelAgencyCreator travelAgencyCreator = new TravelAgencyCreator();
        try {
            travelAgency = TravelAgencyCreator.createAgency();
            logger.info("Output of initial agency:");
            Report.reportOffers(travelAgency);

            SortOffers sortOffers = new SortOffers();
            TravelAgency sortedByTypeTravelAgency = sortOffers.sortOffersByType(travelAgency);
            logger.info("Output of offers sorted by type: ");
            Report.reportOffers(sortedByTypeTravelAgency);

            TravelAgency sortedByDaysTravelAgency = sortOffers.sortOffersByDays(travelAgency);
            logger.info("Output of offers sorted by days: ");
            Report.reportOffers(sortedByDaysTravelAgency);

            FindOffers findOffers = new FindOffers();
            ArrayList<TourismOffer> foundOffersByTransport = findOffers.findOffersByTransport(travelAgency, "BUS");
            logger.info("Found offers by transport");
            Report.reportFoundOffers(foundOffersByTransport);

            ArrayList<TourismOffer> foundOffersByMeal = findOffers.findOffersByMeal(travelAgency, "BREAKFAST");
            logger.info("Found offers by meal");
            Report.reportFoundOffers(foundOffersByMeal);

            ArrayList<TourismOffer> foundOffersByDays = findOffers.findOffersByDays(travelAgency, 12);
            logger.info("Found offers by days");
            Report.reportFoundOffers(foundOffersByDays);
        } catch (CityException e) {
            logger.error("Wrong creation of TravelAgency!");
        }
        logger.info("Application ended");
    }
}
