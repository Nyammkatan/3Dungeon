package com.nyammkatan.game.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.graphics.g3d.decals.DecalMaterial;
import com.nyammkatan.game.Game;
import com.nyammkatan.game.art.Art;

public class Block implements DecalDrawable {
	
	public int type;
	public float x;
	public float y;
	public float z;
	
	public Decal[] decals;
	
	public TextureRegion tr;
	
	public Block(int type, float x, float y, float z) {
		this.type = type;
		this.x = x;
		this.y = y;
		this.z = z;
		this.decals = new Decal[4];
		init();
	}
	
	public TextureRegion getTextureRegion(int type) {
		if (tr == null) {
			tr = Art.getGeneralRegion(0, type);
			return tr;
		}
		return tr;
		
	}
	
	public void setType(int type) {
		for (int i=0; i < decals.length; i++) {
			if (decals[i] != null) {
				decals[i].setTextureRegion(getTextureRegion(type));
				
			}
			
		}
		
	}
	
	public void init() {
		float half = Game.BLOCK_SIZE*0.5f;
		
		Decal d = decals[0];
		d = Decal.newDecal(getTextureRegion(type));
		d.setPosition(x-half, y, z);
		d.setRotationY(-90f);
		d.setDimensions(Game.BLOCK_SIZE, Game.BLOCK_SIZE);
		decals[0] = d;
		
		d = decals[1];
		d = Decal.newDecal(getTextureRegion(type));
		d.setPosition(x, y, z+half);
		d.setDimensions(Game.BLOCK_SIZE, Game.BLOCK_SIZE);
		decals[1] = d;
		
		d = decals[2];
		d = Decal.newDecal(getTextureRegion(type));
		d.setPosition(x, y, z-half);
		d.setRotationY(180f);
		d.setDimensions(Game.BLOCK_SIZE, Game.BLOCK_SIZE);
		decals[2] = d;
		
		d = decals[3];
		d = Decal.newDecal(getTextureRegion(type));
		d.setPosition(x+half, y, z);
		d.setRotationY(90f);
		d.setDimensions(Game.BLOCK_SIZE, Game.BLOCK_SIZE);
		decals[3] = d;
		
	}
	
	public void update(float delta) {
		float half = Game.BLOCK_SIZE*0.5f;
		decals[0].setPosition(x-half, y, z);
		decals[1].setPosition(x, y, z+half);
		decals[2].setPosition(x, y, z-half);
		decals[3].setPosition(x+half, y, z);
		
	}
	
	public void draw(DecalBatch batch) {
		for (int i=0; i < decals.length; i++) {
			if (decals[i] != null) {
				batch.add(decals[i]);
			}
		}
		
	}
	
}
