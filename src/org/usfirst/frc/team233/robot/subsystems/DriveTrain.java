package org.usfirst.frc.team233.robot.subsystems;

import org.usfirst.frc.team233.robot.Robot;
import org.usfirst.frc.team233.robot.RobotMap;
import org.usfirst.frc.team233.robot.commands.TankDrive;
import org.usfirst.frc.team233.robot.subsystems.Lights.LightingType;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Joystick.AxisType;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//import edu.wpi.first.wpilibj.livewindow.LiveWindow;


public class DriveTrain extends Subsystem {
	// Drive train motor definition
	public SpeedController frontLeftMotor = new Talon(RobotMap.leftFrontMotorPort);
	public SpeedController rearLeftMotor = new Talon(RobotMap.leftBackMotorPort);
	public SpeedController frontRightMotor = new Talon(RobotMap.rightFrontMotorPort);
	public SpeedController rearRightMotor = new Talon(RobotMap.rightBackMotorPort);
	
	// Link the motors to the robot
	private RobotDrive drive = new RobotDrive(frontLeftMotor, rearLeftMotor,
			frontRightMotor, rearRightMotor);
	// Define the direction for each motor
	boolean isInverted = true;

	
	// Define other drive train components
	private Compressor compressor = new Compressor(RobotMap.compressorPort);
	public AHRS gyro = new AHRS(SPI.Port.kMXP);
	private Solenoid shifterSolenoid = new Solenoid(RobotMap.shiftingSolenoidPort);

	/*
	 * Calculate the distance each pulse in the encoder equals to for
	 * simulation. Equation: (Wheel Diameter x Pi) / Number of pulses per
	 * encoder revolution
	 */
	//private final double wheelDiameter = 1.0;
	//private final int pulsePerRevolution = 40;
	//final double distancePerPulse = (Math.PI * wheelDiameter) / pulsePerRevolution;

	// Fixed value for distancePerPulse used in real robot
//	private final double distancePerPulseLeft = 0.8254447961;
//	private final double distancePerPulseRight = 0.8228095673;
	private final double distancePerPulse = 0.0617; //pretty good @ 0.066; //0.0661; //0.066505; //0.0675; //0.07740687;
	

	// Define all the encoders that are going to be used for the drive train.
	public Encoder leftEncoder = new Encoder(RobotMap.leftEncoderAPort, 
											 RobotMap.leftEncoderBPort, 
											 false,
											 EncodingType.k1X);
	public Encoder rightEncoder = new Encoder(RobotMap.rightEncoderAPort, 
											  RobotMap.rightEncoderBPort, 
											  false,
											  EncodingType.k1X);

	/** Drive train constructor. */
	public DriveTrain() {
		super();
		// System.out.println("Drivetrain Constructor");
		drive.setSafetyEnabled(true);
		setupMotors();
		setupEncoders();
		resetEncoders();
		setupComponents();
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		setDefaultCommand(new TankDrive());
	}

	/** Steup drive train motors. */
	public void setupMotors() {
		frontLeftMotor.setInverted(isInverted);
		rearLeftMotor.setInverted(isInverted);

		frontRightMotor.setInverted(isInverted);
		rearRightMotor.setInverted(isInverted);
	}

	/** Setup other components used by the drive train. */
	public void setupComponents() {
		compressor.setClosedLoopControl(true);
		gyro.reset();
	}

	/** Setup encoders before use. */
	public void setupEncoders() {
		// Setup left encoder
		if (Robot.isReal()) {
			leftEncoder.setDistancePerPulse(distancePerPulse);
		} else {
			leftEncoder.setDistancePerPulse(distancePerPulse);
		}
		leftEncoder.setPIDSourceType(PIDSourceType.kDisplacement);
		leftEncoder.setReverseDirection(false);
		leftEncoder.setSamplesToAverage(6);

		// Setup right encoder
		if (Robot.isReal()) {
			rightEncoder.setDistancePerPulse(distancePerPulse);
		} else {
			rightEncoder.setDistancePerPulse(distancePerPulse);
		}
		rightEncoder.setReverseDirection(true);
		rightEncoder.setPIDSourceType(PIDSourceType.kDisplacement);
		rightEncoder.setSamplesToAverage(6);
	}

	/** Reset all encoders. */
	public void resetEncoders() {
		leftEncoder.reset();
		rightEncoder.reset();
	}

