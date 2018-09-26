package org.usfirst.frc.team233.robot.subsystems;

import org.usfirst.frc.team233.robot.RobotMap;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Indexer waits for flywheel to get up to speed before releasing 
 * a ball to be shot, then continuously releases balls
 */
public class Indexer extends Subsystem{
	public SpeedController indexer = new Talon(RobotMap.indexerMotorPort); 
	
	// Method called only when flywheel motor is up to speed 
	// (no corresponding joystick button for indexer)
	public void releaseBalls(){
		indexer.set(RobotMap.indexerMotorSpeed);
	}
	
	public void stopIndexer(){
		indexer.stopMotor();
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
}
