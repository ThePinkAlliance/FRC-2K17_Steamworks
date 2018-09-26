package org.usfirst.frc.team233.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
//import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team233.robot.autonomous.AutoGearRoutine1;
import org.usfirst.frc.team233.robot.autonomous.AutoGearRoutine2;
import org.usfirst.frc.team233.robot.autonomous.AutoGearRoutine3;
import org.usfirst.frc.team233.robot.autonomous.AutoShootRoutine1;
import org.usfirst.frc.team233.robot.autonomous.AutoSitAndShoot;
import org.usfirst.frc.team233.robot.commands.GearCommand;
import org.usfirst.frc.team233.robot.commands.GearCommand.GearAction;
import org.usfirst.frc.team233.robot.subsystems.BallCollector;
import org.usfirst.frc.team233.robot.subsystems.DriveTrain;
import org.usfirst.frc.team233.robot.subsystems.Flywheel;
import org.usfirst.frc.team233.robot.subsystems.Hopper;
import org.usfirst.frc.team233.robot.subsystems.Indexer;
import org.usfirst.frc.team233.robot.subsystems.Lights;
import org.usfirst.frc.team233.robot.subsystems.Lights.LightingType;
import org.usfirst.frc.team233.robot.subsystems.RopeClimber;
import org.usfirst.frc.team233.robot.subsystems.GearSlot;
import org.usfirst.frc.team233.robot.OI;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	
	
	// Define subsystem variables
	public static DriveTrain drivetrain;
	public static Flywheel flywheel;
	public static Indexer indexer;
	public static BallCollector ballCollector;
	public static RopeClimber ropeClimber;
	public static Hopper hopper;
	public static GearSlot gearSlot;
	public static OI oi;
	public static PowerDistributionPanel pdPanel;
	public static Lights lights;
	private UsbCamera gearCamera;
	public static double delayTime;
	

	Command autonomousCommand;
	SendableChooser<Command> chooser = new SendableChooser<>();
	SendableChooser<Double> delay = new SendableChooser<>();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		drivetrain = new DriveTrain();
		flywheel = new Flywheel();
		indexer = new Indexer();
		ballCollector = new BallCollector();
		ropeClimber = new RopeClimber();
		hopper = new Hopper();
		lights = new Lights();
		gearSlot = new GearSlot();
		oi = new OI();
		pdPanel = new PowerDistributionPanel(RobotMap.pdpDeviceID);
		pdPanel.resetTotalEnergy();
		// didn't work, value didn't get passed
//		SmartDashboard.putNumber("Autonomous delay", 0.0);
//		double delay = SmartDashboard.getNumber("Autonomous delay", 0.0);
//		SmartDashboard.putNumber("Autonomous delay", delay);
		SmartDashboard.putNumber("Autonomous delay", 0.0);
		setupAutonomousList();
		SmartDashboard.putData("Auto Mode", chooser);
		