	/**
	 * Tank style driving for the DriveTrain.
	 * 
	 * @param left
	 *            Speed in range [-1,1]
	 * @param right
	 *            Speed in range [-1,1]
	 */
	public void drive(double left, double right) {
		/*
		 * If for some reason the wires for the drive train
		 * motors get placed in the switched sides, then
		 * we need to update the ports on RobotMap 
		 * and set the drive train isInverted to the
		 * correct value. This method will take that 
		 * inversion into consideration to align the 
		 * joysticks to the correct side of the drive train. */
		
		if (isInverted) {
			drive.tankDrive(right, left);
		} else {
			drive.tankDrive(left, right);
		}

	}

	/**
	 * @param joy
	 *            The ps3 style joystick to use to drive tank style.
	 */
	public void drive(Joystick base) {
		// System.out.println("Drive1");
		// drive(-base.getY(), -base.getAxis(AxisType.kThrottle));
		drive(-base.getAxis(AxisType.kThrottle), -base.getY());
	}

	/**
	 * Obtain the distance from the encoder on the left of the drive train.
	 */
	public double getLeftDistance() {
		double dist = leftEncoder.getDistance();
		// SmartDashboard.putNumber("Left Distance", dist);
		return dist;
	}

	/**
	 * Obtain the distance from the encoder on the right of the drive train.
	 */
	public double getRightDistance() {
		double dist = rightEncoder.getDistance();
		// SmartDashboard.putNumber("Right Distance", dist);
		return dist;
	}

	/** 
	 * Get the the calculated pulse count for the left encoder.
	 * */
	public int getLeftEncoderCount() {
		SmartDashboard.putNumber("leftEncoder", leftEncoder.get());
		return leftEncoder.get();
	}

	/** 
	 * Get the the calculated pulse count for the right encoder.
	 * */
	public int getRightEncoderCount() {
		SmartDashboard.putNumber("rightEncoder", rightEncoder.get());
		return rightEncoder.get();
	}
	
	/**
	 * Get the current rate (speed) that the drive train is going at
	 * by averaging both encoder rates.
	 * */
	public double getDriveTrainRate() {
		double avgEncodersRate = (leftEncoder.getRate() + rightEncoder
				.getRate()) / 2.0;
		SmartDashboard.putNumber("Encoder Avg Rate", avgEncodersRate);
		return avgEncodersRate;
	}

	/**
	 * Average the distance of both encoders and return its value. Also put this
	 * value in the SmartDashboard.
	 */
	public double getDistanceTraveled() {
		double avgEncoders = (leftEncoder.getDistance() + rightEncoder
				.getDistance()) / 2.0;
		SmartDashboard.putNumber("Encoder Avg", avgEncoders);
		return avgEncoders;
	}

	/**
	 * This method returns the average raw count of pulses between
	 * the left and right encoder.
	 * */
	public double getCountsTraveled() {
		double avgCounts = (leftEncoder.getRaw() + rightEncoder.getRaw()) / 2.0;
		SmartDashboard.putNumber("Encoder Avg Counts", avgCounts);
		return avgCounts;
	}

	/**
	 * Shifts to the opposite of the current gear (toggles).
	 * Ex: Low->Hi, Hi->Low
	 * */
	public void shiftGears() {
		if (shifterSolenoid.get()) {
			shifterSolenoid.set(false);
			Robot.lights.activateLights(LightingType.set_pink_color);
		} else {
			shifterSolenoid.set(true);
			Robot.lights.activateLights(LightingType.police);
		}
	}
	
	/** 
	 * Shift to hi or low gear. Will be used for autonomous.
	 * @param gearShift 	if true, shift to hi gear,
	 *  					else shift to low gear
	 * */
	public void shiftGears(boolean gearShift) {
		shifterSolenoid.set(gearShift);
	}

	
	/**
	 *  Get the drive trains angle of rotation since the
	 * last reset.
	 * */
	public double getGyroRotation() {
		return gyro.getAngle();
	}

	/**
	 * Get the rotation rate for the drive train.
	 * */
	public double getGyroRate() {
		return gyro.getRate();
	}

	/** Reset the gyro. */
	public void resetGyro() {
		gyro.reset();
	}

	public AHRS getDriveTrainGyro() {
		return gyro;
	}

	public void setDriveTrainSafety(boolean value) {
		drive.setSafetyEnabled(value);
	}
	
	/**
	 * Use this method to reset encoders and any other resource for autonomous
	 * use.
	 */
	public void reset() {
		resetEncoders();
		gyro.reset();
	}

	/**
	 * This method should be called on any disable to reset and release any
	 * resource that is not going to be used anymore.
	 */
	public void disableDriveTrain() {
		resetEncoders();
		frontLeftMotor.disable();
		frontRightMotor.disable();
		rearLeftMotor.disable();
		rearRightMotor.disable();
	}
}
