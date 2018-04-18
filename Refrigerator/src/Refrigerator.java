import java.sql.Ref;

/**
 * @author , Noah, Ricky
 *
 * This represents a refrigerator. It has all states of a refrigerator that includes
 * both a freezer and fridge door. There are also methods such as closing and opening doors.
 * There is a clock tick that controls time to allow cooling, warming, or idling.
 *
 * Code is also based on the original source of Class Project 2 Iteration 1.
 */
public class Refrigerator {

    public enum States {
        FRIDGE_DOOR_OPENED_FREEZER_DOOR_OPENED_STATE, //Fridge open, freezer open
        FRIDGE_DOOR_CLOSED_FREEZER_DOOR_OPENED_STATE, //Fridge closed, freezer open
        FRIDGE_DOOR_OPENED_FREEZER_DOOR_CLOSED_STATE, //Fridge open, freezer closed
        FRIDGE_DOOR_CLOSED_FREEZER_DOOR_CLOSED_STATE  //Fridge closed, freezer closed
    };

    private States currentState;                 //The current state of the refrigerator
    private static Refrigerator instance;        //Instance of refrigerator
    private RefrigeratorDisplay display;         //Displays the refrigerator
    private int fridgeTemp, desiredFridgeTemp;   //37 to 41 degree Fahrenheit
    private int freezerTemp, desiredFreezerTemp; //0 to -9 degree Fahrenheit
    private int roomTemp;                        //50 to 110 degree Fahrenheit

    /**
     * Private constructor to support singleton pattern.
     * Sets all states to default with doors closed.
     * Sets up the GUI.
     */
    private Refrigerator() {
        currentState = States.FRIDGE_DOOR_CLOSED_FREEZER_DOOR_CLOSED_STATE;
        display = new RefrigeratorGUI();
        display.setRefrigerator(this);
        display.fridgeLightOff();
        display.freezerLightOff();
        display.fridgeDoorClosed();
        display.freezerDoorClosed();
        fridgeTemp = 37;
        freezerTemp = -9;
        roomTemp = 90;
    }

    /**
     * Supports the singleton pattern.
     *
     * @return instance of refrigerator
     */
    public static Refrigerator instance() {
        if (instance == null) {
            return instance = new Refrigerator();
        }
        return instance;
    }

    /**
     * Set fridge temp
     * @param temp; Fridge temp
     */
    public void setFridgeTemp(int temp) {
        fridgeTemp = temp;
    }

    /**
     * Set desired fridge temp
     * @param temp; Desired fridge temp
     */
    public void setDesiredFridgeTemp(int temp) {
        desiredFridgeTemp = temp;
    }

    /**
     * Set freezer temp
     * @param temp; Freezer temp
     */
    public void setFreezerTemp(int temp) {
        freezerTemp = temp;
    }

    /**
     * Set desired freezer temp
     * @param temp; Desired freezer temp
     */
    public void setDesiredFreezerTemp(int temp) {
        desiredFreezerTemp = temp;
    }

    /**
     * Set room temp
     * @param temp; Room temp
     */
    public void setRoomTemp(int temp) {
        roomTemp = temp;
    }

    /**
     * Gets fridge temp
     * @return fridgeTemp
     */
    public int getFridgeTemp() {
        return fridgeTemp;
    }

    /**
     * Gets desired fridge temp
     * @return desiredFridgeTemp
     */
    public int getDesiredFridgeTemp() {
        return desiredFridgeTemp;
    }

    /**
     * Gets freezer temp
     * @return freezerTemp
     */
    public int getFreezerTemp() {
        return freezerTemp;
    }

    /**
     * Gets desired freezer temp
     * @return desiredFreezerTemp
     */
    public int getDesiredFreezerTemp() {
        return desiredFreezerTemp;
    }

    /**
     * Gets room temp
     * @return roomTemp
     */
    public int getRoomTemp() {
        return roomTemp;
    }

    /**
     * Processes desired fridge temp request
     */
    public void processFridgeTempRequest(int temp) {
        //Need to add check for correct temp input
        setDesiredFridgeTemp(temp);
        //Temporary below
        setFridgeTemp(temp);
        display.setFridgeTempDisplay();
    }

    /**
     * Processes desired freezer temp request
     */
    public void processFreezerTempRequest(int temp) {
        //Need to add check for correct temp input
        setDesiredFreezerTemp(temp);
        //Temporary below
        setFreezerTemp(temp);
        display.setFreezerTempDisplay();
    }

    /**
     * Proccess desired room temp request
     */
    public void processRoomTempRequest(int temp) {
        //Need to add check for correct temp input
        setRoomTemp(temp);
        //Temporary below
        display.setRoomTempDisplay();
    }

