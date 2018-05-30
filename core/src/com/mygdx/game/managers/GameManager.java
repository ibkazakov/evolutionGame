package com.mygdx.game.managers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.Food;
import com.mygdx.game.entities.active_entities.ActiveEntity;
import com.mygdx.game.entities.active_entities.Enemy;
import com.mygdx.game.entities.active_entities.Hero;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameManager {
    private Hero hero;
    private Enemy enemy;

    private static final int FOODS_Q = 10;

    // regular entities are all entities except bullets
    private List<Entity> regularEntities = new ArrayList<Entity>();

    private List<Entity> foods = new ArrayList<Entity>();
    private BulletManager bulletManager;
    private EatingManager eatingManager;

    public GameManager() {
        bulletManager = new BulletManager(100);
        Hero hero = new Hero(bulletManager);
        Enemy enemy = new Enemy(hero, bulletManager);
        regularEntities.add(hero);
        regularEntities.add(enemy);
        eatingManager = new EatingManager();
        eatingManager.addToCanEat(Arrays.asList(new ActiveEntity[]{hero, enemy}));
        eatingManager.addToCanBeEaten(Arrays.asList(new Entity[]{hero, enemy}));
        for(int i = 0; i < FOODS_Q; i++) {
            Food food = new Food();
            regularEntities.add(food);
            foods.add(food);
        }
        eatingManager.addToCanBeEaten(foods);
        eatingManager.addToCanBeEaten(bulletManager.getActual_bullets());
    }

    public void render(SpriteBatch batch) {
        for(int i = 0; i < regularEntities.size(); i++) {
            regularEntities.get(i).render(batch);
        }
        bulletManager.render(batch);
    }

    public void update(float dt) {
        for(int i = 0; i < regularEntities.size(); i++) {
            regularEntities.get(i).update(dt);
        }
        bulletManager.update(dt);
        eatingManager.eating();
    }

    public void dispose() {
        bulletManager.dispose();
    }
}
