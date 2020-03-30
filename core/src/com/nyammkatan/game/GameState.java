package com.nyammkatan.game;

public abstract class GameState {
	
	public GameState() {
		
		
	}
	
	public abstract void update(float delta);
	
	public abstract void draw();
	
	public abstract void gui();

}
