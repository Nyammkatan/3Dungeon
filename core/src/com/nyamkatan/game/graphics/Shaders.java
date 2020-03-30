package com.nyamkatan.game.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class Shaders {
	
	public static ShaderProgram blockShader = createShader("blockShader");
	public static ShaderProgram objectShader = createShader("objectShader");
	
	public static ShaderProgram createShader(String name) {
		return new ShaderProgram(
				Gdx.files.internal(name+"/shader.vert"),
				Gdx.files.internal(name+"/shader.frag"));
		
	}

}
