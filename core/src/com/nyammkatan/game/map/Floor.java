package com.nyammkatan.game.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.nyammkatan.game.Game;
import com.nyammkatan.game.art.Art;

public class Floor implements DecalDrawable {
	
	public int type;
	public float x;
	public float y;
	public float z;
	
	public Decal decal;
	
	public TextureRegion tr;
	
	public Floor(int type, float x, float y, float z) {
		this.type = type;
		this.x = x;
		this.y = y;
		this.z = z;
		this.decal = Decal.newDecal(getTextureRegion(type));
		decal.setPosition(x, y-(Game.BLOCK_SIZE*0.5f), z);
		decal.setRotationX(-90f);
		decal.setDimensions(Game.BLOCK_SIZE, Game.BLOCK_SIZE);
		
	}
	
	public TextureRegion getTextureRegion(int type) {
		if (tr == null) {
			tr = Art.getGeneralRegion(1, type);
			return tr;
		}
		return tr;
		
	}
	
	public void setType(int type) {
		decal.setTextureRegion(getTextureRegion(type));
		
	}
	
	public void update(float delta) {
		decal.setPosition(x, y-(Game.BLOCK_SIZE*0.5f), z);
		
	}
	
	public void draw(DecalBatch batch) {
		batch.add(decal);
		
	}
	
}
