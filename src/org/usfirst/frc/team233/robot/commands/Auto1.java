package org.usfirst.frc.team233.robot.commands;

import org.usfirst.frc.team233.robot.Robot;
import org.usfirst.frc.team233.robot.RobotMap;
import org.usfirst.frc.team233.robot.autonomous.PinkNavigate;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.SPI;

import edu.wpi.first.wpilibj.command.Command;

public class Auto1 extends Command {
	SPI gyro;
	//double currentAngle = gyro.getAccumulatorCount();
    double leftWheelVel;
    double rightWheelVel;
    double avgWheelVel;
    double angularVel;

		public Auto1() {
			// TODO Auto-generated constructor stub
			requires(Robot.drivetrain);
			}
		// Called repeatedly when this Command is scheduled to run
		@Override
		protected void execute() {
			//System.out.println("TankDrive Execute!!!");
			double targetAngle = 0;
			double targetPos = 100000;
			//PinkNavigate destination1 = new PinkNavigate(targetPos, targetAngle, currentAngle, avgWheelVel, angularVel, 1.0);
			System.out.println("Encoder Left = " + Robot.drivetrain.getLeftDistance());
			System.out.println("Encoder Right = " + Robot.drivetrain.getRightDistance());
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
