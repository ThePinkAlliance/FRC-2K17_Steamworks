package org.usfirst.frc.team233.robot;

import org.usfirst.frc.team233.robot.commands.ClimbCommand;
import org.usfirst.frc.team233.robot.commands.ClimbCommand.ClimberAction;
import org.usfirst.frc.team233.robot.commands.CollectorCommand;
import org.usfirst.frc.team233.robot.commands.CollectorCommand.CollectorAction;
import org.usfirst.frc.team233.robot.commands.GearCommand;
import org.usfirst.frc.team233.robot.commands.GearCommand.GearAction;
import org.usfirst.frc.team233.robot.commands.ResetFlywheel;
import org.usfirst.frc.team233.robot.commands.ShiftGear;
import org.usfirst.frc.team233.robot.commands.ShiftGear.ShiftAction;
import org.usfirst.frc.team233.robot.commands.Shoot;
import org.usfirst.frc.team233.robot.commands.Shoot.ShooterAction;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;


/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	public Joystick base = new Joystick(RobotMap.baseJoystickPort);
	public Joystick shooter = new Joystick(RobotMap.shooterJoystickPort);
	
	public OI() {
		// TODO Auto-generated constructor stub
		
		//==========================================
		//		Link to Joystick Buttons
		//==========================================
		
		//Base Driver
		JoystickButton x = new JoystickButton(base, 1);
		JoystickButton a = new JoystickButton(base, 2);
		JoystickButton b = new JoystickButton(base, 3);
		JoystickButton y = new JoystickButton(base, 4);
		JoystickButton leftBumper = new JoystickButton(base, 5);
		JoystickButton rightBumper = new JoystickButton(base, 6);
		JoystickButton leftTrigger = new JoystickButton(base, 7);
		JoystickButton rightTrigger = new JoystickButton(base, 8);
		JoystickButton select = new JoystickButton(base, 9);
		JoystickButton start = new JoystickButton(base, 10);
		JoystickButton leftJoystickPress = new JoystickButton(base, 11);
		JoystickButton rightJoystickPress = new JoystickButton(base, 12);
		int baseDpad = base.getPOV();
		
		//Shooter
		JoystickButton shooterX = new JoystickButton(shooter, 1);
		JoystickButton shooterA = new JoystickButton(shooter, 2);
		JoystickButton shooterB = new JoystickButton(shooter, 3);
		JoystickButton shooterY = new JoystickButton(shooter, 4);
		JoystickButton shooterLeftBumper = new JoystickButton(shooter, 5);
		JoystickButton shooterRightBumper = new JoystickButton(shooter, 6);
		JoystickButton shooterLeftTrigger = new JoystickButton(shooter, 7);
		JoystickButton shooterRightTrigger = new JoystickButton(shooter, 8);
		JoystickButton shooterSelect = new JoystickButton(shooter, 9);
		JoystickButton shooterStart = new JoystickButton(shooter, 10);
		JoystickButton shooterleftJoystickPress = new JoystickButton(shooter, 11);
		JoystickButton shooterrightJoystickPress = new JoystickButton(shooter, 12);
		int shooterDpad = shooter.getPOV();

		//==========================================
		//		Map Joysticks to Commands
		//==========================================
		shooterRightTrigger.whileHeld(new Shoot(ShooterAction.START_FLYWHEEL));
		shooterRightTrigger.whenReleased(new Shoot(ShooterAction.STOP_FLYWHEEL));
		
		shooterRightBumper.whileHeld(new Shoot(ShooterAction.SHOOT));
		shooterRightBumper.whenReleased(new Shoot(ShooterAction.CEASEFIRE));
		
		shooterStart.whenPressed(new ResetFlywheel());
		
		rightTrigger.whileHeld(new CollectorCommand(CollectorAction.COLLECT));
		rightTrigger.whenReleased(new CollectorCommand(CollectorAction.STOP));
		
		leftTrigger.whileHeld(new CollectorCommand(CollectorAction.EJECT));
		leftTrigger.whenReleased(new CollectorCommand(CollectorAction.STOP));
		
		rightBumper.whenPressed(new ShiftGear(ShiftAction.TOGGLE));
		
		shooterY.whileHeld(new ClimbCommand(ClimberAction.CLIMB));
		shooterY.whenReleased(new ClimbCommand(ClimberAction.STOP));
		
		//shooterA.whileHeld(new GearCommand(GearAction.OPEN_SLOT));
		shooterA.whenPressed(new GearCommand(GearAction.OPEN_SLOT));
		shooterA.whenReleased(new GearCommand(GearAction.CLOSE_SLOT));
		
		shooterLeftTrigger.whileHeld(new GearCommand(GearAction.EJECT_GEAR));
		shooterLeftTrigger.whenReleased(new GearCommand(GearAction.RETRACT_EJECTOR));
		
//		x.whileHeld(new Shoot(ShooterAction.TEST_I_UP));
//		x.whenReleased(new Shoot(ShooterAction.TEST_I_DOWN));
//		
//		y.whileHeld(new Shoot(ShooterAction.TEST_H_UP));
//		y.whenReleased(new Shoot(ShooterAction.TEST_H_DOWN));
//		
//		b.whileHeld(new Shoot(ShooterAction.TEST_F_UP));
//		b.whenReleased(new Shoot(ShooterAction.TEST_F_DOWN));
	}
	
	public Joystick getBaseJoystick() {
		return base;
	}
	public Joystick getShooterJoystick() {
		return shooter;
	}
}
