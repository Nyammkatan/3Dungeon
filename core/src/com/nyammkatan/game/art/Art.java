package com.nyammkatan.game.art;

import java.util.HashMap;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Art {
	
	public static final int DECAL_TEXTURE_SIZE = 16;
	public static HashMap<String, Texture> textures = new HashMap<String, Texture>();
	
	public static Texture getTexture(String s) {
		if (textures.containsKey(s)) {
			return textures.get(s);
		} else {
			Texture texture = new Texture(s);
			textures.put(s, texture);
			return texture;
		}
		
	}
	
	public static TextureRegion getTextureRegion(String texture, int x, int y) {
		return new TextureRegion(Art.getTexture(texture), 
				x*DECAL_TEXTURE_SIZE, y*DECAL_TEXTURE_SIZE,
				DECAL_TEXTURE_SIZE, DECAL_TEXTURE_SIZE);
		
	}
	
	public static TextureRegion getGeneralRegion(int x, int y) {
		return getTextureRegion("dn_tileset.png", x, y);
		
	}

}
