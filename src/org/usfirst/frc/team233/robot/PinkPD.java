package org.usfirst.frc.team233.robot;

public class PinkPD {

    private PinkPD() {}

    // Use a PD to determine a motor command, which has a range of -1 to 1 (-1=rev; 0=stop; 1=fwd)
    public static double getMotorCmd(double Kp, double Kd, double error, double currentVel) {
        double motorCmd = (Kp * error) - (Kd * currentVel);
        //motorCmd = Range.clip(motorCmd, -1.0, 1.0);
        return motorCmd;
    }

    // Use a PD to determine a continuous servo command, which has a range of 0 to 1 (0=rev; .5=stop; 1=fwd)
    public static double getServoCmd(double Kp, double Kd, double error, double currentVel) {

        double servoCmd;

        servoCmd = (Kp * error) - (Kd * currentVel);
        servoCmd = Range.clip(servoCmd, 1.0, -1.0);

        return servoCmd;
    }

}

