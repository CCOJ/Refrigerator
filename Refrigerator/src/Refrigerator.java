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

    private States currentState;          //The current state of the refrigerator
    private static Refrigerator instance; //Instance of refrigerator
    private RefrigeratorDisplay display;  //Displays the refrigerator
    private int fridgeTemp;               //37 to 41 degree Fahrenheit
    private int freezerTemp;              //0 to -9 degree Fahrenheit
    private int outsideTemp;              //50 to 110 degree Fahrenheit
    //Add room temp, idle, and cooling
    //Also add clock

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
