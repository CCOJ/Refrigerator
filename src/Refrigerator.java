import java.sql.Ref;

/**
 * @author Randy, Noah, Ricky
 *
 * This represents a refrigerator. It has all states of a refrigerator that includes
 * both a freezer and fridge door. There are also methods such as closing and opening doors.
 * There is a clock tick that controls time to allow cooling, warming, or idling.
 *
 * Code is also based on the original source of Class Project 2 Iteration 1.
 */
public class Refrigerator {

    public enum States {
        FRIDGE_DOOR_OPENED_FREEZER_DOOR_OPENED_STATE, //Fridge open, freezer open; OO
        FRIDGE_DOOR_CLOSED_FREEZER_DOOR_OPENED_STATE, //Fridge closed, freezer open; CO
        FRIDGE_DOOR_OPENED_FREEZER_DOOR_CLOSED_STATE, //Fridge open, freezer closed; OC
        FRIDGE_DOOR_CLOSED_FREEZER_DOOR_CLOSED_STATE  //Fridge closed, freezer closed; CC
    };

    private States currentState;                 //The current state of the refrigerator
    private static Refrigerator instance;        //Instance of refrigerator
    private RefrigeratorDisplay display;         //Displays the refrigerator
    private int fridgeTemp, desiredFridgeTemp,   //Temperatures in Fahrenheit
            freezerTemp, desiredFreezerTemp, roomTemp;

    /**
     * Private constructor to support singleton pattern.
     * Sets all states to default with doors closed.
     * Sets up the GUI.
     */
    private Refrigerator() {
        currentState = States.FRIDGE_DOOR_CLOSED_FREEZER_DOOR_CLOSED_STATE;
        fridgeTemp = 37;                 //Default value to be set by properties.cfg
        freezerTemp = -9;                //Default value to be set by properties.cfg
        roomTemp = 90;                   //Default value to be set by properties.cfg
        display = new RefrigeratorGUI(); //Set up display
        display.setRefrigerator(this);   //Set the display to use this refrigerator
        display.fridgeDoorClosed();      //Close fridge door
        display.fridgeLightOff();        //Turn fridge light off
        display.freezerDoorClosed();     //Close freezer door
        display.freezerLightOff();       //Turn freezer light off
        display.setFridgeTempDisplay();  //Set up fridge temp display
        display.setFreezerTempDisplay(); //Set up freezer temp display
        display.setRoomTempDisplay();    //Set up room temp display
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
     * Processes desired fridge temp request.
     * Only 37 to 41 degree Fahrenheit.
     */
    public void processFridgeTempRequest(int temp) {
        if (temp < 37) { //Checks request and limits to the allowed fridge temp range
            temp = 37;
        } else if (temp > 41) {
            temp = 41;
        }

        setDesiredFridgeTemp(temp); //Sets desired fridge temp

        //Temporary below for testing
        setFridgeTemp(temp);
        display.setFridgeTempDisplay();
    }

    /**
     * Processes desired freezer temp request.
     * Only 0 to -9 degree Fahrenheit.
     */
    public void processFreezerTempRequest(int temp) {
        if (temp < -9) { //Checks request and limits to the allowed freezer temp range
            temp = -9;
        } else if (temp > 0) {
            temp = 0;
        }

        setDesiredFreezerTemp(temp); //Sets desired freezer temp

        //Temporary below for testing
        setFreezerTemp(temp);
        display.setFreezerTempDisplay();
    }

    /**
     * Proccess desired room temp request.
     * Only 50 to 110 degree Fahrenheit.
     */
    public void processRoomTempRequest(int temp) {
        if (temp < 50) { //Checks request and limits to the allowed room temp range
            temp = 50;
        } else if (temp > 110) {
            temp = 110;
        }

        setRoomTemp(temp); //Sets new room temp
        display.setRoomTempDisplay(); //Updates GUI
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
