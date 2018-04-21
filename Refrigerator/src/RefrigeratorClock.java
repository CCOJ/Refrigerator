
import java.util.Observable;

/**
 * @author Randy, Noah, Ricky
 *
 * This runs the clock on the refrigerator to allow time
 * when idling (warming) or cooling
 */
public class RefrigeratorClock extends Observable implements Runnable {

    private static RefrigeratorClock instance;
    
    public enum Events {
        CLOCK_TICKED_EVENT
    };
    
    private RefrigeratorClock() {
        new Thread(this).start();
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
