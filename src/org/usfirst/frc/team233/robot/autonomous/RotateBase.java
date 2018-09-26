package org.usfirst.frc.team233.robot.autonomous;

import org.usfirst.frc.team233.robot.Robot;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;

public class RotateBase extends Command {
	private PIDController pid;
	private final double tolerance = 0.5;
	private final double targetAngle;

	public RotateBase(double angle) {
		requires(Robot.drivetrain);
		targetAngle = angle;
		//Robot.drivetrain.reset();
		// A proportional controller (Kp) will reduce the
		// rise time and will reduce, but never eliminate, the steady-state
		// error.
		//
		// Steady-state error is defined as the difference between the input
		// (command) and the output of a system in the limit as time goes to
		// infinity (i.e. when the response has reached steady state)
		//
		// An integral control (Ki) will eliminate the
		// steady-state error, but it may make the transient response worse.
		//
		// A derivative control (Kd) will increase the
		// stability of the system, reducing the overshoot, and improving the
		// transient response but little effect on rise time
		pid = new PIDController(3, 0, 0.1, new PIDSource() {
			PIDSourceType m_sourceType = PIDSourceType.kDisplacement;

			@Override
			public double pidGet() {
				return Robot.drivetrain.getGyroRotation();
			}

			@Override
			public void setPIDSourceType(PIDSourceType pidSource) {
				m_sourceType = pidSource;
			}

			@Override
			public PIDSourceType getPIDSourceType() {
				return m_sourceType;
			}
		}, new PIDOutput() {
			@Override
			public void pidWrite(double d) {
				if (d > 1.0) {
					d = 1.0;
				} else if (d < -1.0) {
					d = -1.0;
				}
				//Robot.drivetrain.drive(d, -d);
//				NEED TO TEST:
//				// CW/right turn is positive
				if (targetAngle < 0) { // turning left
					// drive(left, right)
					Robot.drivetrain.drive(d, -d);
				} else { // turning left
					Robot.drivetrain.drive(-d, d);
				}
			}
		});
		pid.setAbsoluteTolerance(tolerance);
		pid.setSetpoint(angle);
		pid.setOutputRange(0.5, 0.7);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		// Get everything in a safe starting state.
		//Robot.drivetrain.reset();
		pid.reset();
		pid.enable();
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return pid.onTarget();
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		// Stop PID and the wheels
		pid.disable();
		Robot.drivetrain.drive(0, 0);
	}
}
