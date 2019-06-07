package game.entities;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

import game.screens.PlayScreen;

public class Enemies {
	
	private Texture texture;
	private Vector2 pos;
	private Random r;
	
	public Enemies() {
		texture = new Texture("enemy.png");
		r = new Random();
		pos = new Vector2();
		pos.x = (r.nextFloat() * (PlayScreen.V_WIDTH - texture.getWidth()/PlayScreen.PPM) - texture.getWidth()/PlayScreen.PPM/2)+ texture.getWidth()/PlayScreen.PPM/2;
		pos.y = (r.nextFloat() * (PlayScreen.V_HEIGHT - texture.getHeight()/PlayScreen.PPM) - texture.getHeight()/PlayScreen.PPM/2) + texture.getHeight()/PlayScreen.PPM/2;
	}
	
	public void move() {
		if(pos.x >= texture.getWidth()/PlayScreen.PPM && pos.x <= PlayScreen.V_WIDTH - texture.getWidth()/PlayScreen.PPM && pos.y >= texture.getHeight()/PlayScreen.PPM && pos.y <= PlayScreen.V_HEIGHT - texture.getHeight()/PlayScreen.PPM) {
			pos.x += 10 * Gdx.graphics.getDeltaTime();
			pos.y += 10 * Gdx.graphics.getDeltaTime();
		}
		else {
			pos.x -= 10 * Gdx.graphics.getDeltaTime();
			pos.y -= 5 * Gdx.graphics.getDeltaTime();
		}

	}
	
	public void draw(Batch batch) {
		batch.draw(texture, pos.x,pos.y, texture.getWidth()/PlayScreen.PPM, texture.getHeight()/PlayScreen.PPM);
	}
	

}
