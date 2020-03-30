package com.nyammkatan.game.gameobjects.mobs;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.nyamkatan.game.graphics.Decals;
import com.nyamkatan.game.graphics.IUniformSetter;
import com.nyamkatan.game.graphics.Shaders;
import com.nyammkatan.game.Game;
import com.nyammkatan.game.art.APlayer;
import com.nyammkatan.game.art.Animation;
import com.nyammkatan.game.gameobjects.Mob;
import com.nyammkatan.game.map.Map;

public class Eye extends Mob {

	public static Animation eyeAnimation = new Animation(0.2f, "dn_tileset.png", 3, 0, 4, 0, 5, 0);
	
	public APlayer aPlayer;
	public int batch_id;
	
	public Eye(float x, float z, Map map) {
		super(x, z, eyeAnimation.regions.get(0), map);
		aPlayer = new APlayer(eyeAnimation);
		
	}
	
	public void update(float delta, Camera cam) {
		super.update(delta, cam);
		aPlayer.update(delta);
		this.setTextureRegion(aPlayer.getCurrentFrame());
		this.moveTo(cam.position.x, cam.position.z);
		
	}

}
