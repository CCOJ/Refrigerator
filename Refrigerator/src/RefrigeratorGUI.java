import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * GUI that implements RefrigeratorDisplay and shows an interface of a refrigerator
 */
public class RefrigeratorGUI extends JFrame implements ActionListener, RefrigeratorDisplay {

    private Refrigerator refrigerator;
    private JButton fridgeDoorCloser = new JButton("Close fridge door");
    private JButton fridgeDoorOpener = new JButton("Open fridge door");
    private JButton freezerDoorCloser = new JButton("Close freezer door");
    private JButton freezerDoorOpener = new JButton("Open freezer door");
    private JLabel fridgeDoorStatus = new JLabel("Fridge door closed");
    private JLabel freezerDoorStatus = new JLabel("Freezer door closed");
    private JLabel fridgeLightStatus = new JLabel("Fridge light off");
    private JLabel freezerLightStatus = new JLabel("Freezer light off");
    //Also need to add room temp, and box input to set temps for room, freezer, and fridge
    //Needs temps too

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
        getContentPane().add(fridgeDoorCloser);
        getContentPane().add(fridgeDoorOpener);
        getContentPane().add(freezerDoorCloser);
        getContentPane().add(freezerDoorOpener);
        getContentPane().add(fridgeDoorStatus);
        getContentPane().add(freezerDoorStatus);
        getContentPane().add(fridgeLightStatus);
        getContentPane().add(freezerLightStatus);
        //Also need to add room temp, and box input to set temps for room, freezer, and fridge
        fridgeDoorCloser.addActionListener(this);
        fridgeDoorOpener.addActionListener(this);
        freezerDoorCloser.addActionListener(this);
        freezerDoorOpener.addActionListener(this);
        pack();
        setVisible(true);
    }

    /**
     * Proccesses buttons pressed and calls the Refrigerator object's methods
     *
     */
    public void actionPerformed(ActionEvent event) {
        if (event.getSource().equals(fridgeDoorCloser)) {         //Close fridge
            refrigerator.processCloseFridgeDoor();
        } else if (event.getSource().equals(fridgeDoorOpener)) {  //Open fridge
            refrigerator.processOpenFridgeDoor();
        } else if (event.getSource().equals(freezerDoorCloser)) { //Close freezer
            refrigerator.processCloseFreezerDoor();
        } else if (event.getSource().equals(freezerDoorOpener)) { //Open freezer
            refrigerator.processOpenFreezerDoor();
        }
    }

    /**
     * Keeps the refrigerator
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
}
