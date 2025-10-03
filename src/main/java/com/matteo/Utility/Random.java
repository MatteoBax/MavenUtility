package com.matteo.Utility;

public class Random {
	public static int generateRandomInt(int min, int max) {
		return min + (int)(Math.random() * (max - min + 1));
	}
	
	public static double generateRandomDouble(double min, double max) {
		return min + (Math.random() * (max - min + 1));
	}
}
