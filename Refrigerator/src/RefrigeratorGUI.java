import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

/**
 * GUI that implements RefrigeratorDisplay and shows an interface of a refrigerator
 */
public class RefrigeratorGUI extends JFrame implements ActionListener, RefrigeratorDisplay {

    private Refrigerator refrigerator;

    private JButton fridgeTempRequest = new JButton("Set fridge temp");
    private JButton freezerTempRequest = new JButton("Set freezer temp");
    private JButton roomTempRequest = new JButton("Set room temp");
    private JButton fridgeDoorCloser = new JButton("Close fridge door");
    private JButton fridgeDoorOpener = new JButton("Open fridge door");
    private JButton freezerDoorCloser = new JButton("Close freezer door");
    private JButton freezerDoorOpener = new JButton("Open freezer door");

    private JLabel fridgeDoorStatus = new JLabel("Fridge door closed");
    private JLabel freezerDoorStatus = new JLabel("Freezer door closed");
    private JLabel fridgeLightStatus = new JLabel("Fridge light off");
    private JLabel freezerLightStatus = new JLabel("Freezer light off");
    private JLabel fridgeTempStatus = new JLabel("Fridge temp < >");
    private JLabel freezerTempStatus = new JLabel("Freezer temp < >");
    private JLabel roomTempStatus = new JLabel("Room temp < >");

    protected JTextField desiredFridgeTempInput = new JTextField("Desired fridge temp");
    protected JTextField desiredFreezerTempInput = new JTextField("Desired freezer temp");
    protected JTextField desiredRoomTempInput = new JTextField("Desired room temp");

    /**
     * Sets up the layout of the Refrigerator GUI
     */
    public RefrigeratorGUI() {
        super("Refrigerator");
        addWindowListener(new WindowAdapter() {
            public void WindowClosing(WindowEvent event) {
                System.exit(0);
            }
        });
        getContentPane().setLayout(new FlowLayout());
        //Fridge Temp
        getContentPane().add(desiredFridgeTempInput);
        getContentPane().add(fridgeTempRequest);
        //Freezer temp
        getContentPane().add(desiredFreezerTempInput);
        getContentPane().add(freezerTempRequest);
        //Room temp
        getContentPane().add(desiredRoomTempInput);
        getContentPane().add(roomTempRequest);
        //Fridge door
        getContentPane().add(fridgeDoorCloser);
        getContentPane().add(fridgeDoorOpener);
        //Freezer door
        getContentPane().add(freezerDoorCloser);
        getContentPane().add(freezerDoorOpener);
        //Door status
        getContentPane().add(fridgeDoorStatus);
        getContentPane().add(freezerDoorStatus);
        //Light status
        getContentPane().add(fridgeLightStatus);
        getContentPane().add(freezerLightStatus);
        //Temp status
        getContentPane().add(fridgeTempStatus);
        getContentPane().add(freezerTempStatus);
        getContentPane().add(roomTempStatus);
        //Action listeners
        fridgeTempRequest.addActionListener(this);
        freezerTempRequest.addActionListener(this);
        roomTempRequest.addActionListener(this);
        fridgeDoorCloser.addActionListener(this);
        fridgeDoorOpener.addActionListener(this);
        freezerDoorCloser.addActionListener(this);
        freezerDoorOpener.addActionListener(this);
        pack();
        setVisible(true);
    }

    /**
     * Proccesses buttons pressed and calls the Refrigerator object's methods
     */
    public void actionPerformed(ActionEvent event) {
        if (event.getSource().equals(fridgeTempRequest)) {         //Desired fridge temp request
            String tempInput = desiredFridgeTempInput.getText();
            int temp = Integer.parseInt(tempInput);
            refrigerator.processFridgeTempRequest(temp);
        } else if (event.getSource().equals(freezerTempRequest)) { //Desired freezer temp request
            String tempInput = desiredFreezerTempInput.getText();
            int temp = Integer.parseInt(tempInput);
            refrigerator.processFreezerTempRequest(temp);
        } else if (event.getSource().equals(roomTempRequest)) {    //Desired room temp request
            String tempInput = desiredRoomTempInput.getText();
            int temp = Integer.parseInt(tempInput);
            refrigerator.processRoomTempRequest(temp);
        } else if (event.getSource().equals(fridgeDoorCloser)) {   //Close fridge
            refrigerator.processCloseFridgeDoor();
        } else if (event.getSource().equals(fridgeDoorOpener)) {   //Open fridge
            refrigerator.processOpenFridgeDoor();
        } else if (event.getSource().equals(freezerDoorCloser)) {  //Close freezer
            refrigerator.processCloseFreezerDoor();
        } else if (event.getSource().equals(freezerDoorOpener)) {  //Open freezer
            refrigerator.processOpenFreezerDoor();
        }
    }

    /**
     * Keeps the refrigerator
     *
     * @param refrigerator; the Refrigerator object
     */
    public void setRefrigerator(Refrigerator refrigerator) {
        this.refrigerator = refrigerator;
    }

    /**
     * Fridge door is now opened
     */
    public void fridgeDoorOpened() {
        fridgeDoorStatus.setText("Fridge door opened");
    }

    /**
     * Fridge door is now closed
     */
    public void fridgeDoorClosed() {
        fridgeDoorStatus.setText("Fridge door closed");
    }

    /**
     * Freezer door is now opened
     */
    public void freezerDoorOpened() {
        freezerDoorStatus.setText("Freezer door open");
    }

    /**
     * Freezer door is now closed
     */
    public void freezerDoorClosed() {
        freezerDoorStatus.setText("Freezer door closed");
    }

    /**
     * Fridge light is now on; Only when fridge door is opened
     */
    public void fridgeLightOn() {
        fridgeLightStatus.setText("Fridge light on");
    }

    /**
     * Fridge light is now off; Only when fridge door is closed
     */
    public void fridgeLightOff() {
        fridgeLightStatus.setText("Fridge light off");
    }

    /**
     * Freezer light is now on; Only when freezer door is opened
     */
    public void freezerLightOn() {
        freezerLightStatus.setText("Freezer light on");
    }

    /**
     * Freezer light is now off; Only when freezer door is closed
     */
    public void freezerLightOff() {
        freezerLightStatus.setText("Freezer light off");
    }

    /**
     * Sets temperature for fridge; Only 37 to 41 degree Fahrenheit
     */
    public void setFridgeTempDisplay() {
        fridgeTempStatus.setText("Fridge temp <" + refrigerator.getFridgeTemp() + ">");
    }

    /**
     * Sets temperature for freezer; Only 0 to -9 degree Fahrenheit
     */
    public void setFreezerTempDisplay() {
        freezerTempStatus.setText("Freezer temp <" + refrigerator.getFreezerTemp() + ">");
    }

    /**
     * Sets temperature for room; Only 50 to 110 degree Fahrenheit
     */
    public void setRoomTempDisplay() {
        roomTempStatus.setText("Room temp <" + refrigerator.getRoomTemp() + ">");
    }
}
