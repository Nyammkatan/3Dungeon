package com.nyammkatan.game.gameobjects.items;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.nyammkatan.game.Game;
import com.nyammkatan.game.gameobjects.GameObject;
import com.nyammkatan.game.gameobjects.Item;

public abstract class Potion extends Item {

	public Potion(float x, float z, TextureRegion region) {
		super(x, z, region);
		this.decal.setDimensions(GameObject.BLOCK_HALF, GameObject.BLOCK_HALF);
		
	}
	
	@Override
	public void update(float delta, Camera cam) {
		super.update(delta, cam);
		this.decal.getPosition().y = (float) (Math.sin(Game.time*5)/20f)-0.2f;
		
	}

}
