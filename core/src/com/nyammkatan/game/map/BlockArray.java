package com.nyammkatan.game.map;

import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.nyammkatan.game.Game;

public class BlockArray {
	
	public Block[][] blocks;
	
	public BlockArray(Map gameMap) {
		int[][] map = gameMap.array;
		blocks = new Block[map.length][map[0].length];
		for (int i=0; i < blocks.length; i++) {
			for (int j=0; j < blocks[i].length; j++) {
				if (map[i][j] == 0) {
					if (i-1 >= 0) {
						if (map[i-1][j] == 1 || map[i-1][j] == 2)
							blocks[i][j] = new Block(gameMap.typeArray[i-1][j], j, 0, i);
					}
					if (i+1 < blocks.length) {
						if (map[i+1][j] == 1 || map[i+1][j] == 2)
							blocks[i][j] = new Block(gameMap.typeArray[i+1][j], j, 0, i);
					}
					if (j-1 >= 0) {
						if (map[i][j-1] == 1 || map[i][j-1] == 2)
							blocks[i][j] = new Block(gameMap.typeArray[i][j-1], j, 0, i);
					}
					if (j+1 < blocks[0].length) {
						if (map[i][j+1] == 1 || map[i][j+1] == 2)
							blocks[i][j] = new Block(gameMap.typeArray[i][j+1], j, 0, i);
					}
				}
			}
		}
		
	}
	
	public void update(float delta, int ic, int jc) {
		for (int i=ic-Game.RENDER_CHUNK_SIZE; i < ic+Game.RENDER_CHUNK_SIZE; i++) {
			for (int j=jc-Game.RENDER_CHUNK_SIZE; j < jc+Game.RENDER_CHUNK_SIZE; j++) {
				if (i >= 0 && i < blocks.length && j >= 0 && j < blocks[0].length) {
					if (blocks[i][j] != null) {
						blocks[i][j].update(delta);
					}
				}
			}
		}
		
	}
	
	public void draw(DecalBatch dBatch, int ic, int jc) {
		for (int i=ic-Game.RENDER_CHUNK_SIZE; i < ic+Game.RENDER_CHUNK_SIZE; i++) {
			for (int j=jc-Game.RENDER_CHUNK_SIZE; j < jc+Game.RENDER_CHUNK_SIZE; j++) {
				if (i >= 0 && i < blocks.length && j >= 0 && j < blocks[0].length) {
					if (blocks[i][j] != null) {
						blocks[i][j].draw(dBatch);	
					}
				}
			}
		}
		
	}

}
