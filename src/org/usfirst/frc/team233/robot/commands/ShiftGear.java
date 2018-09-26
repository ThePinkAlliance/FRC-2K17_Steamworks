package org.usfirst.frc.team233.robot.commands;

import org.usfirst.frc.team233.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ShiftGear extends Command {

	public enum ShiftAction {
		HI_GEAR,
		LOW_GEAR,
		TOGGLE;
	}
	
	ShiftAction action;
	
	public ShiftGear(ShiftAction action) {
		// TODO Auto-generated constructor stub
		this.action = action;
	}
	
	@Override
	protected void execute() {
		// TODO Auto-generated method stub
		switch (action) {
			case HI_GEAR:
				Robot.drivetrain.shiftGears(true);
				break;
				
			case LOW_GEAR:
				Robot.drivetrain.shiftGears(false);
				break;
				
			default:
				Robot.drivetrain.shiftGears();
				break;
		}
		
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return true;
	}

}
