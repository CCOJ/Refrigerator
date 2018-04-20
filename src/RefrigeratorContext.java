
import java.util.Observable;
import java.util.Observer;

/**
 * The context is an obserer for the clock and stores the context info for
 * states
 *
 * @authors Randy, Noah, Ricky
 */
public class RefrigeratorContext implements Observer {
	public static enum Events {
		FRIDGE_DOOR_CLOSED_FREEZER_DOOR_CLOSED_EVENT,
                FRIDGE_DOOR_CLOSED_FREEZER_DOOR_OPEN_EVENT,
                FRIDGE_DOOR_OPEN_FREEZER_DOOR_CLOSED_EVENT,
                FRIDGE_DOOR_OPEN_FREEZER_DOOR_OPEN_EVENT
	};

	private static RefrigeratorDisplay RefrigeratorDisplay;
	private RefrigeratorState currentState;
	private static RefrigeratorContext instance;
	static {
		instance = new RefrigeratorContext();
		RefrigeratorDisplay = RefrigeratorDisplay.instance();
	}

	/**
	 * Make it a singleton
	 */
	private RefrigeratorContext() {
	}

	/**
	 * Return the instance
	 * 
	 * @return the object
	 */
	public static RefrigeratorContext instance() {
		if (instance == null) {
			instance = new RefrigeratorContext();
		}
		return instance;
	}

	public void initialize() {
		instance.changeCurrentState(DoorClosedState.instance());
		RefrigeratorClock.instance().addObserver(instance);
	}

	/**
	 * For observer
	 * 
	 * @param observable
	 *            will be the clock
	 * @param arg
	 *            the event that clock has ticked
	 */
	@Override
	public void update(Observable observable, Object arg) {
		currentState.handle(arg);
	}

	/**
	 * handle one of the several other events such as door close
	 * 
	 * @param arg
	 *            the event from the GUI
	 */
	public void processEvent(Object arg) {
		currentState.handle(arg);
	}

	/**
	 * Called from the states to change the current state
	 * 
	 * @param nextState
	 *            the next state
	 */
	public void changeCurrentState(RefrigeratorState nextState) {
		currentState = nextState;
		nextState.run();
	}

	/**
	 * Gets the display
	 * 
	 * @return the display
	 */
	public RefrigeratorDisplay getDisplay() {
		return RefrigeratorDisplay;
	}

}