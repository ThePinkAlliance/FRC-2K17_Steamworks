package org.usfirst.frc.team233.robot.subsystems;

import org.usfirst.frc.team233.robot.Robot;
import org.usfirst.frc.team233.robot.RobotMap;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class BallCollector extends Subsystem{
	
	// Collector Motor Definition
	private SpeedController collectorMotor = new Talon(RobotMap.collectorMotorPort);
	private final boolean isInverted = true;
	
	public BallCollector() {	
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
	
	/** Start collecting balls */
	public void collect() {
		if (Robot.isReal()) {
			// Set the correct orientation for the motors
			collectorMotor.setInverted(isInverted);
		}
		// Run the collector motor at defined speed
		collectorMotor.set(RobotMap.collectorMotorSpeed);
		 
	}
	
	/** Reverse collector to eject balls */
	public void eject() {
		if (Robot.isReal()) {
			// Reverse the orientation of the motor
			collectorMotor.setInverted(!isInverted);
		}
		// Run the collector motor at defined speed
		collectorMotor.set(RobotMap.collectorMotorSpeed);
	}
	
	/** Stop collecting balls*/
	public void stop() {
		// Stop the collector 
		collectorMotor.stopMotor();
	}
	
}

