/**
 *
 * @author Randy, Noah, Ricky
 */
public abstract class RefrigeratorState {
    	protected static RefrigeratorContext context;
	protected static RefrigeratorDisplay display;

	/**
	 * Initialzies the context and display
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
	 * @param event
	 *            event to be processed
	 */
	public abstract void handle(Object event);
}
