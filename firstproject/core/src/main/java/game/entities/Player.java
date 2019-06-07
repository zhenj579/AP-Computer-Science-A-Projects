package game.entities;

import static game.physics.PhysicsFilter.CATEGORY_PLAYER;
import static game.screens.PlayScreen.V_HEIGHT;
import static game.screens.PlayScreen.V_WIDTH;

import javax.annotation.Resources;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

import game.screens.PlayScreen;

public class Player {
	
	
	private Direction direction = Direction.RIGHT;
	private Body pBody;
	private Vector2 pos;
	private Vector2 spd;
	private ObjectMap<Direction,Animation<TextureRegion>> animations;
	private Sprite sprite;
	private Texture sheet;
	private float stateTime = 0f;
	private Array<String> inv;
	
	public Player() {
		inv = new Array<String>();
		sheet = new Texture("testsprite.png");
		animations = new ObjectMap<Direction,Animation<TextureRegion>>();
		pos = new Vector2(0,0);
		spd = new Vector2();
		createAnimations();
		sprite = new Sprite(animations.get(Direction.RIGHT).getKeyFrame(stateTime, true));

	}

	public void draw(Batch batch) {
		sprite.setRegion(animations.get(this.direction).getKeyFrame(stateTime, true));
		batch.draw(sprite, pos.x - ((sprite.getWidth()/PlayScreen.PPM)/2), pos.y - ((sprite.getHeight()/PlayScreen.PPM)/2), sprite.getWidth()/PlayScreen.PPM, sprite.getHeight()/PlayScreen.PPM);
	}
	
	public void createBody(World world) {
		Body body;
		float spriteWidth = sprite.getWidth()/PlayScreen.PPM;
		float spriteHeight = sprite.getHeight()/PlayScreen.PPM;
		PolygonShape shape = new PolygonShape();
		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();
		bdef.type = BodyDef.BodyType.DynamicBody;
		bdef.position.set(pos.x + spriteWidth, pos.y + spriteHeight);
		bdef.fixedRotation = true;
		body = world.createBody(bdef);
		body.setUserData(this);
		shape.setAsBox(spriteWidth/2, spriteHeight/2);
		fdef.shape = shape;
		fdef.filter.categoryBits = CATEGORY_PLAYER;
		body.createFixture(fdef);
		
		shape.dispose();
		
		pBody = body;
	}
	
	public void setPos(Vector2 pos) {
		this.pos = pos;
	}
	
	public void destroyBody(World world) {
		world.destroyBody(pBody);
	}
	
	public void setPlayer(float x, float y) {
		pBody.setTransform(x, y, 0);
	}
	
    public void move() {
        
        spd.set(20,20);
       
        if(direction == Direction.DOWN) {
        	stateTime+= Gdx.graphics.getDeltaTime();
            spd.setAngle(270);
            pBody.setLinearVelocity(spd);
        }
        if(direction == Direction.UP) {
        	stateTime+= Gdx.graphics.getDeltaTime();
            spd.setAngle(90);
            pBody.setLinearVelocity(spd);
        }
        if(direction == Direction.LEFT) {
        	stateTime+= Gdx.graphics.getDeltaTime();
            spd.setAngle(180);
            pBody.setLinearVelocity(spd);
        }
        if(direction == Direction.RIGHT) {
        	stateTime+= Gdx.graphics.getDeltaTime();
            spd.setAngle(0);
            pBody.setLinearVelocity(spd);
        }
        if(direction == Direction.UPLEFT) {
        	stateTime+= Gdx.graphics.getDeltaTime();
            spd.setAngle(135);
            pBody.setLinearVelocity(spd);
        }
        if(direction == Direction.UPRIGHT) {
        	stateTime+= Gdx.graphics.getDeltaTime();
            spd.setAngle(45);
            pBody.setLinearVelocity(spd);
        }
        if(direction == Direction.DOWNRIGHT) {
        	stateTime+= Gdx.graphics.getDeltaTime();
            spd.setAngle(-45);
            pBody.setLinearVelocity(spd);
        }
        if(direction == Direction.DOWNLEFT) {
        	stateTime+= Gdx.graphics.getDeltaTime();
            spd.setAngle(-135);
            pBody.setLinearVelocity(spd);
        }
        if(direction == Direction.NONE) {
            spd.setAngle(0);
            pBody.setLinearVelocity(0,0);
        }

        pos = pBody.getPosition();
  
    }
    
    private void createAnimations() {
    	TextureRegion[] none = new TextureRegion[1];
    	TextureRegion[] up = new TextureRegion[4];
    	TextureRegion[] down = new TextureRegion[4];
    	TextureRegion[] left = new TextureRegion[4];
    	TextureRegion[] right = new TextureRegion[4];
    	TextureRegion[] upright = new TextureRegion[4];
    	TextureRegion[] upleft = new TextureRegion[4];
    	TextureRegion[] downright = new TextureRegion[4];
    	TextureRegion[] downleft = new TextureRegion[4];
    	TextureRegion[][] allFrames = TextureRegion.split(sheet, sheet.getWidth()/4, sheet.getHeight()/8);
    	
    	none[0] = allFrames[0][0];
    	
    	for(int i = 0; i < 4; i++) {
    		right[i] = allFrames[0][i];
    		upright[i] = allFrames[1][i];
    		up[i] = allFrames[2][i];
    		upleft[i] = allFrames[3][i];
    		left[i] = allFrames[4][i];
    		downleft[i] = allFrames[5][i];
    		down[i] = allFrames[6][i];
    		downright[i] = allFrames[7][i];
    	}
    	
    	animations.put(Direction.UP, new Animation<TextureRegion>(1/8f, up));
    	animations.put(Direction.DOWN, new Animation<TextureRegion>(1/8f, down));
    	animations.put(Direction.LEFT, new Animation<TextureRegion>(1/8f, left));
    	animations.put(Direction.RIGHT, new Animation<TextureRegion>(1/8f, right));
    	animations.put(Direction.UPRIGHT, new Animation<TextureRegion>(1/8f, upright));
    	animations.put(Direction.UPLEFT, new Animation<TextureRegion>(1/8f, upleft));
    	animations.put(Direction.DOWNRIGHT, new Animation<TextureRegion>(1/8f, downright));
    	animations.put(Direction.DOWNLEFT, new Animation<TextureRegion>(1/8f, downleft));
    	animations.put(Direction.NONE, new Animation<TextureRegion>(1/8f, none));
    	
    	
    }
    
    public void addItem(String itemName) {
    	inv.add(itemName);
    }
    
    public void printInv() {
    	for(String i : inv) {
    		System.out.println(i);
    		System.out.println("-------");
    	}
    }
	
	
	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	
	public void centerCamera(OrthographicCamera cam) {

		cam.position.x = V_WIDTH/2;
		cam.position.y = V_HEIGHT/2;

		cam.update();
	}
	


	
	

}
