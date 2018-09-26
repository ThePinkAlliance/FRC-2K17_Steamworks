package org.usfirst.frc.team233.robot.subsystems;

import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Lights extends Subsystem {
	public enum LightingType {
		police,
		init,
		rainbow, 
		off, 
		missing_dot,
		breathe,
		heartbeat,
		staying_alive,
		shooter,
		set_pink_color,
		set_random_color;
	};
	
	private SerialPort arduino;
	private Semaphore mutex = new Semaphore(1);
	private boolean isUsbAvailable = true;
	private LightingType lastCommand;
	
	public Lights() {
		// TODO Auto-generated constructor stub
		try {
			arduino = new SerialPort(9600, SerialPort.Port.kUSB1);
		} catch (Exception e) {
			isUsbAvailable = false;
		}
		lastCommand = LightingType.init;
	}
	
	

	@Override
	protected void initDefaultCommand() {
	}

	public void activateLights(LightingType lightingType) {
		if (lightingType == lastCommand) {
			System.out.println("Command " + lightingType.toString() + " already sent");
			return;
		}
		
		switch(lightingType) {
			case police:
				sendLightCommand("police,255,0,0,0,0,0,0,0!");
				break;
				
			case rainbow:
				sendLightCommand("rainbow,0,0,0,0,0,0,1,0!");
				break;
				
			case shooter:
				sendLightCommand("shooter,255,0,0,0,255,0,1,10!");
				break;
				
			case missing_dot:
				sendLightCommand("missing_dot_chase,255,0,0,0,0,0,10,0!");
				break;
				
			case heartbeat:
				sendLightCommand("heartbeat,255,0,0,0,0,0,0,0!");
				break;
				
			case breathe:
				sendLightCommand("breathe,0,255,0,0,0,0,0,10!");
				break;
				
			case staying_alive:
				sendLightCommand("stayingAlive,255,0,0,255,21,80,100,2!");
				break;
		
			case set_pink_color:
				sendLightCommand("setColor,255,21,80,0,0,0,0,0!");
				break;

			case set_random_color:
				int r = ThreadLocalRandom.current().nextInt(0, 255);
				int g = ThreadLocalRandom.current().nextInt(0, 255);
				int b = ThreadLocalRandom.current().nextInt(0, 255);
				sendLightCommand("setColor," + new Integer(r).toString() + "," + new Integer(g).toString() + "," + new Integer(b).toString() + ",0,0,0,0,0!");
				break;

			// The lights command for the off case is caught here in default
			default:
				sendLightCommand("off,0,0,0,0,0,0,0,0!");
				break;
		}
		lastCommand = lightingType;
	}
	
	private void sendLightCommand(String command) {
		if (!isUsbAvailable) {
			return;
		}
		
		try {	
			mutex.acquire();
			arduino.writeString("*");
			arduino.flush();
			
			Timer.delay(0.25);
			
			arduino.writeString(command);
			arduino.flush();
			
			mutex.release();
		} catch (InterruptedException ie) {
			//ie.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
		}	
	}
	
}
