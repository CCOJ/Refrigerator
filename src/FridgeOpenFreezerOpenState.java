/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * * @author Randy, Noah, Ricky
 */
public class FridgeOpenFreezerOpenState  extends RefrigeratorState{
    
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
	 * Handle cook request and door open events
	 * 
	 */
	@Override
	public void handle(Object event) {
		if (event.equals(RefrigeratorContext
                        .Events.FRIDGE_DOOR_OPEN_FREEZER_DOOR_CLOSED_EVENT)) {
                    
			processFridgeOpen();
		}
                else if (event.equals(RefrigeratorContext
                        .Events.FRIDGE_DOOR_CLOSED_FREEZER_DOOR_OPEN_EVENT)) {
                    
			processFreezerOpen();
		}
	}

	/**
	 * handle door open event
	 * 
	 */
	public void processFridgeOpen() {
		context.changeCurrentState(FridgeOpenFreezerClosedState.instance());
	}
	/**
	 * handle door open event
	 * 
	 */
	public void processFreezerOpen() {
		context.changeCurrentState(FridgeClosedFreezerOpenState.instance());
	}
	/**
	 * initialize the state
	 * 
	 */
	@Override
	public void run() {
		display.freezerDoorClosed();
                display.fridgeDoorClosed();
		display.freezerLightOff();
                display.fridgeLightOff();
	}
}
