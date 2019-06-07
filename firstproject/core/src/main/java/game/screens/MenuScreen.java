package game.screens;

import static game.screens.PlayScreen.V_HEIGHT;
import static game.screens.PlayScreen.V_WIDTH;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MenuScreen implements Screen{
	
	protected Stage stage;
	private TextureAtlas atlas;
	protected Skin skin;
	private OrthographicCamera cam;
	private Viewport viewport;
	private SpriteBatch batch;
	
	public MenuScreen() {
		atlas = new TextureAtlas("uiskin.atlas");
		skin = new Skin(Gdx.files.internal("uiskin.json"), atlas);
		batch = new SpriteBatch();
		cam = new OrthographicCamera();
		viewport = new FitViewport((int)(V_WIDTH*1.7),(int)(V_HEIGHT*1.7), cam);
		stage = new Stage(viewport, batch);
	}

	@Override
	public void show() {
		
		Gdx.input.setInputProcessor(stage);
		
		Table mainTable = new Table();
		mainTable.setFillParent(true);
		mainTable.top();
		
		TextButton playButton = new TextButton("Play", skin);
		TextButton exitButton = new TextButton("Exit", skin);
		
		playButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				((Game)Gdx.app.getApplicationListener()).setScreen(new PlayScreen());
				stage.dispose();
			}
		});
		
		exitButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.exit();
			}
		});
		
		mainTable.add(playButton);
		mainTable.row();
		mainTable.add(exitButton);
		
		stage.addActor(mainTable);
		
		
		
	}

	@Override
	public void render(float delta) {
		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act();
		stage.draw();
		
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void dispose() {
		
	}

}
