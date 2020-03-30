package com.nyammkatan.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

public class MenuState extends GameState {

	@Override
	public void update(float delta) {
		if (Gdx.input.isKeyPressed(Keys.UP)) {
			
		} else
		if (Gdx.input.isKeyPressed(Keys.DOWN)) {
			Game.setState(new PlayState());
		}
		
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void gui() {
		// TODO Auto-generated method stub
		
	}

}
