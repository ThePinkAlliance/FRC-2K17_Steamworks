package org.usfirst.frc.team233.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team233.robot.subsystems.BallCollector;
import org.usfirst.frc.team233.robot.subsystems.DriveTrain;
import org.usfirst.frc.team233.robot.subsystems.Flywheel;
import org.usfirst.frc.team233.robot.subsystems.Hopper;
import org.usfirst.frc.team233.robot.subsystems.Indexer;
import org.usfirst.frc.team233.robot.subsystems.Lights;
import org.usfirst.frc.team233.robot.subsystems.Lights.LightingType;
import org.usfirst.frc.team233.robot.OI;

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
	public static Hopper hopper;
	public static OI oi;
	public static PowerDistributionPanel pdPanel;
	public static Lights lights;
	public static double delayTime;
	

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
		hopper = new Hopper();
		lights = new Lights();
		oi = new OI();
		pdPanel = new PowerDistributionPanel(RobotMap.pdpDeviceID);
		pdPanel.resetTotalEnergy();
		// didn't work, value didn't get passed
		
		
		lights.activateLights(LightingType.off);
		lights.activateLights(LightingType.staying_alive);
		
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

	@Override
	public void teleopInit() {
		drivetrain.getGyroRotation();
		System.out.println("Teleop Init");
		flywheel.resetFlywheelSpeed();
		drivetrain.resetEncoders();
		flywheel.resetEncoder();
		drivetrain.setDriveTrainSafety(true);
		Robot.drivetrain.shiftGears(false);
		flywheel.flywheelMotor.set(0);
		indexer.indexer.set(0);
		hopper.stopAgitate();
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
