package game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

import game.entities.Direction;
import game.entities.Objects;
import game.entities.Player;
import game.physics.ContactHandler;

public class PlayScreen implements Screen{
	
	public static final float V_WIDTH = 50;
	public static final float V_HEIGHT = V_WIDTH*((float)Gdx.graphics.getWidth()/Gdx.graphics.getHeight());
	public static final float PPM = 8;
	
	
	private Box2DDebugRenderer debug;
	private OrthographicCamera cam;
	private OrthogonalTiledMapRenderer renderer;
	private Player player;
	private TiledMap map;
	private World world;
	private Objects mapObjs;
	private Music bgm;
	private boolean initialized = false;
	private boolean changeMap = false;
	private boolean revertMap = false;
	boolean removeObj = false;
	public Body bodyToRemove = null;
	

	@Override
	public void show() {
		
		if(!initialized) {
			initialized = true;
			initialize();
		}
		
	}
	
	public void mapChange(boolean change) {
		changeMap = true;
	}
	
	public void revertMap(boolean change) {
		revertMap = true;
	}
	
	private void initialize() {
		
		bgm = Gdx.audio.newMusic(Gdx.files.internal("Netherplace.mp3"));
		
		bgm.setLooping(true);
		
		world = new World(new Vector2(0f,0f), false);
		
		world.setContactListener(new ContactHandler(this));
		
		player = new Player();
		
		player.createBody(world);
		
		cam = new OrthographicCamera(V_WIDTH, V_HEIGHT);
		
		map = new TmxMapLoader().load("map.tmx");
		
		mapObjs = new Objects(map,world);
		
		mapObjs.createBody(3);
		
		renderer = new OrthogonalTiledMapRenderer(map, 1f/PPM);

//		debug = new Box2DDebugRenderer();
		

		
	}
	
	public void changeMap() {
		
		renderer.getMap().dispose();
		
		bgm.dispose();
		if(changeMap) {
			map = new TmxMapLoader().load("indoorhouse.tmx");
			bgm = Gdx.audio.newMusic(Gdx.files.internal("house.mp3"));
		}
		else if(revertMap) {
			map = new TmxMapLoader().load("map.tmx");
			bgm = Gdx.audio.newMusic(Gdx.files.internal("Netherplace.mp3"));
		}
		
		mapObjs.updateMap(map);
		renderer.setMap(map);
	}
	
	public TiledMap getMap() {
		return map;
	}
	
	
	private void update(float dt) {
		world.step(1/60f, 6, 2);
		
		if(changeMap) {
			changeMap();
			mapObjs.destroyBodies(world);
			mapObjs.createBody(3);
			player.setPlayer(V_WIDTH/2 + 3, V_HEIGHT/2 + 3);
			changeMap = false;
		}
		if(revertMap) {
			changeMap();
			mapObjs.destroyBodies(world);
			mapObjs.createBody(3);
			player.setPlayer(mapObjs.portalPos().x,mapObjs.portalPos().y);
			revertMap = false;
		}
		if(bodyToRemove != null) {
			removeObj(bodyToRemove);
			bodyToRemove = null;
			player.printInv();
		}
		
		updateDirection();
		
		player.move();
		
		
	}
	
	public void addItem(String itemName) {
		player.addItem(itemName);
	}
	
	public void updateDirection() {
		if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
			player.setDirection(Direction.UP);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			player.setDirection(Direction.DOWN);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			player.setDirection(Direction.LEFT);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			player.setDirection(Direction.RIGHT);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && Gdx.input.isKeyPressed(Input.Keys.UP)) {
			player.setDirection(Direction.UPRIGHT);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.UP) && Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			player.setDirection(Direction.UPLEFT);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN) && Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			player.setDirection(Direction.DOWNLEFT);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN) && Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			player.setDirection(Direction.DOWNRIGHT);
		}
		if(!Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)) {
			player.setDirection(Direction.NONE);
		}
	}

	@Override
	public void render(float delta) {
		update(delta);
		bgm.play();
		Gdx.gl20.glClearColor(0, 0, 0, 1);
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		player.centerCamera(cam); 
		renderer.setView(cam);
		renderer.render();
//		debug.render(world, cam.combined);
		renderer.getBatch().begin();
		player.printInv();

//
//		
//		for(Enemies e : enemigos) {
//			e.move();
//			e.draw(renderer.getBatch());
//		}

		player.draw(renderer.getBatch());
		renderer.getBatch().end();

	}
	
	public void removeObj(Body body) {
		mapObjs.destroyBody(world, body);
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		renderer.dispose();
		// TODO Auto-generated method stub
		
	}

}
