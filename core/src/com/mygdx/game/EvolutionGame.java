package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class EvolutionGame extends ApplicationAdapter {
	SpriteBatch batch;

	List<Entity> allEntities = new ArrayList<Entity>();
	List<ActiveEntity> activeEntities = new ArrayList<ActiveEntity>();
	List<Bullet> bullets = new LinkedList<Bullet>();

	public int bulletDeleted = 0;
	
	@Override
	public void create() {
		batch = new SpriteBatch();
		Hero hero = new Hero(bullets);
		Enemy enemy = new Enemy(bullets, hero);
		allEntities.add(hero);
		allEntities.add(enemy);
		activeEntities.add(hero);
		activeEntities.add(enemy);
		for(int i = 0; i < 10; i++) {
			allEntities.add(new Food());
		}
	}

	@Override
	public void render() {
		float dt = Gdx.graphics.getDeltaTime();
		update(dt);
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		for(int i = 0; i < allEntities.size(); i++) {
			allEntities.get(i).render(batch);
		}
		for(Bullet bullet : bullets) {
			bullet.render(batch);
		}
		batch.end();
	}
	
	@Override
	public void dispose() {
		batch.dispose();
	}

	public void update(float dt) {
		for(int i = 0; i < allEntities.size(); i++) {
			allEntities.get(i).update(dt);
		}
		for(Bullet bullet : bullets) {
			bullet.update(dt);
		}

		//eating
		for(ActiveEntity entity : activeEntities) {
			for(int i = 0; i < allEntities.size(); i++) {
				entity.tryEat(allEntities.get(i));
			}
			for(Bullet bullet : bullets) {
				entity.tryEat(bullet);
			}
		}

		freeBullets();
	}

	// GC for bullets
	private void freeBullets() {
		Iterator<Bullet> iterator = bullets.iterator();
		while (iterator.hasNext()) {
			Bullet bullet = iterator.next();
			if (!bullet.isActive()) {
				iterator.remove();
				bulletDeleted++;
			}
		}
		if (bulletDeleted >= 100) {
			System.gc();
			bulletDeleted = 0;
		}

	}

}
