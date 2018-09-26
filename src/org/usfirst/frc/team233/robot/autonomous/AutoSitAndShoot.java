package org.usfirst.frc.team233.robot.autonomous;

import org.usfirst.frc.team233.robot.Robot;
//import org.usfirst.frc.team233.robot.Robot;
import org.usfirst.frc.team233.robot.commands.Shoot;
import org.usfirst.frc.team233.robot.commands.Shoot.ShooterAction;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class AutoSitAndShoot extends CommandGroup {
//n.B. FACING FRONT OF ROBOT, SHOOTER IS ON RIGHT, shoots toward front
	
	
	double shootingSpeed = 0.75;
	double targetAngle;
	double shootingDelay = 10.0;
	
	public AutoSitAndShoot(boolean isBlueAlliance) {
//		if (isBlueAlliance) {
//			targetAngle = -45.0;
//		}
//		else {
//			targetAngle = 90.0;
//		}
		
		if(!isBlueAlliance){
			/*RED ALLIANCE SIDE -- SHOOTER CLOSER TO WALL*/
			addSequential(new WaitCommand(Robot.delayTime));
			//line up however we want & shoot immediately
			addParallel(new Shoot(ShooterAction.SHOOT, shootingSpeed));
			//addParallel(new PinkNavigate(0, 0, 0.7, true));
			addSequential(new WaitCommand(shootingDelay));
			addSequential(new Shoot(ShooterAction.CEASEFIRE));
			
			//drive over line
			addSequential(new PinkNavigate(-24, 0, 1.0, false));
			addSequential(new PinkNavigate(300, -90, 1.0, false));
			addSequential(new PinkNavigate(300, 0, 1.0, false));
			addSequential(new PinkNavigate(360, 0, 1.0, true));

		} else {
			/*BLUE ALLIANCE SIDE -- SHOOTER AWAY FROM WALL*/
			addSequential(new WaitCommand(Robot.delayTime));
			//line up however we want & shoot immediately
			addParallel(new Shoot(ShooterAction.SHOOT, shootingSpeed));
			//addParallel(new PinkNavigate(0, 0, 0.7, true));
			addSequential(new WaitCommand(shootingDelay));
			addSequential(new Shoot(ShooterAction.CEASEFIRE));
			
			//cross line
			addSequential(new PinkNavigate(-324, -45, 1.0, false));
			addSequential(new PinkNavigate(-324, 45, 1.0, false));
			addSequential(new PinkNavigate(-288, 45, 1.0, true));
			//addSequential(new PinkNavigate(-324, 45, 1.0));
			//addSequential(new PinkNavigate(-324, 45, 1.0));
			//addSequential(new PinkNavigate());
		}
	}

}
