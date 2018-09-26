package org.usfirst.frc.team233.robot.subsystems;

import org.usfirst.frc.team233.robot.Range;
import org.usfirst.frc.team233.robot.RobotMap;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Flywheel extends Subsystem {
	public SpeedController flywheelMotor = new Talon(RobotMap.flywheelMotorPort);
	
	private ShootingState state;
	
	// Defines the default value for the flywheel speed
	public double flywheelSpeed = RobotMap.flywheelMotorSpeed;
	// Defines the amount to increase or decrease the flywheel speed
	private final double speedAdjustment = 0.02;

	private double tolerance = 0.1;
	private final double flywheelKp = 0.4;
	private Encoder encoder = new Encoder(RobotMap.flywheelEncoderPortA, RobotMap.flywheelEncoderPortB, false, EncodingType.k1X);
	//private Encoder encoder = new Encoder(RobotMap.flywheelEncoderPortA, RobotMap.flywheelEncoderPortB);
	private final double flywheelDistancePerPulse = 0.123;
	
	/* Calculate the distance each pulse in the encoder equals to.
	 * Equation: (Wheel Diameter x Pi) / Number of pulses per encoder revolution */
	private final double gearDiameter = 4.0;
	private final int pulsePerRevolution = 40;
	private final double distancePerPulse = (Math.PI * gearDiameter) / pulsePerRevolution;
	private boolean speedButtonPressed;
	
	public Flywheel() {
		super();
		SmartDashboard.putNumber("Flywheel Motor Speed", flywheelMotor.get());
		state = ShootingState.FLYWHEEL_STOPPED;
		speedButtonPressed = false;
		setupEncoder();
	}
	

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}

	public void startFlywheel() {
		flywheelMotor.set(getPDSpeed(encoder.getRate(), flywheelSpeed, flywheelKp));
	}

	public void stopFlywheel() {
		flywheelMotor.stopMotor();
	}
	
	public void flywheelHalfSpeed() {
		flywheelMotor.set(0.5);
	}
	
	public void flywheelTest(double test) {
		flywheelMotor.set(test);
	}

	
	/** Reset all encoders. */
	public void resetEncoder() {
		encoder.reset();
	}
	
	/** Setup encoders before use. */
	public void setupEncoder() {
		encoder.setDistancePerPulse(0.0005);
		//leftEncoder.setPIDSourceType(PIDSourceType.kDisplacement);
		//SmartDashboard.putData("Flywheel Encoder", encoder);
	}
	
	/** Get the current value of the flywheel motor.
	 * Ranges from -1.0 to 1.0 */
	public double getFlywheelMotorSpeed(){
		return flywheelMotor.get();
	}
	
	/** Get the speed of the flywheel, which is determined
	 * by the encoder. */
	public double getFlywheelEncoderSpeed(){
		return encoder.getRate();
	}
	
	public double getEncoderCounts() {
		return encoder.get();
	}
	
	/** Adjust the speed of the */
	public void adjustFlywheelSpeed(int adjustment){
		//System.out.println("Button pressed = " + adjustment);
		if(adjustment == 0 /*&& !speedButtonPressed*/){
			System.out.println("Add speed to flywheel");
			flywheelSpeed = rangeValue(flywheelSpeed + speedAdjustment);
			speedButtonPressed = true;
		} else if (adjustment == 180 /*&& !speedButtonPressed*/){
			System.out.println("Slow down flywheel");
			flywheelSpeed = rangeValue(flywheelSpeed - speedAdjustment);
			speedButtonPressed = true;
		}
		else if ((adjustment != 0) && (adjustment != 180)){
			// Only set to false when not pressing either 
			// the up or down dpad
			speedButtonPressed = false;
		}
	}
	
	
	public void overrideFlywheelSpeed(double newSpeed){
		flywheelSpeed = Range.clip(newSpeed, 1, -1);
	}
	
	private double rangeValue(double value) {
		if (value > 1.0) {
			return 1.0;
		} 
		else if (value < -1.0) {
			return -1.0;
		}
		else {
			return value;
		}
	}
	
	/** Calculate a P and D values and apply it to the speed that 
	 * is going to be applied to the flywheel motor. */
	public double getPDSpeed(double currentSpeed, double targetSpeed, double Kp) {
		currentSpeed = Math.abs(currentSpeed);
		double error = (targetSpeed - currentSpeed);
		double addedSpeed = (Kp * error);
		addedSpeed = Range.clip(addedSpeed, 0.3, -0.3);
        double speedCmd;
        //System.out.println("inside getSpeedCmd, currentSpeed: " + currentSpeed);
        speedCmd = ((addedSpeed) + targetSpeed);
        //System.out.println("Speed CMD = " + speedCmd);
        return speedCmd;
	}
	
	public boolean motorSpeedEqualsSetSpeed(){
		if (Math.abs(encoder.getRate() - flywheelSpeed) < tolerance) {
		//if (Math.abs(Math.abs(flywheelMotor.get()) - flywheelSpeed) < tolerance) {
			return true;
		}
		return false;
	}
	
	public void setFlywheelState(ShootingState setState) {
		state = setState;
	}

	public ShootingState getFlywheelState() {
		return state;
	}
	
	public void resetFlywheelSpeed(){
		flywheelSpeed = RobotMap.flywheelMotorSpeed;
	}

}
