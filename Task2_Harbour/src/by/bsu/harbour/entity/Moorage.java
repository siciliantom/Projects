package by.bsu.harbour.entity;

import org.apache.log4j.Logger;

import java.util.Random;

/**
 * Created by Kate on 06.12.2015.
 */
public class Moorage {
    static Logger logger = Logger.getLogger(Moorage.class);
    private int number;

    public Moorage() {
        this.number = new Random().nextInt(100);
        logger.debug("Moorage was created with number: " + number);
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
