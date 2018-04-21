import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;


/**
 * @author Randy, Noah, Ricky
 *
 * GUI that implements RefrigeratorDisplay and shows an interface of a refrigerator
 */
public class RefrigeratorGUI extends RefrigeratorDisplay implements ActionListener{

    private static GUI gui;
    
    
    private RefrigeratorState currentState;                 //The current state of the refrigerator
    private RefrigeratorDisplay display;         //Displays the refrigerator
    private int fridgeTemp, desiredFridgeTemp,   //Temperatures in Fahrenheit
            freezerTemp, desiredFreezerTemp, roomTemp;
    
    private RefrigeratorGUI() {
        
        currentState = new FridgeClosedFreezerClosedState();
        display = RefrigeratorDisplay.instance();
        fridgeTemp = 37;                 //Default value to be set by properties.cfg
        freezerTemp = -9;                //Default value to be set by properties.cfg
        roomTemp = 90;                   //Default value to be set by properties.cfg

        gui = new GUI();
	initialize();
    }
    private class GUI extends JFrame{
        /**
         * ActionEvent buttons
         */
        private JButton fridgeTempRequest = new JButton("Set fridge temp");
        private JButton freezerTempRequest = new JButton("Set freezer temp");
        private JButton roomTempRequest = new JButton("Set room temp");
        private JButton fridgeDoorCloser = new JButton("Close fridge door");
        private JButton fridgeDoorOpener = new JButton("Open fridge door");
        private JButton freezerDoorCloser = new JButton("Close freezer door");
        private JButton freezerDoorOpener = new JButton("Open freezer door");


        /**
         * Labels and textfields for desired temps of fridge, freezer, and room
         */
        private JLabel desiredFridgeTempLabel = new JLabel("Desired fridge temp");
        private JLabel desiredFreezerTempLabel = new JLabel("Desired freezer temp");
        private JLabel desiredRoomTempLabel = new JLabel("Desired room temp");
        private JTextField desiredFridgeTempInput = new JTextField(String.valueOf(fridgeTemp), 5);
        private JTextField desiredFreezerTempInput = new JTextField(String.valueOf(freezerTemp), 5);
        private JTextField desiredRoomTempInput = new JTextField(String.valueOf(roomTemp), 5);
        
        /**
         * Status labels
         */
        private JLabel fridgeDoorStatus = new JLabel("Fridge door closed");
        private JLabel freezerDoorStatus = new JLabel("Freezer door closed");
        private JLabel fridgeLightStatus = new JLabel("Fridge light off");
        private JLabel freezerLightStatus = new JLabel("Freezer light off");
        private JLabel fridgeTempStatus = new JLabel("Fridge temp <" 
                + desiredFridgeTempInput.getText() + ">");
        private JLabel freezerTempStatus = new JLabel("Freezer temp <" 
                + desiredFreezerTempInput.getText() + ">");
        private JLabel roomTempStatus = new JLabel("Room temp <" 
                + desiredRoomTempInput.getText() + ">");

        /**
         * Panels to sort the various components
         */
        private JPanel variablePane = new JPanel(new FlowLayout());
        private JPanel doorPane = new JPanel(new FlowLayout());
        private JPanel statusPane = new JPanel(new GridLayout(1,7));
        private JPanel mainPane = new JPanel(new GridLayout(3,1, 5, 30));
        /**
         * Sets up the layout of the Refrigerator GUI
         */
        private GUI() {
            super("Refrigerator");
            addWindowListener(new WindowAdapter() {
                public void WindowClosing(WindowEvent event) {
                    System.exit(0);
                }
            });
            getContentPane().setLayout(new FlowLayout());
            //Fridge Temp request
            variablePane.add(desiredFridgeTempLabel);
            variablePane.add(desiredFridgeTempInput);
            variablePane.add(fridgeTempRequest);
            //Freezer temp request
            variablePane.add(desiredFreezerTempLabel);
            variablePane.add(desiredFreezerTempInput);
            variablePane.add(freezerTempRequest);
            //Room temp request
            variablePane.add(desiredRoomTempLabel);
            variablePane.add(desiredRoomTempInput);
            variablePane.add(roomTempRequest);
            //Fridge door
            doorPane.add(fridgeDoorCloser);
            doorPane.add(fridgeDoorOpener);
            //Freezer door
            doorPane.add(freezerDoorCloser);
            doorPane.add(freezerDoorOpener);
            //Door status
            statusPane.add(fridgeDoorStatus);
            statusPane.add(freezerDoorStatus);
            //Light status
            statusPane.add(fridgeLightStatus);
            statusPane.add(freezerLightStatus);
            //Temp status
            statusPane.add(fridgeTempStatus);
            statusPane.add(freezerTempStatus);
            statusPane.add(roomTempStatus);
            //Whole frame
            mainPane.add(variablePane);
            mainPane.add(doorPane);
            mainPane.add(statusPane);
            getContentPane().add(mainPane);
            //Action listeners
            fridgeTempRequest.addActionListener(RefrigeratorGUI.this);
            freezerTempRequest.addActionListener(RefrigeratorGUI.this);
            roomTempRequest.addActionListener(RefrigeratorGUI.this);
            fridgeDoorCloser.addActionListener(RefrigeratorGUI.this);
            fridgeDoorOpener.addActionListener(RefrigeratorGUI.this);
            freezerDoorCloser.addActionListener(RefrigeratorGUI.this);
            freezerDoorOpener.addActionListener(RefrigeratorGUI.this);
            //Additional settings
            setDefaultCloseOperation(EXIT_ON_CLOSE); //Stop if closed
            //Pack and make visible
            pack();
            setVisible(true);
        }
    }

