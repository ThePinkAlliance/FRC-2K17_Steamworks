package org.usfirst.frc.team233.robot.subsystems;

import org.usfirst.frc.team233.robot.Robot;
import org.usfirst.frc.team233.robot.RobotMap;
import org.usfirst.frc.team233.robot.commands.GearCommand;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class RopeClimber extends Subsystem {

	private SpeedController climberMotor = new Talon(RobotMap.ropeClimberMotorPort);
	private SpeedController climberMotor2 = new Talon(RobotMap.ropeClimber2MotorPort);
	private final boolean isInverted = false;
	
	public RopeClimber() {
		// TODO Auto-generated constructor stub
		if (Robot.isReal()) {
			climberMotor.setInverted(isInverted);
			//climberMotor2.setInverted(isInverted);
		}
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}

	/** Skeleton method for a simple rope climbing action. */
	public void startClimbingRope() {
		// climber motor must be undirectional --> abs value of given speed
		// otherwise ratchet component gets destroyed
		// no backspinning
		climberMotor.set(Math.abs(RobotMap.ropeClimberSpeed));
		climberMotor2.set(Math.abs(RobotMap.ropeClimberSpeed));
		Robot.gearSlot.closeGearSlot();
	}
	
	public void stopClimbingRope() {
		climberMotor.stopMotor();
		climberMotor2.stopMotor();
	}
	
 	public void reverseClimber() {
 		climberMotor.set(-0.5);
 		climberMotor2.set(-0.5);
 	}

 	public void holdPos(){
 		climberMotor.set(0.05);
 		climberMotor2.set(0.05);
 	}
	
	

}
