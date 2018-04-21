/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Randy, Noah, Ricky
 */
public class FridgeOpenFreezerClosedState  extends RefrigeratorState{
    
        private static FridgeOpenFreezerClosedState instance;
        
        
	static {
		instance = new FridgeOpenFreezerClosedState();
	}

	/**
	 * returns the instance
	 * 
	 * @return this object
	 */
	public static FridgeOpenFreezerClosedState instance() {
		return instance;
	}
        
	/**
	 * Handle events
	 * 
	 */
	@Override
	public void handle(Object event) {
		if (event.equals(RefrigeratorContext
                        .Events.FRIDGE_DOOR_CLOSED_EVENT)) {
                    
 			processFridgeClosedFreezerClosed();
		}
                else if (event.equals(RefrigeratorContext
                        .Events.FREEZER_DOOR_OPENED_EVENT)) {
                    
			processFridgeOpenedFreezerOpened();
		}
	}

	/**
	 * handle door open event
	 * 
	 */
	public void processFridgeOpenedFreezerOpened() {
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
		display.freezerDoorClosed();
                display.fridgeDoorOpened();
		display.freezerLightOff();
                display.fridgeLightOn();
	}
}
