package com.nyamkatan.game.graphics;

import java.util.HashMap;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.nyammkatan.game.Game;

public class Decals {
	
	private static DecalBatch dBatch;
	
	public static void initDecalBatch(ShaderProgram shader, IUniformSetter setter) {
		dBatch = new DecalBatch(new DecalGroupStrategy(Game.gameCamera, shader, setter));
		
	}
	
	public static DecalBatch getBatch() {
		return dBatch;
		
	}
	
	public static void flush() {
		dBatch.flush();
		
	}
	
	public static void dispose() {
		dBatch.dispose();
		
	}

}
