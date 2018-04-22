/**
 * @author Randy, Noah, Ricky
 * <p>
 * Base refrigerator state class that lets more states to be created if needed
 */
public abstract class RefrigeratorState {
    protected static RefrigeratorContext context;
    protected static RefrigeratorDisplay display;

    /**
     * Initializes the context and display
     */
    protected RefrigeratorState() {
        context = RefrigeratorContext.instance();
        display = context.getDisplay();
    }

    /**
     * Initializes the state
     */
    public abstract void run();

    /**
     * Handles an event
     *
     * @param event event to be processed
     */
    public abstract void handle(Object event);
}
