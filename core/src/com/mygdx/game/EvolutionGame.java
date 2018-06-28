package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.managers.GameScreen;
import com.mygdx.game.managers.ScreenManager;


public class EvolutionGame extends Game {
	private SpriteBatch batch;
	// private GameScreen gameScreen;

	/*
	@Override
	public void create() {
		batch = new SpriteBatch();
		gameScreen = new GameScreen(batch);
		setScreen(gameScreen);
	}

	@Override
	public void render() {
		float dt = Gdx.graphics.getDeltaTime();
		gameScreen.render(dt);
	}
	
	@Override
	public void dispose() {
		batch.dispose();
		gameScreen.dispose();
	}
	*/

	@Override
	public void create() {
		batch = new SpriteBatch();
		ScreenManager.getInstance().init(this, batch);
		ScreenManager.getInstance().changeScreen(ScreenManager.ScreenType.MENU);
	}

	@Override
	public void render() {
		float dt = Gdx.graphics.getDeltaTime();
		getScreen().render(dt);
	}

	@Override
	public void dispose() {
		batch.dispose();
	}

}
