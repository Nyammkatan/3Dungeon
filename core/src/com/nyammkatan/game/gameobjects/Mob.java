package com.nyammkatan.game.gameobjects;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.math.Vector2;
import com.nyamkatan.game.graphics.IRenderable;
import com.nyammkatan.game.Game;
import com.nyammkatan.game.art.Art;
import com.nyammkatan.game.map.Map;

public abstract class Mob extends GameObject {

	float health = 100f;
	float mSpeed = 1f/40f;
	int[][] mapData;
	
	public Mob(float x, float z, TextureRegion region, Map gameMap) {
		super(x, z, region);
		this.mapData = gameMap.array;
		
	}
	
	public void moveTo(float x, float z) {
		if (Vector2.dst(this.x, this.z, x, z) > Game.BLOCK_SIZE*0.8f) {
			float xDist = x - this.x;
			float zDist = z - this.z;
			float angle = (float) Math.atan2(zDist, xDist);
			float dx = (float) (Math.cos(angle) * mSpeed);
			float dz = (float) (Math.sin(angle) * mSpeed);
			move(dx, dz);
			
		}
		
	}
	
	public boolean canGoX(float dx) {
		float nPx = this.x + dx;
		float nPz = this.z;
		return canGo(nPx, nPz);
		
	}
	
	public boolean canGoZ(float dz) {
		float nPx = this.x;
		float nPz = this.z + dz;
		return canGo(nPx, nPz);
		
	}
	
	public boolean canGo(float nPx, float nPz) {
		int ltcx = (int)(nPx-OBJECT_HALF+BLOCK_HALF);
		int ltcz = (int)(nPz-OBJECT_HALF+BLOCK_HALF);
		
		int lbcx = (int)(nPx-OBJECT_HALF+BLOCK_HALF);
		int lbcz = (int)(nPz+OBJECT_HALF+BLOCK_HALF);
		
		int rtcx = (int)(nPx+OBJECT_HALF+BLOCK_HALF);
		int rtcz = (int)(nPz-OBJECT_HALF+BLOCK_HALF);
		
		int rbcx = (int)(nPx+OBJECT_HALF+BLOCK_HALF);
		int rbcz = (int)(nPz+OBJECT_HALF+BLOCK_HALF);
		
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
		if (canGoX(dx)) {
			this.x += dx;
		}
		if (canGoZ(dz)) {
			this.z += dz;
		}
		
	}
	
}
