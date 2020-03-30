package com.nyammkatan.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.nyammkatan.game.Game;

public class RayHelper {
	
	public static float SHAKE_FACTOR = 0.02f;
	public static float SPEED_FACTOR = 10f;
	
	public static void cameraFpc(Camera cam, boolean isMoving) {
		if (isMoving)
			cam.position.y = (float) (Math.sin(Game.time*SPEED_FACTOR)*SHAKE_FACTOR);
		else
			cam.position.y = 0f;
		cam.direction.y = 0;
		Ray ray = cam.getPickRay(Gdx.input.getX(), Gdx.input.getY()-1f);
		cam.direction.set(ray.direction.x, 0, ray.direction.z);
		Gdx.input.setCursorPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
		
	}
	
}
