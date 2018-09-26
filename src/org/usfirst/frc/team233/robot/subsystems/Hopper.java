package org.usfirst.frc.team233.robot.subsystems;

import org.usfirst.frc.team233.robot.RobotMap;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Hopper extends Subsystem {

	private SpeedController agitatorMotor = new Talon(RobotMap.agitatorMotorPort); 
	
	public Hopper() {
		super();
	}
	
	public void agitate(){
		//Robot.oi.getShooterJoystick().
		//shooter.getButton(button)
		//comp.start();
		agitatorMotor.set(RobotMap.agitatorMotorSpeed);
	}
	
	public void stopAgitate() {
		//comp.stop();
		agitatorMotor.stopMotor();
	}
	
	public void reverseAgitate() {
		agitatorMotor.set(-RobotMap.agitatorMotorSpeed);
	}
	
	public void blow(){
		//blowerMotor.set(RobotMap.blowerMotorSpeed);
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}

}
