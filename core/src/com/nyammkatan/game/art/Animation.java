package com.nyammkatan.game.art;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Animation {
	
	public ArrayList<TextureRegion> regions;
	public float time = 0;

	public Animation(float time, String tname, int... coords) {
		this.time = time;
		regions = new ArrayList<TextureRegion>();
		for (int i=0; i < coords.length; i+=2) {
			TextureRegion region = Art.getTextureRegion(tname, coords[i], coords[i+1]);
			regions.add(region);
			
		}
		
	}

}
