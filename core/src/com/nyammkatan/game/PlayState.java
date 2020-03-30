package com.nyammkatan.game;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.nyamkatan.game.graphics.Decals;
import com.nyamkatan.game.graphics.Shaders;
import com.nyamkatan.game.graphics.UserMap;
import com.nyammkatan.game.gameobjects.CameraPlayer;
import com.nyammkatan.game.gameobjects.GameObject;
import com.nyammkatan.game.gameobjects.ObjectsUniformSetter;
import com.nyammkatan.game.map.BlockArray;
import com.nyammkatan.game.map.CeilArray;
import com.nyammkatan.game.map.FloorArray;
import com.nyammkatan.game.map.Map;

public class PlayState extends GameState {
	
	//Camera
	public PerspectiveCamera cam;
	public static int camx, camy, camz;
	//public FirstPersonCameraController fpcc;
	public CameraPlayer cPlayer;
	public boolean isMoving = false;
	//decal
	public ObjectsUniformSetter objectsUniformSetter;
	
	//FrameBuffer
	public FrameBuffer buffer;
	public TextureRegion bufferRegion;
	public SpriteBatch sBatch;
	
	FloorArray floorArray;
	BlockArray blockArray;
	CeilArray ceilArray;
	
	//game logic
	public Map gameMap;
	public UserMap userMap;
	
	public HashMap<Integer, GameObject> objects;
	
	public PlayState() {
		//camera
		cam = new PerspectiveCamera(67, Gdx.graphics.getWidth()/Game.PIXEL_FACTOR,
										Gdx.graphics.getHeight()/Game.PIXEL_FACTOR);
		cam.position.set(1f, 0f, 1f);
		cam.direction.set(1, 0, 0);
		cam.near = 0.1f;
		cam.far = 300f;
		cam.update();
		Game.gameCamera = cam;
		
		objectsUniformSetter = new ObjectsUniformSetter();
		//dBatchObjects = new DecalBatch(new DecalGroupStrategy(cam, Shaders.objectShader, objectsUniformSetter));
		Decals.initDecalBatch(Shaders.blockShader, objectsUniformSetter);
		//buffer
		buffer = new FrameBuffer(Format.RGBA8888, (int)(Gdx.graphics.getWidth()/Game.PIXEL_FACTOR),
												(int)(Gdx.graphics.getHeight()/Game.PIXEL_FACTOR), true);
		buffer.getColorBufferTexture().setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		bufferRegion = new TextureRegion(buffer.getColorBufferTexture());
		bufferRegion.flip(false, true);
		sBatch = new SpriteBatch();
		
		gameMap = new Map(Game.gameRandom);
		userMap = new UserMap(gameMap.array);
		floorArray = new FloorArray(gameMap);
		blockArray = new BlockArray(gameMap);
		ceilArray = new CeilArray(gameMap);
		
		objects = new HashMap<Integer, GameObject>();
		
		cPlayer = new CameraPlayer(cam, gameMap);
		cam.position.x = gameMap.rooms.get(0).x+1;
		cam.position.z = gameMap.rooms.get(0).y+1;
		
	}

	@Override
	public void update(float delta) {
		floorArray.update(delta, camx, camz);
		ceilArray.update(delta, camx, camz);
		blockArray.update(delta, camx, camz);
		
		//fpcc.update();
		cPlayer.update(delta);
		camx = (int) cam.position.x;
		camy = (int) cam.position.y;
		camz = (int) cam.position.z;
		
		for (GameObject go: this.objects.values()) {
			go.update(delta, cam);
		}
		
		objectsUniformSetter.update(delta, cam);
		
		Game.time += delta;
		
	}

	@Override
	public void draw() {
		buffer.begin();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glViewport(0, 0, (int)(Gdx.graphics.getWidth()/Game.PIXEL_FACTOR),
								(int)(Gdx.graphics.getHeight()/Game.PIXEL_FACTOR));
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		Gdx.gl.glEnable(Gdx.gl.GL_DEPTH_TEST);
		
		cam.update();
		
		floorArray.draw(Decals.getBatch(), camz, camx);
		ceilArray.draw(Decals.getBatch(), camz, camx);
		blockArray.draw(Decals.getBatch(), camz, camx);
		
		for (GameObject go: this.objects.values()) {
			go.draw(Decals.getBatch(), cam);
		}
		
		//
		Decals.flush();
		//Gdx.gl20.glDepthMask(true);
		
		buffer.end();
		
		sBatch.begin();
		sBatch.draw(this.bufferRegion, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		//userMap.draw(sBatch, camx, camz);
		sBatch.end();
		
	}

	@Override
	public void gui() {
		
		
	}

}