//		delayTime = 0;
//		SmartDashboard.putNumber("Autonomous delay", delayTime);
		SmartDashboard.putData("Auto Delay", delay);
		
		lights.activateLights(LightingType.off);
		lights.activateLights(LightingType.staying_alive);
		
		try {
			gearCamera = CameraServer.getInstance().startAutomaticCapture();
			gearCamera.setResolution(480, 320);
			gearCamera.setFPS(30);
			//System.out.println(gearCamera.getPath());
		} catch (Exception e) {
			//Do nothing
		}
		
	}
	
	
	/**
	 * Add all the autonomous routines to the 
	 * chooser list
	 * */
	private void setupAutonomousList() {
		chooser.addObject("RED Gear Left", new AutoGearRoutine1(false));
		chooser.addObject("RED Gear Center", new AutoGearRoutine2(false));
		chooser.addObject("RED Gear Right", new AutoGearRoutine3(false));
		
		chooser.addObject("BLUE Gear Right", new AutoGearRoutine1(true));
		chooser.addDefault("BLUE Gear Center", new AutoGearRoutine2(true));
		chooser.addObject("BLUE Gear Left", new AutoGearRoutine3(true));
		
		chooser.addObject("Auto Shoot Routine 1", new AutoShootRoutine1());
		chooser.addObject("BLUE Sit and Shoot", new AutoSitAndShoot(true));
		chooser.addObject("RED Sit and Shoot", new AutoSitAndShoot(false));
		
		//delay options
		delay.addDefault("0 sec", new Double(0));
		delay.addObject("1 sec", new Double(1));
		delay.addObject("3 sec", new Double(3));
		delay.addObject("5 sec", new Double(5));
		delay.addObject("7 sec", new Double(7));
		delay.addObject("10 sec", new Double(10));
		
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {
		// Reset any resources that are not needed
		Robot.drivetrain.disableDriveTrain();
	}

	@Override
	public void disabledPeriodic() {
		delayTime = SmartDashboard.getNumber("Autonomous delay", delayTime);
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
//		delayTime = SmartDashboard.getNumber("Autonomous delay", delayTime);
		System.out.println("AutoInit");
		delayTime = delay.getSelected().doubleValue();
		setupAutonomousList();		
		autonomousCommand = chooser.getSelected();
		drivetrain.resetGyro();
		drivetrain.resetEncoders();
		drivetrain.setDriveTrainSafety(false);
		Robot.gearSlot.closeGearSlot();
		Robot.gearSlot.retractEjecter();
		Robot.drivetrain.shiftGears(false);
		flywheel.flywheelMotor.set(0);
		indexer.indexer.set(0);
		hopper.stopAgitate();
		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		if (autonomousCommand != null)
			autonomousCommand.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		log();
	}

	@Override
	public void teleopInit() {
		drivetrain.getGyroRotation();
		//ASUME 4inch wheels
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null)
			autonomousCommand.cancel();
		System.out.println("Teleop Init");
		flywheel.resetFlywheelSpeed();
		drivetrain.resetEncoders();
		flywheel.resetEncoder();
		drivetrain.setDriveTrainSafety(true);
		Robot.gearSlot.retractEjecter();
		Robot.gearSlot.closeGearSlot();
		Robot.drivetrain.shiftGears(false);
		flywheel.flywheelMotor.set(0);
		indexer.indexer.set(0);
		hopper.stopAgitate();
		
		
		//Scheduler.getInstance().enable();
		//Scheduler.getInstance().removeAll();
		//Scheduler.getInstance().add(tankDrive);
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		log();
	}
	
	public void log() {
//		SmartDashboard.putData("Power Distribution Panel", pdPanel);
		SmartDashboard.putNumber("Flywheel motor", pdPanel.getCurrent(14));
		SmartDashboard.putNumber("Agitator motor", pdPanel.getCurrent(4));
		SmartDashboard.putNumber("Indexer motor", pdPanel.getCurrent(5));
		SmartDashboard.putNumber("Collector motor", pdPanel.getCurrent(8));
		SmartDashboard.putNumber("Left Encoder = ", drivetrain.getLeftDistance());
		SmartDashboard.putNumber("Right Encoder = ", drivetrain.getRightDistance());
		SmartDashboard.putNumber("Left Raw = ", drivetrain.leftEncoder.getRaw());
		SmartDashboard.putNumber("Right Raw = ", drivetrain.rightEncoder.getRaw());
		SmartDashboard.putNumber("Flywheel Encoder Count", flywheel.getEncoderCounts());
		SmartDashboard.putNumber("Flywheel Encoder Rate", flywheel.getFlywheelEncoderSpeed());
		SmartDashboard.putNumber("Flywheel Motor Speed", flywheel.getFlywheelMotorSpeed());
		//SmartDashboard.putNumber("Count Encoder Left", drivetrain.getLeftEncoderCount());
		//SmartDashboard.putNumber("Count Encoder Right", drivetrain.getRightEncoderCount());
		SmartDashboard.putData("Gyro", drivetrain.getDriveTrainGyro());
		SmartDashboard.putNumber("Gyro rate ", drivetrain.getGyroRate());
		SmartDashboard.putNumber("Gyro angle ", drivetrain.getGyroRotation());
	}
	
	@Override
	public void robotPeriodic() {
	}
	
	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
}
