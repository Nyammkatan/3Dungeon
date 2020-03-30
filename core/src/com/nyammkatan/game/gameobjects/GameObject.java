package com.nyammkatan.game.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.math.Vector2;
import com.nyamkatan.game.graphics.IRenderable;
import com.nyammkatan.game.Game;

public abstract class GameObject implements IRenderable {
	
	public static final float BLOCK_HALF = Game.BLOCK_SIZE/2f;
	public static final float OBJECT_SIZE = Game.BLOCK_SIZE/3f;
	public static final float OBJECT_HALF = OBJECT_SIZE/2f;
	public static final float RENDER_DISTANCE = 5f;

	public float x;
	public float z;
	
	public Decal decal;
	
	public GameObject(float x, float z, TextureRegion region) {
		this.x = x;
		this.z = z;
		decal = Decal.newDecal(region, true);
		decal.setDimensions(Game.BLOCK_SIZE, Game.BLOCK_SIZE);
		
	}

	@Override
	public void update(float delta, Camera cam) {
		decal.lookAt(cam.position, cam.up);
		decal.setPosition(x, 0, z);
		
	}
	
	public void setTextureRegion(TextureRegion region) {
		decal.setTextureRegion(region);
		
	}
	
	public boolean isCloseEnough(Camera cam) {
		return Vector2.dst(x, z, cam.position.x, cam.position.z) < RENDER_DISTANCE;
		
	}

	@Override
	public void draw(DecalBatch batch, Camera cam) {
		if (this.isCloseEnough(cam)) {
			batch.add(this.decal);
		}
		
	}
	
}
