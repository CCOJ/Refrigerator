import java.util.Observable;
import java.util.Observer;

/**
 * The context is an observer for the clock and stores the context info for
 * states
 *
 * @authors Randy, Noah
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
     * Gets room temp
     * @return roomTemp
     */
    public int getRoomTemp() {
        return roomTemp;
    }

    /**
     * Sets room temp
     * @param roomTemp
     */
    public void setRoomTemp(int roomTemp) {
        this.roomTemp = roomTemp;
    }

    /**
     * Gets fridge temp
     * @return fridgeTemp
     */
    public int getFridgeTemp() {
        return fridgeTemp;
    }

    /**
     * Sets fridge temp
     * @param fridgeTemp
     */
    public void setFridgeTemp(int fridgeTemp) {
        this.fridgeTemp = fridgeTemp;
    }

    /**
     * Gets freezer temp
     * @return freezer temp
     */
    public int getFreezerTemp() {
        return freezerTemp;
    }

    /**
     * Sets freezer temp
     * @param freezerTemp
     */
    public void setFreezerTemp(int freezerTemp) {
        this.freezerTemp = freezerTemp;
    }

    /**
     * Gets desired fridge temp
     * @return desiredFridgeTemp
     */
    public int getDesiredFridgeTemp() {
        return desiredFridgeTemp;
    }

    /**
     * Sets desired fridge temp
     * @param desiredFridgeTemp
     */
    public void setDesiredFridgeTemp(int desiredFridgeTemp) {
        this.desiredFridgeTemp = desiredFridgeTemp;
    }

    /**
     * Gets desired freezer temp
     * @return desiredFreezerTemp
     */
    public int getDesiredFreezerTemp() {
        return desiredFreezerTemp;
    }

    /**
     * Sets desired freezer temp
     * @param desiredFreezerTemp
     */
    public void setDesiredFreezerTemp(int desiredFreezerTemp) {
        this.desiredFreezerTemp = desiredFreezerTemp;
    }

    /**
     * Gets fridge rate loss
     * @return
     */
    public int getFridgeRateLoss() {
        return fridgeRateLoss;
    }

    /**
     * Sets fridge rate loss
     * @param fridgeRateLoss
     */
    public void setFridgeRateLoss(int fridgeRateLoss) {
        this.fridgeRateLoss = fridgeRateLoss;
    }

    /**
     * Gets freezer rate loss
     * @return
     */
    public int getFreezerRateLoss() {
        return freezerRateLoss;
    }

    /**
     * Sets freezer rate loss
     * @param freezerRateLoss
     */
    public void setFreezerRateLoss(int freezerRateLoss) {
        this.freezerRateLoss = freezerRateLoss;
    }

    /**
     * Gets fridge compressor status
     * @return "on" when true, "off" when false
     */
    public String getFridgeCompressor() {
        if (fridgeCompressorOn) {
            return "on";
        } else {
            return "off";
        }
    }

    /**
     * Gets freezer compressor status
     * @return "on" when true, "off" when false
     */
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
         */
        if (fridgeCompressorOn
                
                && (time % config.getProperty("FridgeCoolRate") == 0)) {
            fridgeTemp--;
        }

        /**
         * Increases fridge temp every loss interval
         */
        else if (fridgeCompressorOn == false 
                && time % fridgeRateLoss == 0 
                && fridgeTemp < roomTemp) {
            fridgeTemp++;
        }

        /**
         * If freezer compressor is on, decrease freezer temp
         */
        if (freezerCompressorOn 
                && freezerRateLoss == config.getProperty("FreezerRateLossDoorClosed")
                && (time % config.getProperty("FreezerCoolRate") == 0)) {
            freezerTemp--;
        }

        /**
         * Increases freezer temp every loss interval
         */
        else if (freezerCompressorOn == false
                && time % freezerRateLoss == 0 && freezerTemp < roomTemp) {
            freezerTemp++;
        }

        /**
         * If fridge temp is above threshold, turn on compressor. The compressor
         * can only be on when the door is closed. It will detect if the door is 
         * open if the rateLoss is higher than usual. The compressor is only 
         * shut off when the door is opened or the lower temp threshold has been
         * reached
         */
        if( fridgeRateLoss != config.getProperty("FridgeRateLossDoorClosed")){
            fridgeCompressorOn = false;
        }
        else if (fridgeTemp >= desiredFridgeTemp +
                config.getProperty("FridgeCompressorStartDiff")) {
            fridgeCompressorOn = true;
        }
        else if(fridgeTemp <= desiredFridgeTemp -
                config.getProperty("FridgeCompressorStartDiff")){
            fridgeCompressorOn = false;
        }

        /**
         * If freezer temp is above threshold, turn on compressor. The compressor
         * can only be on when the door is closed. It will detect if the door is 
         * open if the rateLoss is higher than usual. The compressor is only 
         * shut off when the door is opened or the lower temp threshold has been
         * reached
         */
        if( freezerRateLoss != config.getProperty("FreezerRateLossDoorClosed")){
            freezerCompressorOn = false;
        }
        else if (freezerTemp >= desiredFreezerTemp +
                config.getProperty("FreezerCompressorStartDiff")) {
            
            freezerCompressorOn = true;
        }
        else if(freezerTemp <= desiredFreezerTemp -
                config.getProperty("FreezerCompressorStartDiff")){
            
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