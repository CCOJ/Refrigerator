/**
 * @author Randy, Noah, Ricky
 *
 * This runs the clock on the refrigerator to allow time
 * when idling (warming) or cooling
 */
public class RefrigeratorClock implements Runnable {

    private static Refrigerator refrigerator;
    private static RefrigeratorClock instance;
    
    private RefrigeratorClock() {
        refrigerator = Refrigerator.instance();
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
                refrigerator.clockTicked();
            }
        } catch (InterruptedException ie) {

        }
    }
}
