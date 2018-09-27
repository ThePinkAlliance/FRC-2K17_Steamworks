package org.usfirst.frc.team233.robot.subsystems;

import org.usfirst.frc.team233.robot.RobotMap;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;


public class Hopper extends Subsystem {

	private SpeedController agitatorMotor = new Talon(RobotMap.agitatorMotorPort); 
	
	public Hopper() {
		super();
	}
	
	public void agitate() {
		agitatorMotor.set(RobotMap.agitatorMotorSpeed);
	}
	
	public void stopAgitate() {
		agitatorMotor.stopMotor();
	}
	
	public void reverseAgitate() {
		agitatorMotor.set(-RobotMap.agitatorMotorSpeed);
	}

	@Override
	protected void initDefaultCommand() {
	}

}
