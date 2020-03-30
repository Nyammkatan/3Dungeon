package com.nyammkatan.game.art;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class APlayer {
	
	public Animation anim;
	public float timer = 0;
	public int currentIndex = 0;
	
	public APlayer(Animation anim) {
		this.anim = anim;	
		
	}
	
	public void start() {
		this.currentIndex = 0;
		
	}
	
	public void update(float delta) {
		timer += delta;
		if (timer >= anim.time) {
			timer -= anim.time;
			this.currentIndex++;
			if (this.currentIndex == anim.regions.size()) {
				this.currentIndex = 0;
				
			}
		}
		
	}
	
	public TextureRegion getCurrentFrame() {
		return anim.regions.get(currentIndex);
		
	}

}
