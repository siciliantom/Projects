package by.bsu.tourism.entity;

import org.apache.log4j.Logger;

import java.util.ArrayList;

/**
 * Created by Kate on 24.11.2015.
 */
public abstract class TourismOffer {
    static Logger logger = Logger.getLogger(TourismOffer.class);

    public enum Transport {CAR, BUS, TRAIN, PLANE, LINER}
    public enum Meal {BREAKFAST, LUNCH, DINNER, SUPPER, SNACK, WITHOUT_MEAL}

    private ArrayList<Meal> mealsIncluded;
    private Transport transport;
    private int amountOfDays;

    public TourismOffer(ArrayList<Meal> mealsIncluded, Transport transport, int amountOfDays) {
        this.mealsIncluded = mealsIncluded;
        this.transport = transport;
        this.amountOfDays = amountOfDays;
    }

    public ArrayList<Meal> getMealsIncluded() {
        return mealsIncluded;
    }

    public void setMealsIncluded(ArrayList<Meal> mealsIncluded) {
        this.mealsIncluded = mealsIncluded;
    }

    public Transport getTransport() {
        return transport;
    }

    public void setTransport(Transport transport) {
        this.transport = transport;
    }

    public int getAmountOfDays() {
        return amountOfDays;
    }

    public void setAmountOfDays(int amountOfDays) {
        this.amountOfDays = amountOfDays;
    }

    @Override
    public String toString() {
        StringBuilder offerOutput = new StringBuilder(this.getClass() + "\n Meals: ");
        for(Meal meal : mealsIncluded){
            offerOutput.append(meal.toString() + " ");
        }
        offerOutput.append("\n Transport: " + transport);
        offerOutput.append("\n For " + amountOfDays + " days \n");
        return offerOutput.toString();
    }
}
