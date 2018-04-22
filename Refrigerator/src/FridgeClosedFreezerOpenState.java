/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Randy, Noah, Ricky
 */
public class FridgeClosedFreezerOpenState  extends RefrigeratorState{
    
        private static FridgeClosedFreezerOpenState instance;
        
        
	static {
		instance = new FridgeClosedFreezerOpenState();
	}

	/**
	 * returns the instance
	 * 
	 * @return this object
	 */
	public static FridgeClosedFreezerOpenState instance() {
		return instance;
	}
        
	/**
	 * Handle events
	 * 
	 */
	@Override
	public void handle(Object event) {
		if (event.equals(RefrigeratorContext
                        .Events.FRIDGE_DOOR_OPENED_EVENT)) {
                    
 			processFridgeOpenFreezerOpen();
		}
                else if (event.equals(RefrigeratorContext
                        .Events.FREEZER_DOOR_CLOSED_EVENT)) {
                    
			processFridgeClosedFreezerClosed();
		}
	}

	/**
	 * handle door open event
	 * 
	 */
	public void processFridgeOpenFreezerOpen() {
		context.changeCurrentState(FridgeOpenFreezerOpenState.instance());
	}
        /**
	 * handle door open event
	 * 
	 */
	public void processFridgeClosedFreezerClosed() {
		context.changeCurrentState(FridgeClosedFreezerClosedState.instance());
	}
	/**
	 * initialize the state
	 * 
	 */
	@Override
	public void run() {
		display.freezerDoorOpened();
                display.fridgeDoorClosed();
		display.freezerLightOn();
                display.fridgeLightOff();
                context.setFreezerRateLoss(context.config
                        .getProperty("FreezerRateLossDoorOpen"));
                context.setFridgeRateLoss(context.config
                        .getProperty("FridgeRateLossDoorClosed"));
	}
}
