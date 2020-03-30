package com.nyammkatan.game.map;

import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.nyammkatan.game.Game;

public class CeilArray {
	
	public Ceil[][] ceils;
	
	public CeilArray(Map gameMap) {
		int[][] map = gameMap.array;
		ceils = new Ceil[map.length][map[0].length];
		for (int i=0; i < ceils.length; i++) {
			for (int j=0; j < ceils[i].length; j++) {
				if (map[i][j] == 1 || map[i][j] == 2) {
					ceils[i][j] = new Ceil(gameMap.typeArray[i][j], j, 0, i);
				}
			}
		}
		
	}
	
	public void update(float delta, int ic, int jc) {
		for (int i=ic-Game.RENDER_CHUNK_SIZE; i < ic+Game.RENDER_CHUNK_SIZE; i++) {
			for (int j=jc-Game.RENDER_CHUNK_SIZE; j < jc+Game.RENDER_CHUNK_SIZE; j++) {
				if (i >= 0 && i < ceils.length && j >= 0 && j < ceils[0].length) {
					if (ceils[i][j] != null) {
						ceils[i][j].update(delta);
					}
				}
			}
		}
		
	}
	
	public void draw(DecalBatch dBatch, int ic, int jc) {
		for (int i=ic-Game.RENDER_CHUNK_SIZE; i < ic+Game.RENDER_CHUNK_SIZE; i++) {
			for (int j=jc-Game.RENDER_CHUNK_SIZE; j < jc+Game.RENDER_CHUNK_SIZE; j++) {
				if (i >= 0 && i < ceils.length && j >= 0 && j < ceils[0].length) {
					if (ceils[i][j] != null) {
						ceils[i][j].draw(dBatch);
					}
				}
			}
		}
		
	}

}
