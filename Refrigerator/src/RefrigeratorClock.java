public class RefrigeratorClock implements Runnable {

    private static Refrigerator refrigerator;

    public RefrigeratorClock() {
        refrigerator = Refrigerator.instance();
        new Thread(this).start();
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
