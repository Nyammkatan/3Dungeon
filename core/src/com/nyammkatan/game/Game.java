package com.nyammkatan.game;

import java.awt.AWTException;
import java.awt.DisplayMode;
import java.awt.Robot;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.decals.CameraGroupStrategy;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.FirstPersonCameraController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.physics.bullet.collision.ClosestRayResultCallback;
import com.badlogic.gdx.physics.bullet.collision.RayResultCallback;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.nyamkatan.game.graphics.DecalGroupStrategy;
import com.nyamkatan.game.graphics.Decals;
import com.nyamkatan.game.graphics.Shaders;
import com.nyamkatan.game.graphics.UserMap;
import com.nyammkatan.game.art.Art;
import com.nyammkatan.game.gameobjects.CameraPlayer;
import com.nyammkatan.game.gameobjects.GameObject;
import com.nyammkatan.game.gameobjects.Mob;
import com.nyammkatan.game.gameobjects.ObjectsUniformSetter;
import com.nyammkatan.game.gameobjects.items.DamagePotion;
import com.nyammkatan.game.gameobjects.items.DefPotion;
import com.nyammkatan.game.gameobjects.items.HealthPotion;
import com.nyammkatan.game.gameobjects.items.ManaPotion;
import com.nyammkatan.game.gameobjects.mobs.Eye;
import com.nyammkatan.game.map.Block;
import com.nyammkatan.game.map.BlockArray;
import com.nyammkatan.game.map.Ceil;
import com.nyammkatan.game.map.CeilArray;
import com.nyammkatan.game.map.Floor;
import com.nyammkatan.game.map.FloorArray;
import com.nyammkatan.game.map.Map;
import com.nyammkatan.game.utils.RayHelper;

public class Game extends ApplicationAdapter implements InputProcessor {
	
	public static final float BLOCK_SIZE = 1.0f;
	public static final float PIXEL_FACTOR = 1f;
	public static final int RENDER_CHUNK_SIZE = 10;
	
	public static Random gameRandom;
	
	//Camera
	public PerspectiveCamera cam;
	public static Camera gameCamera;
	public int camx, camy, camz;
	//public FirstPersonCameraController fpcc;
	public CameraPlayer cPlayer;
	public boolean isMoving = false;
	//decal
	public ObjectsUniformSetter objectsUniformSetter;
	//UI
	public Stage stage;
	public Label label;
	public BitmapFont font;
	
	//FrameBuffer
	public FrameBuffer buffer;
	public TextureRegion bufferRegion;
	public SpriteBatch sBatch;
	
	IConfig iConfig;
	
	public Game(IConfig iConfig) {
		super();
		this.iConfig = iConfig;
		
	}
	
	FloorArray floorArray;
	BlockArray blockArray;
	CeilArray ceilArray;
	
	public HashMap<Integer, GameObject> objects;
	
	public static float time;
	
	//game logic
	public Map gameMap;
	public UserMap userMap;
	
	@Override
	public void create () {
		Gdx.input.setCursorCatched(true);
		//input
		InputMultiplexer multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(this);
		Gdx.input.setInputProcessor(multiplexer);
		//camera
		cam = new PerspectiveCamera(67, Gdx.graphics.getWidth()/PIXEL_FACTOR,
										Gdx.graphics.getHeight()/PIXEL_FACTOR);
		cam.position.set(1f, 0f, 1f);
		cam.direction.set(1, 0, 0);
		cam.near = 0.1f;
		cam.far = 300f;
		cam.update();
		Game.gameCamera = cam;
		//ui
		stage = new Stage();
		font = new BitmapFont();
		label = new Label("", new Label.LabelStyle(font, Color.WHITE));
		label.setX(10);
		label.setY(20);
		stage.addActor(label);
		//decals
		//dBatch = new DecalBatch(new DecalGroupStrategy(cam, Shaders.blockShader));
		objectsUniformSetter = new ObjectsUniformSetter();
		//dBatchObjects = new DecalBatch(new DecalGroupStrategy(cam, Shaders.objectShader, objectsUniformSetter));
		Decals.initDecalBatch(Shaders.blockShader, objectsUniformSetter);
		//buffer
		buffer = new FrameBuffer(Format.RGBA8888, (int)(Gdx.graphics.getWidth()/PIXEL_FACTOR),
												(int)(Gdx.graphics.getHeight()/PIXEL_FACTOR), true);
		buffer.getColorBufferTexture().setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		bufferRegion = new TextureRegion(buffer.getColorBufferTexture());
		bufferRegion.flip(false, true);
		sBatch = new SpriteBatch();
		
		gameRandom = new Random();
		initGame();
		
	}
	
	public void initGame() {
		gameMap = new Map(gameRandom);
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
	public void render () {
		update(Gdx.graphics.getDeltaTime());
		draw();
		gui();
		
	}
	
	public void gui() {
		label.setText("FPS: "+Gdx.graphics.getFramesPerSecond()+" xyz: "+camx+" "+camy+" "+camz);
		stage.draw();
		
	}
	
	
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
		time += delta;
		
	}

	public void draw(){
		buffer.begin();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glViewport(0, 0, (int)(Gdx.graphics.getWidth()/PIXEL_FACTOR),
								(int)(Gdx.graphics.getHeight()/PIXEL_FACTOR));
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
	public void dispose () {
		Decals.dispose();
		for (Texture t : Art.textures.values()) {
			t.dispose();
			
		}
		Shaders.blockShader.dispose();
		Shaders.objectShader.dispose();
		
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
	
}
