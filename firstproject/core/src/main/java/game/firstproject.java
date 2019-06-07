package game;

import com.badlogic.gdx.Game;

import game.screens.MenuScreen;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class firstproject extends Game {

	@Override
	public void create() {
		 setScreen(new MenuScreen());
	}
	
	public void render() {
		super.render();
	}
	
	public void dispose() {
		super.dispose();
	}
}