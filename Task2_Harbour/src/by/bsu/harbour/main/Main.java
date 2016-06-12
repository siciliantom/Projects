package by.bsu.harbour.main;

import by.bsu.harbour.entity.Ship;
import by.bsu.harbour.singleton.Port;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import java.util.concurrent.TimeUnit;

/**
 * Created by Kate on 06.12.2015.
 */
public class Main {
    static {
        new DOMConfigurator().doConfigure("log4j.xml", LogManager.getLoggerRepository());
    }
    static Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args) throws InterruptedException{
        Port port = Port.getInstance();
        Ship ship1 = new Ship("Alice", 45, port);
        Ship ship2 = new Ship("Waterloo", 40, port);
        Ship ship3 = new Ship("Victory", 30, port);
        Ship ship4 = new Ship("Vronsky", 10, port);
        Ship ship5 = new Ship("Petrograd", 0, port);
        ship1.start();
        ship2.start();
        ship3.start();
        ship4.start();
        ship5.start();
        TimeUnit.SECONDS.sleep(10);
        Ship.isWorkingDay = false;
        logger.debug("End of the main application");
    }
}
