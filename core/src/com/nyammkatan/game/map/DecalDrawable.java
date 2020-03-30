package com.nyammkatan.game.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.nyammkatan.game.art.Art;

public interface DecalDrawable {
	
	void setType(int type);
	void draw(DecalBatch dbatch);

}
