
import java.util.Observable;
import java.util.Observer;

/**
 * The context is an obserer for the clock and stores the context info for
 * states
 *
 * @authors Randy, Noah, Ricky
 */
public class RefrigeratorContext implements Observer {

    /**
     * Events for all states of doors being opened or closed
     */
    public static enum Events {
        FRIDGE_DOOR_CLOSED_EVENT,
        FRIDGE_DOOR_OPENED_EVENT,
        FREEZER_DOOR_CLOSED_EVENT,
        FREEZER_DOOR_OPENED_EVENT
    }

    private static RefrigeratorDisplay refrigeratorDisplay;
    private RefrigeratorState currentState;
    private static RefrigeratorContext instance;

    static {
        instance = new RefrigeratorContext();
        refrigeratorDisplay = RefrigeratorDisplay.instance();
    }

    public Config config = new Config();

    /**
     * Variables used globally will be here in the context class.
     */
    private boolean fridgeCompressorOn;
    private boolean freezerCompressorOn;
    private int fridgeRateLoss =
            config.getProperty("FridgeRateLossDoorClosed");
    private int freezerRateLoss =
            config.getProperty("FreezerRateLossDoorClosed");
    private int roomTemp = config.getProperty("RoomLow");
    private int fridgeTemp = config.getProperty("RoomLow");
    private int freezerTemp = config.getProperty("RoomLow");
    private int desiredFridgeTemp = config.getProperty("FridgeLow");
    private int desiredFreezerTemp = config.getProperty("FreezerLow");
    private int time = 0;

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
        instance.changeCurrentState(FridgeClosedFreezerClosedState.instance());
        RefrigeratorClock.instance().addObserver(instance);
    }

    /**
     * For observer
     *
     * @param observable will be the clock
     * @param arg        the event that clock has ticked
     */
    @Override
    public void update(Observable observable, Object arg) {
        if (arg.equals(RefrigeratorClock.Events.CLOCK_TICKED_EVENT)) {
            processClockTick();
        }
        currentState.handle(arg);
    }

    /**
     * handle one of the several other events such as door close
     *
     * @param arg the event from the GUI
     */
    public void processEvent(Object arg) {
        if (arg.equals(RefrigeratorClock.Events.CLOCK_TICKED_EVENT)) {
            processClockTick();
        }

        currentState.handle(arg);
    }

    /**
     * Called from the states to change the current state
     *
     * @param nextState the next state
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
        return refrigeratorDisplay;
    }

    /**
     * Getters and setters for each temperature
     */
    public int getRoomTemp() {
        return roomTemp;
    }

    public void setRoomTemp(int roomTemp) {
        this.roomTemp = roomTemp;
    }

    public int getFridgeTemp() {
        return fridgeTemp;
    }

    public void setFridgeTemp(int fridgeTemp) {
        this.fridgeTemp = fridgeTemp;
    }

    public int getFreezerTemp() {
        return freezerTemp;
    }

    public int getDesiredFridgeTemp() {
        return desiredFridgeTemp;
    }

    public void setDesiredFridgeTemp(int desiredFridgeTemp) {
        this.desiredFridgeTemp = desiredFridgeTemp;
    }

    public int getDesiredFreezerTemp() {
        return desiredFreezerTemp;
    }

    public void setDesiredFreezerTemp(int desiredFreezerTemp) {
        this.desiredFreezerTemp = desiredFreezerTemp;
    }

    public int getFridgeRateLoss() {
        return fridgeRateLoss;
    }

    public void setFridgeRateLoss(int fridgeRateLoss) {
        this.fridgeRateLoss = fridgeRateLoss;
    }

    public int getFreezerRateLoss() {
        return freezerRateLoss;
    }

    public void setFreezerRateLoss(int freezerRateLoss) {
        this.freezerRateLoss = freezerRateLoss;
    }

    public String getFridgeCompressor() {
        if (fridgeCompressorOn) {
            return "on";
        } else {
            return "off";
        }
    }

    public String getFreezerCompressor() {
        if (freezerCompressorOn) {
            return "on";
        } else {
            return "off";
        }
    }

    /**
     * Handles the changing of temperatures with each clock tick notified
     * from the observable
     */
    private void processClockTick() {
        /**
         * Increase time ticked
         */
        time++;

        /**
         * If fridge compressor is on, decrease fridge temp
         * else, increase fridge temp
         */
        if (fridgeCompressorOn && (time % config.getProperty("FridgeCoolRate") == 0)) {
            fridgeTemp--;
        } else if (!fridgeCompressorOn && (time % fridgeRateLoss == 0) && fridgeTemp < roomTemp) {
            fridgeTemp++;
        }

        /**
         * If freezer compressor is on, decrease freezer temp
         * else, increase freezer temp
         */
        if (freezerCompressorOn && (time % config.getProperty("FreezerCoolRate") == 0)) {
            freezerTemp--;
        } else if (!fridgeCompressorOn && (time % freezerRateLoss == 0) && freezerTemp < roomTemp) {
            freezerTemp++;
        }

        /**
         * If fridge temp is above threshold, turn on compressor
         */
        if (fridgeTemp >= desiredFridgeTemp +
                config.getProperty("FridgeCompressorStartDiff")) {

            fridgeCompressorOn = true;
        }

        /**
         * If freezer temp is above threshold, turn on compressor
         */
        if (freezerTemp >= desiredFreezerTemp +
                config.getProperty("FreezerCompressorStartDiff")) {

            freezerCompressorOn = true;
        }

        /**
         * If fridge temp is below threshold, turn off compressor
         */
        if (fridgeTemp <= desiredFridgeTemp -
                config.getProperty("FridgeCompressorStartDiff")) {

            fridgeCompressorOn = false;
        }

        /**
         * If freezer temp is below threshold, turn off compressor
         */
        if (freezerTemp <= desiredFreezerTemp -
                config.getProperty("FreezerCompressorStartDiff")) {

            freezerCompressorOn = false;
        }

        /**
         * Set all temps to display current values and display compressor status
         */

        refrigeratorDisplay.setFridgeTempDisplay(fridgeTemp);
        refrigeratorDisplay.setFreezerTempDisplay(freezerTemp);
        refrigeratorDisplay.setFridgeCompressorDisplay();
        refrigeratorDisplay.setFreezerCompressorDisplay();
    }

}