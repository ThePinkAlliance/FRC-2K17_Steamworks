package org.usfirst.frc.team233.robot;

import org.usfirst.frc.team233.robot.commands.CollectorCommand;
import org.usfirst.frc.team233.robot.commands.CollectorCommand.CollectorAction;
import org.usfirst.frc.team233.robot.commands.ShiftGear;
import org.usfirst.frc.team233.robot.commands.ShiftGear.ShiftAction;
import org.usfirst.frc.team233.robot.commands.Shoot;
import org.usfirst.frc.team233.robot.commands.Shoot.ShooterAction;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;


public class OI {
	public Joystick base = new Joystick(RobotMap.baseJoystickPort);
	
	public OI() {	
		//==========================================
		//		Link to Joystick Buttons
		//==========================================

		JoystickButton leftBumper = new JoystickButton(base, 5);
		JoystickButton rightBumper = new JoystickButton(base, 6);
		JoystickButton leftTrigger = new JoystickButton(base, 7);

		//==========================================
		//		Map Joysticks to Commands
		//==========================================
		
		rightBumper.whileHeld(new Shoot(ShooterAction.START_FLYWHEEL));
		rightBumper.whenReleased(new Shoot(ShooterAction.STOP_FLYWHEEL));
		
		rightBumper.whileHeld(new Shoot(ShooterAction.SHOOT));
		rightBumper.whenReleased(new Shoot(ShooterAction.CEASEFIRE));
		
		leftBumper.whileHeld(new CollectorCommand(CollectorAction.COLLECT));
		leftBumper.whenReleased(new CollectorCommand(CollectorAction.STOP));
		
		leftTrigger.whenPressed(new ShiftGear(ShiftAction.TOGGLE));	
	}
	
	public Joystick getBaseJoystick() {
		return base;
	}
}
