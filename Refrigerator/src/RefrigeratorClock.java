import java.util.Observable;

/**
 * @author Randy, Noah
 * <p>
 * This runs the clock on the refrigerator to allow time
 * when idling (warming) or cooling
 */
public class RefrigeratorClock extends Observable implements Runnable {

    private static RefrigeratorClock instance;
    private Thread thread = new Thread(this);

    /**
     * Events for clock
     */
    public enum Events {
        CLOCK_TICKED_EVENT
    }

    /**
     * Starts the thread when the clock object is created
     */
    private RefrigeratorClock() {
        thread.start();
    }

    /**
     * To get the instance
     *
     * @return returns the clock
     */
    public static RefrigeratorClock instance() {
        if (instance == null) {
            instance = new RefrigeratorClock();
        }
        return instance;
    }

    @Override
    /**
     * Runs the ticks every 1000 milliseconds or 1 second
     */
    public void run() {
        try {
            while (true) {
                Thread.sleep(1000);
                setChanged();
                notifyObservers(Events.CLOCK_TICKED_EVENT);
            }
        } catch (InterruptedException ie) {

        }
    }
}
