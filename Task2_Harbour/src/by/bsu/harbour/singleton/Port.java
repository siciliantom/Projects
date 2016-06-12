package by.bsu.harbour.singleton;

import by.bsu.harbour.entity.Moorage;
import by.bsu.harbour.entity.Ship;
import by.bsu.harbour.exception.MoorageException;
import org.apache.log4j.Logger;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Kate on 06.12.2015.
 */
public class Port {
    public static final int MAX_CONTAINERS_AMOUNT = 300;//storage
    public static final int MAX_MOORAGES_AMOUNT = 3;
    static Logger logger = Logger.getLogger(Port.class);
    private static Port port;
    private AtomicInteger currentContainersAmount;
    private Lock lock = new ReentrantLock();
    private Queue<Moorage> resources = new LinkedList<Moorage>();//ConcurrentLinkedQueue

    private Port() {
        for (int i = 0; i < MAX_MOORAGES_AMOUNT; i++) {
            resources.add(new Moorage());
        }
        currentContainersAmount = new AtomicInteger(100);
        logger.debug("Port was created");
    }

    public static Port getInstance() {
        if (port == null) {
            port = new Port();
        }
        return port;
    }

    public  void setCurrentContainersAmount(int amount) {
        this.currentContainersAmount.getAndSet(amount);
    }

    public boolean takeMoorage (Ship ship) {
        try {
           lock.lock();
           Thread.sleep(1000);
           ship.setOccupiedMoorage(resources.poll());
        }catch (InterruptedException | MoorageException e) {
            logger.error("***Ship " + ship.getShipName() + " can't land to the moorage");
            return false;
        } finally {
            lock.unlock();
        }
        return true;
    }

    public void releaseMoorage(Moorage moorage) {
        resources.add(moorage);
        logger.debug("***Moorage number " + moorage.getNumber() + " is released");
    }

    public int takeContainers(int amount) {
        if (currentContainersAmount.intValue() - amount >= 0) {
            currentContainersAmount.addAndGet(-amount);
            return amount;//successful
        }
        else {
            int untilEmpty = currentContainersAmount.intValue();
            currentContainersAmount.set(0);
            return untilEmpty;
        }
    }

    public int loadContainers(int amount) {
        if (currentContainersAmount.intValue() + amount <= MAX_CONTAINERS_AMOUNT) {
            currentContainersAmount.addAndGet(amount);
            return amount;//successful
        }
        else {
            int  untilFull = MAX_CONTAINERS_AMOUNT - currentContainersAmount.intValue();
            currentContainersAmount.addAndGet(untilFull);
            return untilFull;
        }
    }
}
