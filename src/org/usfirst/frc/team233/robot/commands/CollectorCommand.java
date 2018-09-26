package org.usfirst.frc.team233.robot.commands;

import org.usfirst.frc.team233.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class CollectorCommand extends Command {
	
	public enum CollectorAction {
		COLLECT,
		EJECT,
		STOP
	}
	CollectorAction action;
	
	public CollectorCommand(CollectorAction action) {
		// TODO Auto-generated constructor stub
		requires(Robot.ballCollector);
		this.action = action;
	}
	
	@Override
	protected void execute() {
		// TODO Auto-generated method stub
		switch (action) {
			case COLLECT:
				Robot.ballCollector.collect();
				break;
				
			case EJECT:
				Robot.ballCollector.eject();
				break;
			
			default:
				Robot.ballCollector.stop();
				break;
		}
	}
	
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		// FOR TESTING!!!!
		return true;
	}
}
