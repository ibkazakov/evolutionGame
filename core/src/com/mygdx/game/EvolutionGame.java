package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.Food;
import com.mygdx.game.entities.active_entities.ActiveEntity;
import com.mygdx.game.entities.active_entities.Enemy;
import com.mygdx.game.entities.active_entities.Hero;
import com.mygdx.game.managers.BulletManager;
import com.mygdx.game.managers.EatingManager;
import com.mygdx.game.managers.GameManager;

import java.util.*;

public class EvolutionGame extends ApplicationAdapter {
	SpriteBatch batch;

	GameManager gameManager;
	
	@Override
	public void create() {
		batch = new SpriteBatch();
		gameManager = new GameManager();
	}

	@Override
	public void render() {
		float dt = Gdx.graphics.getDeltaTime();
		update(dt);
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		gameManager.render(batch);
		batch.end();
	}
	
	@Override
	public void dispose() {
		batch.dispose();
		gameManager.dispose();
	}

	public void update(float dt) {
		gameManager.update(dt);
	}

}
