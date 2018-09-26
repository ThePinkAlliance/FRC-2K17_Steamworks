package org.usfirst.frc.team233.robot.commands;

import org.usfirst.frc.team233.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class TankDrive extends Command {

		public TankDrive() {
			// TODO Auto-generated constructor stub
			requires(Robot.drivetrain);
		}
	
		// Called repeatedly when this Command is scheduled to run
		@Override
		protected void execute() {
			Robot.drivetrain.drive(Robot.oi.getBaseJoystick());
		}

		// Make this return true when this Command no longer needs to run execute()
		@Override
		protected boolean isFinished() {
			return false; // Runs until interrupted
		}

		// Called once after isFinished returns true
		@Override
		protected void end() {
			Robot.drivetrain.drive(0, 0);
		}
	

}
