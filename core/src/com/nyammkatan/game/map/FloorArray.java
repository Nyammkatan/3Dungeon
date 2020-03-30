package com.nyammkatan.game.map;

import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.nyammkatan.game.Game;

public class FloorArray {
	
	public Floor[][] floors;
	
	public FloorArray(Map gameMap) {
		int[][] map = gameMap.array;
		floors = new Floor[map.length][map[0].length];
		for (int i=0; i < floors.length; i++) {
			for (int j=0; j < floors[i].length; j++) {
				if (map[i][j] == 1 || map[i][j] == 2) {
					floors[i][j] = new Floor(gameMap.typeArray[i][j], j, 0, i);
				}
			}
		}
		
	}
	
	public void update(float delta, int ic, int jc) {
		for (int i=ic-Game.RENDER_CHUNK_SIZE; i < ic+Game.RENDER_CHUNK_SIZE; i++) {
			for (int j=jc-Game.RENDER_CHUNK_SIZE; j < jc+Game.RENDER_CHUNK_SIZE; j++) {
				if (i >= 0 && i < floors.length && j >= 0 && j < floors[0].length) {
					if (floors[i][j] != null) {
						floors[i][j].update(delta);
					}
				}
			}
		}
		
	}
	
	public void draw(DecalBatch dBatch, int ic, int jc) {
		for (int i=ic-Game.RENDER_CHUNK_SIZE; i < ic+Game.RENDER_CHUNK_SIZE; i++) {
			for (int j=jc-Game.RENDER_CHUNK_SIZE; j < jc+Game.RENDER_CHUNK_SIZE; j++) {
				if (i >= 0 && i < floors.length && j >= 0 && j < floors[0].length) {
					if (floors[i][j] != null) {
						floors[i][j].draw(dBatch);
					}
				}
			}
		}
		
	}

}
