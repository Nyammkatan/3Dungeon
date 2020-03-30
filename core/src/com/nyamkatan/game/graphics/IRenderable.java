package com.nyamkatan.game.graphics;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;

public interface IRenderable {
	
	void update(float delta, Camera cam);
	void draw(DecalBatch batch, Camera cam);

}
