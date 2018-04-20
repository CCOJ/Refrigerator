/**
 * @author Randy, Noah, Ricky
 *
 * This implements the essential features of the refrigerator.
 */
public abstract class RefrigeratorDisplay {
    
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
     * Keeps the refrigerator
     *
     * @param refrigerator; the Refrigerator object
     */
    public void setRefrigerator(Refrigerator refrigerator){
        
    };

    /**
     * Fridge door is now opened
     */
    public void fridgeDoorOpened(){
        
    };

    /**
     * Fridge door is now closed
     */
    public void fridgeDoorClosed(){
        
    };

    /**
     * Freezer door is now opened
     */
    public void freezerDoorOpened(){
        
    };

    /**
     * Freezer door is now closed
     */
    public void freezerDoorClosed(){
        
    };

    /**
     * Fridge light is now on; Only when fridge door is opened
     */
    public void fridgeLightOn(){
        
    };

    /**
     * Fridge light is now off; Only when fridge door is closed
     */
    public void fridgeLightOff(){
        
    };

    /**
     * Freezer light is now on; Only when freezer door is opened
     */
    public void freezerLightOn(){
        
    };

    /**
     * Freezer light is now off; Only when freezer door is closed
     */
    public void freezerLightOff(){
        
    };

    /**
     * Sets temperature for fridge; Only 37 to 41 degree Fahrenheit
     */
    public void setFridgeTempDisplay(){
        
    };

    /**
     * Sets temperature for freezer; Only 0 to -9 degree Fahrenheit
     */
    public void setFreezerTempDisplay(){
        
    };

    /**
     * Sets temperature for room; Only 50 to 110 degree Fahrenheit
     */
    public void setRoomTempDisplay(){
        
    };
}
