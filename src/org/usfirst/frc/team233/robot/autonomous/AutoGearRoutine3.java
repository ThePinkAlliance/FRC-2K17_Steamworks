package org.usfirst.frc.team233.robot.autonomous;

import org.usfirst.frc.team233.robot.Robot;
import org.usfirst.frc.team233.robot.commands.Shoot;
import org.usfirst.frc.team233.robot.commands.Shoot.ShooterAction;
import org.usfirst.frc.team233.robot.commands.GearCommand;
import org.usfirst.frc.team233.robot.commands.GearCommand.GearAction;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 * Autonomous gear routine starting @ nearest position to boiler
 * Red Alliance side
 */
public class AutoGearRoutine3 extends CommandGroup {

	//make isBlueAlliance constructor param after finished testing
	//public boolean isBlueAlliance = true;

	public AutoGearRoutine3(boolean isBlueAlliance){
		if(!isBlueAlliance){
			/* RED ALLIANCE AUTONOMOUS CODE */
			addSequential(new WaitCommand(Robot.delayTime));
			addSequential(new PinkNavigate(-72.5, 0, 1));
			
			//turn 60 degrees to back up to tip of peg
			addSequential(new PinkNavigate(-72.5, -60, 0.8));
			addSequential(new PinkNavigate(-100, -60, 0.5));
			addParallel(new GearCommand(GearAction.EJECT_GEAR));
			addSequential(new WaitCommand(Robot.gearSlot.getDelay()));
			//addSequential(new WaitCommand(2.0));
			addParallel(new GearCommand(GearAction.RETRACT_EJECTOR));
			
			addParallel(new Shoot(ShooterAction.START_FLYWHEEL));
			addSequential(new PinkNavigate(18, -50, 1.0));
			
			addParallel(new PinkNavigate(18, -50, 1.0, true));
			addSequential(new Shoot(ShooterAction.SHOOT));
			
			
		} else {
			/* BLUE ALLIANCE AUTONOMOUS CODE */
			addSequential(new WaitCommand(Robot.delayTime));
			addSequential(new PinkNavigate(-72.5, 0, 1));
			
			//turn 60 degrees to back up to tip of peg
			addSequential(new PinkNavigate(-72.5, 60, 0.8));
			addSequential(new PinkNavigate(-100, 60, 0.5));
			addSequential(new GearCommand(GearAction.EJECT_GEAR));
			addSequential(new WaitCommand(Robot.gearSlot.getDelay()));
			//addSequential(new WaitCommand(2.0));
			addParallel(new GearCommand(GearAction.RETRACT_EJECTOR));

			addParallel(new Shoot(ShooterAction.START_FLYWHEEL));
			addSequential(new PinkNavigate(18, 50, 1.0));
			
			addParallel(new PinkNavigate(18, 50, 1.0, true));
			addSequential(new Shoot(ShooterAction.SHOOT));
			
		}
	}
}
