package org.usfirst.frc.team233.robot.commands;

import org.usfirst.frc.team233.robot.Robot;
import org.usfirst.frc.team233.robot.subsystems.Lights.LightingType;

import edu.wpi.first.wpilibj.command.Command;

public class ClimbCommand extends Command {
	
	public enum ClimberAction {
 		CLIMB,
 		REVERSE,
 		STOP
 	}
	
	ClimberAction action;
	
	public ClimbCommand(ClimberAction action) {
		// TODO Auto-generated constructor stub
		requires(Robot.ropeClimber);
		this.action = action;
	}
	
	@Override
	protected void execute() {
		// TODO Auto-generated method stub
		super.execute();
		switch (action) {
			case CLIMB:
				Robot.ropeClimber.startClimbingRope();
				Robot.lights.activateLights(LightingType.off);
				break;
			
			case REVERSE:
				Robot.ropeClimber.reverseClimber();
				Robot.lights.activateLights(LightingType.off);
				break;
	
			default:
				Robot.ropeClimber.stopClimbingRope();
				//Robot.ropeClimber.holdPos();
				Robot.lights.activateLights(LightingType.rainbow);
			break;
		}
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return true;
	}
	
	

}
