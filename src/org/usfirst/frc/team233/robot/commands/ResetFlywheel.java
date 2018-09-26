package org.usfirst.frc.team233.robot.commands;

import org.usfirst.frc.team233.robot.Robot;
import org.usfirst.frc.team233.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

public class ResetFlywheel extends Command {

	@Override
	protected void execute(){
		Robot.flywheel.resetFlywheelSpeed();
	}
	
	@Override
	protected boolean isFinished() {
		if( Robot.flywheel.getFlywheelMotorSpeed() == RobotMap.flywheelMotorSpeed){
			return true;
		}
		return false;
	}

}
