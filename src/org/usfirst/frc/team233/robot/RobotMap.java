package org.usfirst.frc.team233.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	
	//============================================
	// 		DRIVE TRAIN VARIABLES
	//============================================
	// Motors
	public static int leftFrontMotorPort = 2;
	public static int leftBackMotorPort = 3;
	public static int rightFrontMotorPort = 0;
	public static int rightBackMotorPort = 1;
	
	// Encoders
	public static int leftEncoderAPort = 0;
	public static int leftEncoderBPort = 1;
	public static int rightEncoderAPort = 2;
	public static int rightEncoderBPort = 3;
	
	// Pneumatics
	public static int compressorPort = 0;
	public static int shiftingSolenoidPort = 0;
	
	// Gyro
	public static int gyroPort = 0;
	
	// Power Distribution Panel
	public static int pdpDeviceID = 1;
	
	//============================================
	// 		SHOOTER VARIABLES
	//============================================
	// Motors
	public static int flywheelMotorPort = 4;
	public static int indexerMotorPort = 5;
	
	// Motor Speeds
	public static double flywheelMotorSpeed = 0.69;
	public static double indexerMotorSpeed = 0.75;
	
	// Encoder
	public static int flywheelEncoderPortA = 5;
	public static int flywheelEncoderPortB = 6;
	
	
	//============================================
	// 		COLLECTOR VARIABLES
	//============================================
	// Motor
	public static int collectorMotorPort = 6;
	
	// Motor Speed
	public static double collectorMotorSpeed = 0.75;
	

	//============================================
	// 		ROPE CLIMBER VARIABLES
	//============================================
	// Motors
	public static int ropeClimberMotorPort = 7;
	public static int ropeClimber2MotorPort = 9;
	
	// Motor Speeds
	public static double ropeClimberSpeed = 1.0;
	
	
	//============================================
	// 		HOPPER VARIABLES
	//============================================
	// Motors
	public static int agitatorMotorPort = 8;
	
	// Motor Speeds
	public static double agitatorMotorSpeed = 0.75;
	
	
	//============================================
	// 		GEAR SLOT VARIABLES
	//============================================
	public static int gearSlotSolenoidPort = 7;
	public static int ejectorSolenoidPort = 6;
	
	
	//============================================
	// 		JOYSTICK VARIABLES
	//============================================
	public static int baseJoystickPort = 0;
	public static int shooterJoystickPort = 1;
	
	// Define all raw button numbers
	public static int xButtonNumber = 1;
	public static int aButtonNumber = 2;
	public static int bButtonNumber = 3;
	public static int yButtonNumber = 4;
	public static int leftBumperButtonNumber = 5;
	public static int rightBumperButtonNumber = 6;
	public static int leftTriggerButtonNumber = 7;
	public static int rightTriggerButtonNumber = 8;
	public static int selectButtonNumber = 9;
	public static int startButtonNumber = 10;
	public static int leftJoystickButtonNumber = 11;
	public static int rightJoystickButtonNumber = 12;
	
	
	
	
	
}