package com.nyammkatan.game.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;
import com.nyammkatan.game.Game;
import com.nyammkatan.game.map.Block;
import com.nyammkatan.game.map.BlockArray;
import com.nyammkatan.game.map.Map;
import com.nyammkatan.game.utils.RayHelper;

public class CameraPlayer {
	
	public static final float PLAYER_SIZE = 0.5f;
	public static final float PLAYER_HALF = PLAYER_SIZE/2f;
	public static final float BLOCK_HALF = Game.BLOCK_SIZE/2f;
	
	public float speed = 2f;
	public int[][] mapData;
	Camera cam;
	public CameraPlayer(Camera cam, Map gameMap) {
		this.cam = cam;
		mapData = gameMap.array;
		
	}
	
	public void update(float delta) {
		boolean isMoving = false;
		if (Gdx.input.isKeyPressed(Keys.W)) {
			float angler = (float) Math.atan2(cam.direction.z, cam.direction.x);
			float dx = (float) (delta * Math.cos(angler) * speed);
			float dz = (float) (delta * Math.sin(angler) * speed);
			move(dx, dz);
			isMoving = true;
		} else 
		if (Gdx.input.isKeyPressed(Keys.S)) {
			float angler = (float) Math.atan2(cam.direction.z, cam.direction.x);
			float dx = (float) (delta * Math.cos(angler) * -speed);
			float dz = (float) (delta * Math.sin(angler) * -speed);
			move(dx, dz);
			isMoving = true;
			
		}
		if (Gdx.input.isKeyPressed(Keys.A)) {
			float angler = (float) Math.atan2(cam.direction.z, cam.direction.x);
			float dx = (float) (delta * Math.cos(Math.toRadians(Math.toDegrees(angler)-90f)) * speed);
			float dz = (float) (delta * Math.sin(Math.toRadians(Math.toDegrees(angler)-90f)) * speed);
			move(dx, dz);	
			isMoving = true;
			
		} else
		if (Gdx.input.isKeyPressed(Keys.D)) {
			float angler = (float) Math.atan2(cam.direction.z, cam.direction.x);
			float dx = (float) (delta * Math.cos(Math.toRadians(Math.toDegrees(angler)+90f)) * speed);
			float dz = (float) (delta * Math.sin(Math.toRadians(Math.toDegrees(angler)+90f)) * speed);
			move(dx, dz);
			isMoving = true;
			
		}
		RayHelper.cameraFpc(cam, isMoving);
		
	}
	
	public boolean canGoX(Camera cam, float dx) {
		float nPx = cam.position.x + dx;
		float nPz = cam.position.z;
		return canGo(nPx, nPz);
		
	}
	
	public boolean canGoZ(Camera cam, float dz) {
		float nPx = cam.position.x;
		float nPz = cam.position.z + dz;
		return canGo(nPx, nPz);
		
	}
	
	public boolean canGoXZ(Camera cam, float dx, float dz) {
		float nPx = cam.position.x + dz;
		float nPz = cam.position.z + dz;
		return canGo(nPx, nPz);
		
	}
	
	public boolean canGo(float nPx, float nPz) {
		int ltcx = (int)(nPx-PLAYER_HALF+BLOCK_HALF);
		int ltcz = (int)(nPz-PLAYER_HALF+BLOCK_HALF);
		
		int lbcx = (int)(nPx-PLAYER_HALF+BLOCK_HALF);
		int lbcz = (int)(nPz+PLAYER_HALF+BLOCK_HALF);
		
		int rtcx = (int)(nPx+PLAYER_HALF+BLOCK_HALF);
		int rtcz = (int)(nPz-PLAYER_HALF+BLOCK_HALF);
		
		int rbcx = (int)(nPx+PLAYER_HALF+BLOCK_HALF);
		int rbcz = (int)(nPz+PLAYER_HALF+BLOCK_HALF);
		
		if (mapData[ltcz][ltcx] == 0) {
			return false;
		}
		if (mapData[lbcz][lbcx] == 0) {
			return false;
		}
		if (mapData[rtcz][rtcx] == 0) {
			return false;
		}
		if (mapData[rbcz][rbcx] == 0) {
			return false;
		}
		return true;
		
	}
	
	public void move(float dx, float dz) {
		if (canGoX(cam, dx)) {
			cam.position.x += dx;
		}
		if (canGoZ(cam, dz)) {
			cam.position.z += dz;
		}
		
	}

}
