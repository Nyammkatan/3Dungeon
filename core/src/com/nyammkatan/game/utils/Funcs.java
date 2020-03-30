package com.nyammkatan.game.utils;

public class Funcs {

	public static float clamp(float value, float min, float max) {
		if (value < min) return min;
		if (value > max) return max;
		return value;
		
	}

	public static float lerp(float fx1, float fx2, float x1, float x, float x2) {
		return fx1+( fx2 - fx1 )*(x - x1)/(x2 - x1);
		
	}
	
}
