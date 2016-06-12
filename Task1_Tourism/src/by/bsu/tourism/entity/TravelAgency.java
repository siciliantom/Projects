package by.bsu.tourism.entity;

import java.util.ArrayList;

/**
 * Created by Kate on 25.11.2015.
 */
public class TravelAgency {
    private String agencyName;
    private ArrayList<TourismOffer> offerList;

    public TravelAgency(String agencyName, ArrayList<TourismOffer> offerList) {
        this.agencyName = agencyName;
        this.offerList = offerList;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public ArrayList<TourismOffer> getOfferList() {
        ArrayList<TourismOffer> copyOfOfferList = new ArrayList<TourismOffer>(offerList);
        return copyOfOfferList;
    }

    public void setOfferList(ArrayList<TourismOffer> offerList) {
        this.offerList = offerList;
    }

    @Override
    public String toString() {
        StringBuilder agencyOutput = new StringBuilder("TravelAgency: " + agencyName + '\n');
        for(TourismOffer offer : offerList){
            agencyOutput.append(offer.toString());
        }
        return agencyOutput.toString();
    }
}
