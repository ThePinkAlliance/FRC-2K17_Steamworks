package org.usfirst.frc.team233.robot.autonomous;

import org.usfirst.frc.team233.robot.PinkPD;
import org.usfirst.frc.team233.robot.Range;
import org.usfirst.frc.team233.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PinkNavigate extends Command {
	static final double POSITION_THRESHOLD = 0.5; // Distance (Inches)
	static final double ANGLE_THRESHOLD = 3.0; // Degrees
	static final double ANGLE_KP = 0.033;
	static final double BASE_KP = 0.35;
	
	private boolean looping;
	private double targetPos;
	private double targetAngle;
	private double maxPower;

	
	/** Constructor - Define the distance and angle for the robot to */
	public PinkNavigate(double targetPos, double targetAngle, double maxPower) {
		this(targetPos, targetAngle, maxPower, false);
	}
	
	/** 
	 * Constructor - Define the distance and angle for the robot to 
	 * NOTE: Used in auto routines 
	 * */
	public PinkNavigate(double targetPos, double targetAngle, double maxPower, boolean looping) {
		this.targetPos = targetPos;
		this.targetAngle = targetAngle;
		this.maxPower = maxPower;
		this.looping = looping;
		requires(Robot.drivetrain);
		//System.out.println("PinkNav initialized");
	}

	/**
	 * Tank drive two wheels to target positions in inches.
	 * Returns true when both arrive at the target.
	 */
	public boolean driveToPos() {
		SmartDashboard.putNumber("Distance traveled", Robot.drivetrain.getDistanceTraveled());
		double angularVelocity = Robot.drivetrain.getGyroRate();
		SmartDashboard.putNumber("angularVelocity", angularVelocity);
		double linearVelocity = Robot.drivetrain.getDriveTrainRate();
		SmartDashboard.putNumber("linearVelocity", linearVelocity);
		double linearError = targetPos - Robot.drivetrain.getDistanceTraveled();
		SmartDashboard.putNumber("linearError", linearError);
		double angularError = targetAngle - Robot.drivetrain.getGyroRotation();
		SmartDashboard.putNumber("angularError", angularError);
		// 02/21/2017 TESTED MOTOR_KP=0.3 ON COMPETITION ROBOT WITH FULL BATTERY, WORKED PERFECTLY
		double motorCmd = PinkPD.getMotorCmd(BASE_KP, 0.01, linearError,
				linearVelocity);
		SmartDashboard.putNumber("MotorCMD", motorCmd);
		motorCmd = Range.clip(motorCmd, 0.8, -0.8);

		// Determine and add the angle offset
		// 02/21/2017 TESTED ANGLE_KP=0.07 ON COMPETITION ROBOT WITH FULL BATTERY, WORKED PERFECTLY
		double angleOffset = PinkPD.getMotorCmd(ANGLE_KP /*0.02*/, 0.0/*0.002*/, angularError,
				angularVelocity);
		SmartDashboard.putNumber("Angle Offset", angleOffset);
		double leftMotorCmd = motorCmd - angleOffset;
		double rightMotorCmd = motorCmd + angleOffset;
		leftMotorCmd = Range.clip(leftMotorCmd, maxPower, -maxPower);
		rightMotorCmd = Range.clip(rightMotorCmd, maxPower, -maxPower);
		SmartDashboard.putNumber("leftMotorCmd", leftMotorCmd);
		SmartDashboard.putNumber("rightMotorCmd", rightMotorCmd);

		// Scale the motor commands back to account for the MC windup problem
		// (if the motor can't keep up with the command, error builds up)
		// leftMotorCmd *= maxPower;
		// rightMotorCmd *= maxPower;

		Robot.drivetrain.drive(leftMotorCmd, rightMotorCmd);
		if ((Math.abs(linearError) < POSITION_THRESHOLD) && 
			(Math.abs(angularError) < ANGLE_THRESHOLD)) {
			return true;
		} else {
			return false;
		}
	}

	/** Clip the command value in between the defined max and min value. */
	// migrated over from Range class
	public static double clip(double cmd, double max, double min) {
		if (cmd > max) {
			return max;
		} else if (cmd < min) {
			return min;
		}
		return cmd;
	}
	
	@Override
	protected boolean isFinished() {
		return driveToPos() && !looping;
	}

	@Override
	protected void end() {
		Robot.drivetrain.drive(0, 0);
	}
}