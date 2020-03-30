package com.nyammkatan.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.nyammkatan.game.Game;
import com.nyammkatan.game.IConfig;

public class DesktopLauncher implements IConfig {
	
	public static LwjglApplicationConfiguration config;
	
	public static void main (String[] arg) {
		config = new LwjglApplicationConfiguration();
		config.width = 960;
		config.height = 540;
		config.vSyncEnabled = true;
		config.fullscreen = false;
		new LwjglApplication(new Game(new DesktopLauncher()), config);
	}

	@Override
	public int getX() {
		return config.x;
	}

	@Override
	public int getY() {
		return config.y;
	}
}