    /**
     * Proccesses buttons pressed and calls the Refrigerator object's methods
     */
    public void actionPerformed(ActionEvent event) {
        if (event.getSource().equals(gui.fridgeTempRequest)) {         //Desired fridge temp request
            String tempInput = gui.desiredFridgeTempInput.getText();
            int temp = Integer.parseInt(tempInput);
            processFridgeTempRequest(temp);
        } 
        else if (event.getSource().equals(gui.freezerTempRequest)) { //Desired freezer temp request
            String tempInput = gui.desiredFreezerTempInput.getText();
            int temp = Integer.parseInt(tempInput);
            processFreezerTempRequest(temp);
        } 
        else if (event.getSource().equals(gui.roomTempRequest)) {    //Desired room temp request
            String tempInput = gui.desiredRoomTempInput.getText();
            int temp = Integer.parseInt(tempInput);
            processRoomTempRequest(temp);
        } 
        else if (event.getSource().equals(gui.fridgeDoorCloser)) {   //Close fridge
            RefrigeratorContext.instance().processEvent(
                    RefrigeratorContext.Events.FRIDGE_DOOR_CLOSED_EVENT);
        } 
        else if (event.getSource().equals(gui.fridgeDoorOpener)) {   //Open fridge
            RefrigeratorContext.instance().processEvent(
                    RefrigeratorContext.Events.FRIDGE_DOOR_OPENED_EVENT);
        } 
        else if (event.getSource().equals(gui.freezerDoorCloser)) {  //Close freezer
            RefrigeratorContext.instance().processEvent(
                    RefrigeratorContext.Events.FREEZER_DOOR_CLOSED_EVENT);
        } 
        else if (event.getSource().equals(gui.freezerDoorOpener)) {  //Open freezer
            RefrigeratorContext.instance().processEvent(
                    RefrigeratorContext.Events.FREEZER_DOOR_OPENED_EVENT);
        }
    }
    /**
     * Fridge door is now opened
     */
    public void fridgeDoorOpened() {
        gui.fridgeDoorStatus.setText("Fridge door opened");
    }

    /**
     * Fridge door is now closed
     */
    public void fridgeDoorClosed() {
        gui.fridgeDoorStatus.setText("Fridge door closed");
    }

    /**
     * Freezer door is now opened
     */
    public void freezerDoorOpened() {
        gui.freezerDoorStatus.setText("Freezer door open");
    }

    /**
     * Freezer door is now closed
     */
    public void freezerDoorClosed() {
        gui.freezerDoorStatus.setText("Freezer door closed");
    }

    /**
     * Fridge light is now on; Only when fridge door is opened
     */
    public void fridgeLightOn() {
        gui.fridgeLightStatus.setText("Fridge light on");
    }

    /**
     * Fridge light is now off; Only when fridge door is closed
     */
    public void fridgeLightOff() {
        gui.fridgeLightStatus.setText("Fridge light off");
    }

    /**
     * Freezer light is now on; Only when freezer door is opened
     */
    public void freezerLightOn() {
        gui.freezerLightStatus.setText("Freezer light on");
    }

    /**
     * Freezer light is now off; Only when freezer door is closed
     */
    public void freezerLightOff() {
        gui.freezerLightStatus.setText("Freezer light off");
    }

    /**
     * Sets temperature for fridge; Only 37 to 41 degree Fahrenheit
     */
    public void setFridgeTempDisplay() {
        gui.fridgeTempStatus.setText("Fridge temp <" + getFridgeTemp() + ">");
    }

    /**
     * Sets temperature for freezer; Only 0 to -9 degree Fahrenheit
     */
    public void setFreezerTempDisplay() {
        gui.freezerTempStatus.setText("Freezer temp <" + getFreezerTemp() + ">");
    }

    /**
     * Sets temperature for room; Only 50 to 110 degree Fahrenheit
     */
    public void setRoomTempDisplay() {
        gui.roomTempStatus.setText("Room temp <" + getRoomTemp() + ">");
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


    //TODO: COOLING AND IDLING PROCESS

    /**
     * Ticks the clock that will pass time in order to change the temperature
     * of the refrigerator overtime.
     */
    public void clockTicked() {
        //Empty for now
    }

    public static void main(String[] s) {
        //RefrigeratorClock refrigeratorClock = new RefrigeratorClock();
        RefrigeratorGUI refrigerator = new RefrigeratorGUI();
    }
}
