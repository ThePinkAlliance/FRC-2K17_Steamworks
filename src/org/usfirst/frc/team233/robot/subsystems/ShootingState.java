package org.usfirst.frc.team233.robot.subsystems;

/**
 * This ENUM defines all possible states that the shooter
 * subsystem will have. */
public enum ShootingState {
	BALLS_RELEASING,	//indexer & agitator motors spinning
	//CEASEFIRE,	//indexer & agitator stopped & flywheel spinning
	FLYWHEEL_INIT,	//flywheel instantiated (change to FLYWHEEL_STOPPED
	FLYWHEEL_HALF_SPEED,	//flywheel motor @50%
	FLYWHEEL_SPINNING_UP,	//flywheel spinning up from 50% to 100%
	FLYWHEEL_STOPPED,	//flywheel motor @0%
	FLYWHEEL_UP_TO_SPEED,	//flywheel motor @100%
	INDEXER_STOPPED,	//indexer motor @0%
	//READY_TO_SHOOT, 	//
	FLYWHEEL_STARTED,		//flywheel motor <50%
	IGNORE,
	//STOP_FLYWHEEL,		//flywheel motor @0%
	//SHOOTING_INTERRUPTED,	//undef case
	SHOOTING,		//all 3 motors are running; == BALLS_RELEASING + FLYWHEEL_UP_TO_SPEED
	//SHOOT		//start flywheel to full speed, verify @100%, start agitator process begin process of agitating & shooting
}