package org.usfirst.frc.team233.robot.commands;

import org.usfirst.frc.team233.robot.Robot;
import org.usfirst.frc.team233.robot.RobotMap;
import org.usfirst.frc.team233.robot.subsystems.Lights.LightingType;
import org.usfirst.frc.team233.robot.subsystems.ShootingState;

import edu.wpi.first.wpilibj.command.Command;

/**
 * WARNING: Still in prototype phase!
 * 		    DO NOT use for shooting
 * 			yet!*/
public class Shoot extends Command {
	ShooterAction action;
	private int dPadValue = Integer.MAX_VALUE;
	
	public enum ShooterAction {
		STOP_FLYWHEEL,
		START_FLYWHEEL,
		SHOOT,
		CEASEFIRE,
		TEST_F_UP,
		TEST_F_DOWN,
		TEST_H_UP,
		TEST_H_DOWN,
		TEST_I_UP,
		TEST_I_DOWN,
		SKIP; // Used to make sure the subsystem actions don't run again
		
	}
	
	public Shoot(ShooterAction action) {
		this.action = action;
	}
	
	public Shoot(ShooterAction action, double flywheelSpeed){
		this.action = action;
		Robot.flywheel.overrideFlywheelSpeed(flywheelSpeed);
	}
	
	@Override
	protected void execute() {
		//edited for if joystick unplugged, will return null
		if (Robot.oi.getShooterJoystick() != null){
			System.out.println("Change speed");
			if(dPadValue != Robot.oi.getShooterJoystick().getPOV() && Robot.oi.getShooterJoystick().getPOV() == -1){
				Robot.flywheel.adjustFlywheelSpeed( dPadValue /*Robot.oi.getShooterJoystick().getPOV()*/);
			}
			dPadValue = Robot.oi.getShooterJoystick().getPOV();
		}	
		
		// Obtain what action to perform 
		System.out.println("ACTION = " + action.toString());
		switch (action) {
			case START_FLYWHEEL:
				System.out.println("inside START FLYWHEEL");
				if ((Robot.flywheel.getFlywheelState() == ShootingState.FLYWHEEL_STOPPED) ||
					(Robot.flywheel.getFlywheelState() == ShootingState.FLYWHEEL_HALF_SPEED) ||
					(Robot.flywheel.getFlywheelState() == ShootingState.IGNORE)) {
					
					System.out.println("Flywheel half speed");
					Robot.flywheel.flywheelHalfSpeed();
					Robot.lights.activateLights(LightingType.missing_dot);
					Robot.flywheel.setFlywheelState(ShootingState.FLYWHEEL_HALF_SPEED);
				}
				//action = ShooterAction.SKIP;
				break;
				
			case STOP_FLYWHEEL:
				if (Robot.flywheel.getFlywheelState() == ShootingState.FLYWHEEL_HALF_SPEED) {
					Robot.flywheel.stopFlywheel();
					Robot.flywheel.setFlywheelState(ShootingState.FLYWHEEL_STOPPED);
				}
				break;
				
			case SHOOT:
				System.out.println("inside SHOOT");
				Robot.flywheel.startFlywheel();
				if (Robot.flywheel.getFlywheelState() != ShootingState.FLYWHEEL_UP_TO_SPEED){
					System.out.println("flywheel state not FLYWHEEL UP TO SPEED");
					Robot.flywheel.setFlywheelState(ShootingState.FLYWHEEL_SPINNING_UP);
				}
				//action = ShooterAction.SKIP;
				break;
				
			case CEASEFIRE:
				Robot.indexer.stopIndexer();
				Robot.hopper.stopAgitate();
				Robot.flywheel.setFlywheelState(ShootingState.INDEXER_STOPPED);
				Robot.lights.activateLights(LightingType.set_pink_color);
				break;
				
			case TEST_F_DOWN:
				Robot.flywheel.stopFlywheel();
				Robot.flywheel.setFlywheelState(ShootingState.IGNORE);
				break;
				
			case TEST_F_UP:
				Robot.flywheel.startFlywheel();
				Robot.flywheel.setFlywheelState(ShootingState.IGNORE);
				break;
				
			case TEST_H_UP:
				Robot.hopper.agitate();
				Robot.flywheel.setFlywheelState(ShootingState.IGNORE);
				break;
				
			case TEST_H_DOWN:
				Robot.hopper.stopAgitate();
				Robot.flywheel.setFlywheelState(ShootingState.IGNORE);
				break;
				
			case TEST_I_UP:
				Robot.indexer.releaseBalls();
				Robot.flywheel.setFlywheelState(ShootingState.IGNORE);
				break;
			
			case TEST_I_DOWN:
				Robot.indexer.stopIndexer();
				Robot.flywheel.setFlywheelState(ShootingState.IGNORE);
				break;
				
	
			default:
				System.out.println("ShootingAction == SKIP");
				break;
		}
		
		// Depending on the state, run the concurrent command
		System.out.println("STATE = " + Robot.flywheel.getFlywheelState().toString());
		switch (Robot.flywheel.getFlywheelState()) {
			case FLYWHEEL_SPINNING_UP:
				if (Robot.flywheel.motorSpeedEqualsSetSpeed()) {
					Robot.flywheel.setFlywheelState(ShootingState.FLYWHEEL_UP_TO_SPEED);
				}
				break;
		
			case FLYWHEEL_UP_TO_SPEED:
				Robot.hopper.agitate();
				Robot.indexer.releaseBalls();
				Robot.lights.activateLights(LightingType.shooter);
				break;
			
			case INDEXER_STOPPED:
				if (Robot.oi.getShooterJoystick().getRawButton(RobotMap.rightTriggerButtonNumber)) {
					Robot.flywheel.flywheelHalfSpeed();
				}
				else {
					Robot.flywheel.stopFlywheel();
				}
				Robot.flywheel.setFlywheelState(ShootingState.FLYWHEEL_HALF_SPEED);
				break;
	
			default:
				break;
		}
		
	}
	
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		if ((ShooterAction.CEASEFIRE == action) ||
			(ShooterAction.STOP_FLYWHEEL == action) ||
			(Robot.flywheel.getFlywheelState() == ShootingState.IGNORE)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		System.out.println("Interrupted Action = " + action.toString());
		end();
	}

}
