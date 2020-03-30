package com.nyamkatan.game.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class UserMap {
	
	Pixmap pixmap;
	Texture texture;
	Sprite sprite;
	public boolean show = true;
	
	Pixmap uPixmap;
	Texture uTexture;
	Sprite user;
	
	public UserMap(int[][] map) {
		pixmap = new Pixmap(map[0].length, map.length, Pixmap.Format.RGBA4444);
		pixmap.setColor(new Color(0, 0, 0, 0));
		pixmap.fill();
		pixmap.setColor(new Color(1, 1, 1, 0.5f));
		for (int i=0; i < map.length; i++) {
			for (int j=0; j < map[i].length; j++) {
				if (map[i][j] > 0) {
					pixmap.fillRectangle(j, i, 1, 1);
				}
			}
		}
		texture = new Texture(pixmap);
		sprite = new Sprite(texture);
		sprite.setSize(map[0].length*2, map.length*2);
		
		uPixmap = new Pixmap(1, 1, Pixmap.Format.RGBA4444);
		uPixmap.setColor(new Color(1, 0, 0, 1));
		uPixmap.fill();
		uTexture = new Texture(uPixmap);
		user = new Sprite(uTexture);
		user.setSize(2, 2);
		
	}
	
	public void draw(SpriteBatch batch, int camx, int camz) {
		if (show) {
			sprite.setX(Gdx.graphics.getWidth()-sprite.getWidth()-4);
			sprite.setY(2);
			sprite.draw(batch);
			user.setX(Gdx.graphics.getWidth()-sprite.getWidth()-2+camx*2);
			user.setY(sprite.getHeight()-camz*2-1);
			user.draw(batch);
			
		}
		
	}
	
	public void dispose() {
		pixmap.dispose();
		texture.dispose();
		uPixmap.dispose();
		uTexture.dispose();
		
	}

}
