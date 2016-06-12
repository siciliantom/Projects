package by.bsu.tourism.util;

import by.bsu.tourism.entity.TourismOffer;

import java.util.Comparator;

/**
* Created by Kate on 29.11.2015.
*/
public class DaysComparator implements Comparator<TourismOffer> {
    @Override
    public int compare(TourismOffer ob1, TourismOffer ob2) {
        return ob1.getAmountOfDays() - ob2.getAmountOfDays();
    }
}