    /**
     * Processes fridge door close event.
     * Turns off fridge light.
     * Only happens when fridge door is opened.
     */
    public void processCloseFridgeDoor() {
        //Determines if fridge door is opened to perform process
        if (currentState == States.FRIDGE_DOOR_OPENED_FREEZER_DOOR_CLOSED_STATE
                || currentState == States.FRIDGE_DOOR_OPENED_FREEZER_DOOR_OPENED_STATE) {
            //Set new state
            if (currentState == States.FRIDGE_DOOR_OPENED_FREEZER_DOOR_CLOSED_STATE) {        //If freezer door is closed
                currentState = States.FRIDGE_DOOR_CLOSED_FREEZER_DOOR_CLOSED_STATE;
            } else if (currentState == States.FRIDGE_DOOR_OPENED_FREEZER_DOOR_OPENED_STATE) { //If freezer door is opened
                currentState = States.FRIDGE_DOOR_CLOSED_FREEZER_DOOR_OPENED_STATE;
            }
            //Set statuses
            display.fridgeDoorClosed();
            display.fridgeLightOff();
        }
    }

    /**
     * Processes fridge door open event.
     * Turns on fridge light.
     * Only happens when fridge door is closed.
     */
    public void processOpenFridgeDoor() {
        //Determines if fridge door is closed to perform process
        if (currentState == States.FRIDGE_DOOR_CLOSED_FREEZER_DOOR_CLOSED_STATE
                || currentState == States.FRIDGE_DOOR_CLOSED_FREEZER_DOOR_OPENED_STATE) {
            //Set new state
            if (currentState == States.FRIDGE_DOOR_CLOSED_FREEZER_DOOR_CLOSED_STATE) {        //If freezer door is closed
                currentState = States.FRIDGE_DOOR_OPENED_FREEZER_DOOR_CLOSED_STATE;
            } else if (currentState == States.FRIDGE_DOOR_CLOSED_FREEZER_DOOR_OPENED_STATE) { //If freezer door is opened
                currentState = States.FRIDGE_DOOR_OPENED_FREEZER_DOOR_OPENED_STATE;
            }
            //Set statuses
            display.fridgeDoorOpened();
            display.fridgeLightOn();
        }
    }

    /**
     * Processes freezer door close event.
     * Turns off freezer light.
     * Only happens when freezer door is opened.
     */
    public void processCloseFreezerDoor() {
        //Determines if freezer door is opened to perform process
        if (currentState == States.FRIDGE_DOOR_CLOSED_FREEZER_DOOR_OPENED_STATE
                || currentState == States.FRIDGE_DOOR_OPENED_FREEZER_DOOR_OPENED_STATE) {
            //Set new state
            if (currentState == States.FRIDGE_DOOR_CLOSED_FREEZER_DOOR_OPENED_STATE) {        //If fridge door is closed
                currentState = States.FRIDGE_DOOR_CLOSED_FREEZER_DOOR_CLOSED_STATE;
            } else if (currentState == States.FRIDGE_DOOR_OPENED_FREEZER_DOOR_OPENED_STATE) { //If fridge door is opened
                currentState = States.FRIDGE_DOOR_OPENED_FREEZER_DOOR_CLOSED_STATE;
            }
            //Set statuses
            display.freezerDoorClosed();
            display.freezerLightOff();
        }
    }

    /**
     * Processes freezer door open event.
     * Turns on freezer light.
     * Only happens when freezer door is closed.
     */
    public void processOpenFreezerDoor() {
        //Determines if freezer door is closed to perform process
        if (currentState == States.FRIDGE_DOOR_CLOSED_FREEZER_DOOR_CLOSED_STATE
                || currentState == States.FRIDGE_DOOR_OPENED_FREEZER_DOOR_CLOSED_STATE) {
            //Set new state
            if (currentState == States.FRIDGE_DOOR_CLOSED_FREEZER_DOOR_CLOSED_STATE) {        //If fridge door is closed
                currentState = States.FRIDGE_DOOR_CLOSED_FREEZER_DOOR_OPENED_STATE;
            } else if (currentState == States.FRIDGE_DOOR_OPENED_FREEZER_DOOR_CLOSED_STATE) { //If fridge door is opened
                currentState = States.FRIDGE_DOOR_OPENED_FREEZER_DOOR_OPENED_STATE;
            }
            //Set statuses
            display.freezerDoorOpened();
            display.freezerLightOn();
        }
    }

    //TODO: COOLING AND IDLING PROCESS

    /**
     * Ticks the clock that will pass time in order to change the temperature
     * of the refrigerator overtime.
     */
    public void clockTicked() {
        //Empty for now
    }

    public static void main(String[] s) {
        RefrigeratorClock refrigeratorClock = new RefrigeratorClock();
    }
}
