package org.usfirst.frc.team233.robot;

public class Range {
public static double clip (double cmd, double max, double min) {
	if (cmd > max) {
		return max;
	}
	else if (cmd < min){
		return min;
	}
	return cmd;
	}
}
