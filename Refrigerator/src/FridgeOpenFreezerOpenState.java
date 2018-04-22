/**
 * @author Randy, Noah
 * Fridge door opened; Freezer door open
 */
public class FridgeOpenFreezerOpenState extends RefrigeratorState {

    private static FridgeOpenFreezerOpenState instance;

    static {
        instance = new FridgeOpenFreezerOpenState();
    }

    /**
     * returns the instance
     *
     * @return this object
     */
    public static FridgeOpenFreezerOpenState instance() {
        return instance;
    }

    /**
     * Handle events
     */
    @Override
    public void handle(Object event) {
        if (event.equals(RefrigeratorContext
                .Events.FRIDGE_DOOR_CLOSED_EVENT)) {

            processFridgeClosedFreezerOpen();
        } else if (event.equals(RefrigeratorContext
                .Events.FREEZER_DOOR_CLOSED_EVENT)) {

            processFridgeOpenFreezerClosed();
        }
    }

    /**
     * handle door open event
     */
    public void processFridgeClosedFreezerOpen() {
        context.changeCurrentState(FridgeClosedFreezerOpenState.instance());
    }

    /**
     * handle door open event
     */
    public void processFridgeOpenFreezerClosed() {
        context.changeCurrentState(FridgeOpenFreezerClosedState.instance());
    }

    /**
     * initialize the state
     */
    @Override
    public void run() {
        display.freezerDoorOpened();
        display.fridgeDoorOpened();
        display.freezerLightOn();
        display.fridgeLightOn();
        context.setFreezerRateLoss(context.config
                .getProperty("FreezerRateLossDoorOpen"));
        context.setFridgeRateLoss(context.config
                .getProperty("FridgeRateLossDoorOpen"));
    }
}
