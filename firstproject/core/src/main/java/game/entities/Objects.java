package game.entities;

import static game.physics.PhysicsFilter.CATEGORY_OBSTACLE;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import game.screens.PlayScreen;

import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;


public class Objects{
	
	private TiledMap map;
	private World world;
	private Vector2 portalPos;
	private List<Body> bodies;
	
	
	
	public Objects(TiledMap map, World world) {
		bodies = new ArrayList<Body>();
		this.map = map;
		this.world = world;
	}
	
	public void updateMap(TiledMap map) {
		this.map = map;
	}
	
	public void createBody(int layer) {
		
		Body objBody;
		BodyDef objDef = new BodyDef();
		FixtureDef fdef = new FixtureDef();
		objDef.type = BodyType.StaticBody;
		PolygonShape shape = new PolygonShape();
		for(MapObject object : map.getLayers().get(layer).getObjects().getByType(RectangleMapObject.class)) {
			
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			
			objDef.position.set((rect.getX()/PlayScreen.PPM) + (rect.getWidth()/PlayScreen.PPM)/2, (rect.getY()/PlayScreen.PPM) + (rect.getHeight()/PlayScreen.PPM)/2);
			
			objBody = world.createBody(objDef);
			
			if(!(object.getName() == null) && object.getName().equals("portal")) {
				objBody.setUserData("portal");
				portalPos = new Vector2((rect.getX()/PlayScreen.PPM) - (rect.getWidth()/PlayScreen.PPM/2) + 5, (rect.getY()/PlayScreen.PPM) - (rect.getHeight()/PlayScreen.PPM/2) - 1);
			}
			else if(!(object.getName() == null) && object.getName().equals("portalBack")) 
				objBody.setUserData("portalBack");
			else if(!(object.getName() == null) && object.getName().equals("tree"))
				objBody.setUserData("tree");
			else if(!(object.getName() == null) && object.getName().equals("rock"))
				objBody.setUserData("rock");
			else
				objBody.setUserData(this);
			
			shape.setAsBox((rect.getWidth()/PlayScreen.PPM)/2, (rect.getHeight()/PlayScreen.PPM)/2);
			
			fdef.shape = shape;
			
			fdef.filter.categoryBits = CATEGORY_OBSTACLE;
			
			objBody.createFixture(shape,1.0f);
			
			bodies.add(objBody);
				
		}
	}
	
	public Vector2 portalPos() {
		return portalPos;
	}
	public void destroyBodies(World world) {
		List<Body> destroy = new ArrayList<Body>();
		for(Body body : bodies) {
			destroy.add(body);
			world.destroyBody(body);
		}
		bodies.removeAll(destroy);
	}
	
	public void destroyBody(World world, Body body) {
		bodies.remove(body);
		world.destroyBody(body);
	}
	
	

}
