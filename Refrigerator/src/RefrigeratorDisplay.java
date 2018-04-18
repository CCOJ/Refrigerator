/**
 * This implements the essential features of the refrigerator.
 */
public interface RefrigeratorDisplay {

    /**
     * Keeps the refrigerator
     * @param refrigerator; the Refrigerator object
     */
    public void setRefrigerator(Refrigerator refrigerator);

    /**
     * Fridge door is now opened
     */
    public void fridgeDoorOpened();

    /**
     * Fridge door is now closed
     */
    public void fridgeDoorClosed();

    /**
     * Freezer door is now opened
     */
    public void freezerDoorOpened();

    /**
     * Freezer door is now closed
     */
    public void freezerDoorClosed();

    /**
     * Fridge light is now on; Only when fridge door is opened
     */
    public void fridgeLightOn();

    /**
     * Fridge light is now off; Only when fridge door is closed
     */
    public void fridgeLightOff();

    /**
     * Freezer light is now on; Only when freezer door is opened
     */
    public void freezerLightOn();

    /**
     * Freezer light is now off; Only when freezer door is closed
     */
    public void freezerLightOff();


    /**
     * TODO
     * Also need to add room temp, and box input to set temps for room, freezer, and fridge
     */
}
