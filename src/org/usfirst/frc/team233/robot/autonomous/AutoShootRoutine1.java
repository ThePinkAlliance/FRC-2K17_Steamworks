package org.usfirst.frc.team233.robot.autonomous;

import org.usfirst.frc.team233.robot.Robot;
import org.usfirst.frc.team233.robot.commands.CollectorCommand;
import org.usfirst.frc.team233.robot.commands.CollectorCommand.CollectorAction;
import org.usfirst.frc.team233.robot.commands.Shoot;
import org.usfirst.frc.team233.robot.commands.Shoot.ShooterAction;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 * Autonomous routine starting @ farthest position from boiler
 * Red Alliance Side
 * @author Meriel
 *
 */
public class AutoShootRoutine1 extends CommandGroup{
	
	// aim at 18 deg towards touchpad from right next to boiler
	
	public AutoShootRoutine1(){
		addSequential(new WaitCommand(Robot.delayTime));
		// CALCULATED FROM BLUE SIDE
		// drive to inside edge of touchpad
		//addSequential(new DriveStraight(195.16));
		addSequential(new PinkNavigate(78, 0.0, 1.0));
		// drive to hit touchpad
		// (distance depending on where we line up)
		// turn & back up to catch falling balls
		addSequential(new PinkNavigate(78,-90, 0.7));
		addSequential(new PinkNavigate(112, -90, 0.7));
		addSequential(new WaitCommand(2/*.4*/));
		// collect fallen balls
		//addParallel(new CollectorCommand(CollectorAction.COLLECT));
		addParallel(new Shoot(ShooterAction.START_FLYWHEEL));
		//addParallel(new CollectorCommand(CollectorAction.STOP));
		// drive to shooting position
		addSequential(new PinkNavigate(98, -90, 1));
		addSequential(new PinkNavigate(98, -180, 0.7));
		addSequential(new PinkNavigate(156, -180, 1));
		addParallel(new PinkNavigate(156, -180, 1, true));
		addSequential(new Shoot(ShooterAction.SHOOT));
	}
}
