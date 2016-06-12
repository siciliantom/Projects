package by.bsu.hostel.pool;

import by.bsu.hostel.exception.PoolException;
import com.mysql.jdbc.Driver;
import org.apache.log4j.Logger;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Kate on 05.02.2016.
 *
 * Class which have a set number of connections
 * to use again
 *
 * @author Kate
 * @version 1.0
 */
public class ConnectionPool {
    private static final int POOL_SIZE = 4;
    private static final int ATTEMPTS_LIMIT = 2;
    private static ConnectionPool connectionPool = null;
    private volatile static boolean instanceCreated = false;
    private static ArrayBlockingQueue<ProxyConnection> connectionQueue;
    private static Lock lock = new ReentrantLock();
    static Logger log = Logger.getLogger(ConnectionPool.class);

    /**
     * Constructor for creating a queue of connections
     *
     * @param POOL_SIZE
     * @throws PoolException
     */
    private ConnectionPool(final int POOL_SIZE) throws PoolException {
        connectionQueue = new ArrayBlockingQueue<ProxyConnection>(POOL_SIZE);
        log.debug("Connecting to database...");
        int currentPoolSize = 0;
        int attemptsCount = 0;
        try {
            DriverManager.registerDriver(new Driver());
        } catch (SQLException e) {
            throw new PoolException("Can't create connection pool!" + e);
        }
        while (currentPoolSize < POOL_SIZE && attemptsCount < ATTEMPTS_LIMIT) {
            try {
                ProxyConnection proxyConnection = new ProxyConnection(ConnectorDB.getConnection());
                connectionQueue.offer(proxyConnection);
                currentPoolSize++;
            } catch (SQLException e) {
                attemptsCount++;
                log.warn("Can't get connection" + e);
            }
        }
        if (attemptsCount == ATTEMPTS_LIMIT) {
            throw new PoolException("Can't create connection pool!");
        }
    }

    /**
     * Method for retrieving the instance of ConnectionPool
     * from outside
     *
     * @return ConnectionPool
     */
    public static ConnectionPool getInstance() {
        if (!instanceCreated) {
            lock.lock();
            try {
                if (!instanceCreated) {
                    connectionPool = new ConnectionPool(POOL_SIZE);
                    instanceCreated = true;
                    log.debug("Pool created!");
                }
            } catch (PoolException e) {
                log.fatal(e);
                throw new ExceptionInInitializerError("Cant get pool instance");
            } finally {
                lock.unlock();
            }
        }
        return connectionPool;
    }

    /**
     * Method returns ProxyConnection from pool
     *
     * @return ProxyConnection
     */
    public static ProxyConnection getConnection() {
        ProxyConnection proxyConnection = null;
        try {
            proxyConnection = connectionQueue.take();
        } catch (InterruptedException e) {
            log.error("Can't get connection from queue!");
        }
        return proxyConnection;
    }

    /**
     * Method returns ProxyConnection back to
     * pool queue of connections
     *
     */
    public static void returnConnection(ProxyConnection connection) {
        connectionQueue.offer(connection);
    }

    /**
     * Method which closes pool and all its connections
     *
     */
    public static void closePool() {
        int currentPoolSize = 0;
        int attemptsCount = 0;
        while (currentPoolSize < connectionQueue.size() && attemptsCount < ATTEMPTS_LIMIT) {
            try {
                connectionQueue.poll().closeConnection();
                currentPoolSize++;
            } catch (SQLException e) {
                attemptsCount++;
                log.debug("Can't close connection!" + e);
            }
        }
        if (!connectionQueue.isEmpty()) {
            log.error("Can't close pool!");
        }
    }
}
