package org.usfirst.frc.team233.robot.autonomous;

import org.usfirst.frc.team233.robot.Robot;
import org.usfirst.frc.team233.robot.commands.GearCommand;
import org.usfirst.frc.team233.robot.commands.GearCommand.GearAction;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 * Autonomous gear routine starting @ farthest position from boiler
 * @author Meriel
 */

public class AutoGearRoutine1 extends CommandGroup {
	
	//make isBlueAlliance constructor param after finished testing
	//public boolean isBlueAlliance = true;
	public boolean shortcut = false;
	
	public AutoGearRoutine1(boolean isBlueAlliance){
		if(!isBlueAlliance){
			/* RED ALLIANCE AUTONOMOUS CODE */		
			addSequential(new WaitCommand(Robot.delayTime));
			addSequential(new PinkNavigate(-72.5, 0, 1));
			
			//turn 60 degrees to back up to tip of peg
			addSequential(new PinkNavigate(-72.5, 60, 0.8));
			addSequential(new PinkNavigate(-100, 60, 0.5));
			addParallel(new GearCommand(GearAction.EJECT_GEAR));
			addSequential(new WaitCommand(Robot.gearSlot.getDelay()));
			//addSequential(new WaitCommand(2.0));
			addParallel(new GearCommand(GearAction.RETRACT_EJECTOR));

			//go to the hopper
			addSequential(new PinkNavigate(-75, 60, 1));
			addSequential(new PinkNavigate(-75, 0, 0.8));
			addSequential(new PinkNavigate(-195, 0, 1));
			addSequential(new PinkNavigate(-195, 90, 0.8));
			
			// Go to hopper and hold position
			addSequential(new PinkNavigate(-143, 90, 1, true));
			
		} else {
			/* BLUE ALLIANCE AUTONOMOUS CODE */
			addSequential(new WaitCommand(Robot.delayTime));
			addSequential(new PinkNavigate(-72.5, 0, 1));
			
			//turn 60 degrees to back up to tip of peg
			addSequential(new PinkNavigate(-72.5, -60, 0.8));
			addSequential(new PinkNavigate(-100, -60, 0.5));
			addParallel(new GearCommand(GearAction.EJECT_GEAR));
			addSequential(new WaitCommand(Robot.gearSlot.getDelay()));
			//addSequential(new WaitCommand(2.0));
			addParallel(new GearCommand(GearAction.RETRACT_EJECTOR));

			//go to the hopper
			addSequential(new PinkNavigate(-75, -60, 1));
			addSequential(new PinkNavigate(-75, 0, 0.8));
			addSequential(new PinkNavigate(-195, 0, 1));
			addSequential(new PinkNavigate(-195, -90, 0.8));
			
			// Go to hopper and hold position
			addSequential(new PinkNavigate(-143, -90, 1, true));
			
		}
	}
}
