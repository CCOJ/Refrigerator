/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * * @author Randy, Noah, Ricky
 */
public class FridgeClosedFreezerClosedState extends RefrigeratorState {
    private static FridgeClosedFreezerClosedState instance;


    static {
        instance = new FridgeClosedFreezerClosedState();
    }

    /**
     * returns the instance
     *
     * @return this object
     */
    public static FridgeClosedFreezerClosedState instance() {
        return instance;
    }

    /**
     * Handle events
     */
    @Override
    public void handle(Object event) {
        if (event.equals(RefrigeratorContext
                .Events.FRIDGE_DOOR_OPENED_EVENT)) {

            processFridgeOpenFreezerClosed();
        } else if (event.equals(RefrigeratorContext
                .Events.FREEZER_DOOR_OPENED_EVENT)) {

            processFridgeClosedFreezerOpened();
        }
    }

    /**
     * handle door open event
     */
    public void processFridgeOpenFreezerClosed() {
        context.changeCurrentState(FridgeOpenFreezerClosedState.instance());
    }

    /**
     * handle door open event
     */
    public void processFridgeClosedFreezerOpened() {
        context.changeCurrentState(FridgeClosedFreezerOpenState.instance());
    }

    /**
     * initialize the state
     */
    @Override
    public void run() {
        display.freezerDoorClosed();
        display.fridgeDoorClosed();
        display.freezerLightOff();
        display.fridgeLightOff();
        context.setFreezerRateLoss(context.config
                .getProperty("FreezerRateLossDoorClosed"));
        context.setFridgeRateLoss(context.config
                .getProperty("FridgeRateLossDoorClosed"));
    }
}
