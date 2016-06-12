package by.bsu.tourism.entity;

import org.apache.log4j.Logger;

import java.util.ArrayList;

/**
 * Created by Kate on 24.11.2015.
 */
public class MedicalTreatment extends TourismOffer {
    static Logger logger = Logger.getLogger(MedicalTreatment.class);
    public enum Treatment {FANGOTHERAPY, MASSAGE, PHYTOTHERAPY, INHALATION, SAUNA, PHOTOTHERAPY,
                          PSYCHOTHERAPY, DIETOTHERAPY, EXERCISES, MINERALWATER}
    private  Treatment treatment;

    public MedicalTreatment(ArrayList<Meal> mealsIncluded, Transport transport,
            int amountOfDays,  Treatment treatment) {
        super(mealsIncluded, transport, amountOfDays);
        this.treatment = treatment;
        logger.debug("MedicalTreatment entity was created");
    }

    @Override
    public String toString() {
        StringBuilder medicalTreatmentOutput = new StringBuilder(super.toString());
        medicalTreatmentOutput.append(" Medical treatment: " + treatment + '\n');
        return medicalTreatmentOutput.toString();
    }
}
