
import java.util.Observable;

/**
 * @author Randy, Noah, Ricky
 *
 * This implements the essential features of the refrigerator.
 */
public abstract class RefrigeratorDisplay extends Observable{
    
    protected static RefrigeratorContext context;
    protected static RefrigeratorDisplay instance;

    protected RefrigeratorDisplay() {
	instance = this;
	context = RefrigeratorContext.instance();
    }

    public static RefrigeratorDisplay instance() {
	return instance;
    }
    /**
    * Do the initializations to make the context an observer
    */
    public void initialize() {
	instance().addObserver(context);
	context.initialize();
    }    

    /**
     * Fridge door is now opened
     */
    public abstract void fridgeDoorOpened();

    /**
     * Fridge door is now closed
     */
    public abstract void fridgeDoorClosed();

    /**
     * Freezer door is now opened
     */
    public abstract void freezerDoorOpened();

    /**
     * Freezer door is now closed
     */
    public abstract void freezerDoorClosed();

    /**
     * Fridge light is now on; Only when fridge door is opened
     */
    public abstract void fridgeLightOn();

    /**
     * Fridge light is now off; Only when fridge door is closed
     */
    public abstract void fridgeLightOff();

    /**
     * Freezer light is now on; Only when freezer door is opened
     */
    public abstract void freezerLightOn();

    /**
     * Freezer light is now off; Only when freezer door is closed
     */
    public abstract void freezerLightOff();

    /**
     * Sets temperature for fridge; Only 37 to 41 degree Fahrenheit
     */
    public abstract void setFridgeTempDisplay(int temp);

    /**
     * Sets temperature for freezer; Only 0 to -9 degree Fahrenheit
     */
    public abstract void setFreezerTempDisplay(int temp);

    /**
     * Sets temperature for room; Only 50 to 110 degree Fahrenheit
     */
    public abstract void setRoomTempDisplay(int temp);
        /**
     * Sets temperature for fridge; Only 37 to 41 degree Fahrenheit
     */
    public abstract void setDesiredFridgeTempDisplay();

    /**
     * Sets temperature for freezer; Only 0 to -9 degree Fahrenheit
     */
    public abstract void setDesiredFreezerTempDisplay();

    /**
     * Sets temperature for room; Only 50 to 110 degree Fahrenheit
     */
    public abstract void setDesiredRoomTempDisplay();
}
