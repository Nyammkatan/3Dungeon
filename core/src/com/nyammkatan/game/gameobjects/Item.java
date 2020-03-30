package com.nyammkatan.game.gameobjects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class Item extends GameObject {

	public Item(float x, float z, TextureRegion region) {
		super(x, z, region);
		
	}
	
	public abstract void onPickUp();
	public abstract void onUse();

}
