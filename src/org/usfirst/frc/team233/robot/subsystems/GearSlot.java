package org.usfirst.frc.team233.robot.subsystems;

import org.usfirst.frc.team233.robot.RobotMap;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * This class implement the code necessary
 * to control the active gear slot mechanism.
 * 
 *  NOTE: This is currently a skeleton implementation
 *  and might be missing other necessary methods and 
 *  information. 
 */
public class GearSlot extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	private Solenoid gearSolenoid = new Solenoid(RobotMap.gearSlotSolenoidPort);
	private Solenoid ejectorSolenoid = new Solenoid(RobotMap.ejectorSolenoidPort);
	
	// Global delay for gear eject action
	private double delay = 1.0; //

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
    }
    
    /**
     * This method implements the code logic needed
     * to open the gear slot doors
     */
    public void openGearSlot() {
    	// Code for opening the gear slot
    	gearSolenoid.set(true);
    }

    /**
     * This method implements the code logic needed
     * to close the gear slot doors
     */
    public void closeGearSlot() {
    	// Code for closing the gear slot
    	gearSolenoid.set(false);
    }
    
    
    /**
     * This method will toggle the gear slot
     * mechanism to either open or close.
     */
    public void toggleGearSlot() {
    	if (gearSolenoid.get()) {
    		closeGearSlot();
    	}
    	else {
    		openGearSlot();
    	}
    }
    
    /**
     * This method implements the code logic needed
     * to eject the gear once the slot doors
     * are open.
     */
    public void ejectGear() {
    	// Code to eject gear
    	ejectorSolenoid.set(true);
    }
    
    /**
     * Retract the gear ejector mechanism to it's 
     * original state.
     */
    public void retractEjecter() {
    	ejectorSolenoid.set(false);
    }
     
    /**
     * This is an auxiliary method that already implements
     * an possible behavior if the gear slot needs to 
     * open before ejecting the gear. 
     * 
     * @param delay		specifies the required delay before 
     * 					the gear is ejected
     */
//    public void fullActionEject(double delay) {
    public void fullActionEject() {
    	gearSolenoid.set(true);
    	Timer.delay(delay);
    	ejectorSolenoid.set(true);
    }
    
    /**
     * Reset the gear slot mechanism
     */
    public void resetGearSlot() {
    	gearSolenoid.set(false);
    	ejectorSolenoid.set(false);
    }
    
    public void setDelay(double delay){
    	if(delay > 0){
    		this.delay = delay;
    	}
    }
    
    public double getDelay(){
    	return delay;
    }
}

