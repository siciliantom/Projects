package by.bsu.harbour.entity;

import by.bsu.harbour.exception.MoorageException;
import by.bsu.harbour.singleton.Port;
import org.apache.log4j.Logger;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by Kate on 06.12.2015.
 */
public class Ship extends Thread {
    public static boolean isWorkingDay = true;
    static Logger logger = Logger.getLogger(Ship.class);
    private static final int SHIP_CAPACITY = 50;//containers
    private int currentShipContainersAmount;
    private Moorage occupiedMoorage;
    private String shipName;
    private Port port;

    public Ship() {}

    public Ship(String name, int amountOfContainers, Port port) {
        super();
        this.shipName = name;
        this.currentShipContainersAmount = amountOfContainers;
        this.port = port;
        logger.debug("Ship was created with " + currentShipContainersAmount + " containers");
    }

    public Moorage getOccupiedMoorage() {
        return occupiedMoorage;
    }

    public String getShipName() {
        return shipName;
    }

    public void setOccupiedMoorage(Moorage occupiedMoorage) throws MoorageException {
        if(occupiedMoorage != null) {
            this.occupiedMoorage = occupiedMoorage;
            logger.debug("***Ship " + this.getShipName() + " occupied Moorage number " +
                    occupiedMoorage.getNumber());
        }
        else{
            throw new MoorageException("Moorage is not available!");
        }
    }

    @Override
    public void run() {
        while (isWorkingDay) {
            int whatToDo = new Random().nextInt(200);
            if (port.takeMoorage(this)) {//в порту
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    logger.error("Problems with ship during staying in port");
                }
                if ((whatToDo < 90 || currentShipContainersAmount == 0) //load
                        && currentShipContainersAmount != SHIP_CAPACITY) {
                    int amountToLoad = new Random().nextInt(SHIP_CAPACITY -
                            currentShipContainersAmount);
                    int takenAmount = port.takeContainers(amountToLoad);
                    if (takenAmount == amountToLoad) {
                        currentShipContainersAmount += amountToLoad;
                        logger.debug("***Ship " + this.getShipName() + " loaded " + takenAmount);
                    } else {
                        currentShipContainersAmount += takenAmount;
                        int restToLoad = amountToLoad - takenAmount;
                        logger.debug("***Ship " + this.getShipName() + "loaded " + takenAmount +
                                "instead of " + (restToLoad + takenAmount));
                    }
                } else {//unload
                    int amountToUnload = new Random().nextInt(currentShipContainersAmount);
                    int unLoaded = port.loadContainers(amountToUnload);
                    if (amountToUnload == unLoaded) {
                        currentShipContainersAmount -= amountToUnload;
                        logger.debug("***Ship " + this.getShipName() + " unloaded " + unLoaded);
                    } else {
                        currentShipContainersAmount -= unLoaded;
                        int restToUnload = amountToUnload - unLoaded;
                        logger.debug("***Ship " + this.getShipName() + " unload " + unLoaded +
                                " instead of " + (restToUnload + unLoaded));
                    }
                }
                logger.debug("***Ship " + this.getShipName() + " left the moorage");
                port.releaseMoorage(occupiedMoorage);
            } else {
                try {
                    logger.debug("***Ship " + this.getShipName() + " is waiting for a better time...");
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    logger.error("Problems with ship while waiting for moorage");
                }
            }
        }
   }
}
