package com.mygdx.game.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MiniMap;
import com.mygdx.game.WorldState;
import com.mygdx.game.entities.Bullet;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.Food;
import com.mygdx.game.entities.active_entities.ActiveEntity;
import com.mygdx.game.entities.active_entities.Enemy;
import com.mygdx.game.entities.active_entities.Hero;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameScreen implements Screen {
    private Hero hero;
    private List<Entity> enemyList = new ArrayList<Entity>();
    private SpriteBatch batch;


    private Viewport viewport;
    private Camera camera;
    private MiniMap miniMap;
    private Camera windowCamera;
    private boolean paused = false;
    private BitmapFont font;

    // private Music music;
    // private Sound bulletSound;

    public GameScreen(SpriteBatch batch) {
        this.batch = batch;
    }

    public Viewport getViewport() {
        return viewport;
    }

    private static final int FOODS_Q = 1000;
    private static final int ENEMIES_Q = 50;

    // regular entities are all entities except bullets
    private List<Entity> regularEntities = new ArrayList<Entity>();

    private List<Entity> foods = new ArrayList<Entity>();
    private BulletManager bulletManager;
    private EatingManager eatingManager;

    @Override
    public void show() {
        bulletManager = new BulletManager(100, this);
        font = Assets.getInstance().getAssetManager().get("gomarice48.ttf", BitmapFont.class);
        hero = new Hero(bulletManager);
        hero.setGs(this);
        for (int i = 0; i < ENEMIES_Q; i++) {
            Enemy enemy = new Enemy(hero, bulletManager);
            enemy.setGs(this);
            enemyList.add(enemy);
            regularEntities.add(enemy);
        }

        regularEntities.add(hero);
        eatingManager = new EatingManager();
        eatingManager.addToCanEat(Arrays.asList(new Entity[]{hero}));
        eatingManager.addToCanEat(enemyList);
        eatingManager.addToCanBeEaten(Arrays.asList(new Entity[]{hero}));
        eatingManager.addToCanBeEaten(enemyList);
        for (int i = 0; i < FOODS_Q; i++) {
            Food food = new Food();
            food.setGs(this);
            regularEntities.add(food);
            foods.add(food);
        }
        eatingManager.addToCanBeEaten(foods);
        eatingManager.addToCanBeEaten(bulletManager.getActual_bullets());
        miniMap = new MiniMap(this);

        /*
        music = Assets.getInstance().getAssetManager().get("music.wav", Music.class);
        music.setLooping(true);
        music.setVolume(0.05f);
        music.play();
        bulletSound = Assets.getInstance().getAssetManager().get("laser.wav", Sound.class);
        bulletManager.setBulletSound(bulletSound);
        */

        camera = new OrthographicCamera(WorldState.WORLD_WIGTH, WorldState.WORLD_HEIGHT);
        viewport = new FitViewport(1280, 720, camera);

        windowCamera = new OrthographicCamera(1280, 720);
        windowCamera.position.set(640, 360, 0);
        windowCamera.update();

    }


    public Hero getHero() {
        return hero;
    }

    public List<Entity> getEnemyList() {
        return enemyList;
    }

    public List<Entity> getFoods() {
        return foods;
    }

    @Override
    public void render(float delta) {
        if (!paused) {
            update(delta);
            if (Gdx.input.isKeyPressed(Input.Keys.P)) {
                paused = true;
            }
        } else {
            if (Gdx.input.isKeyPressed(Input.Keys.R)) {
                paused = false;
            }
        }
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        for (int i = 0; i < regularEntities.size(); i++) {
            regularEntities.get(i).render(batch);
        }
        bulletManager.render(batch);
        batch.end();

        batch.setProjectionMatrix(windowCamera.combined);
        batch.begin();
        font.draw(batch, "Score: " + hero.getScore(), 20, 700);
        miniMap.render(batch);
        batch.end();
    }


    public void update(float dt) {
        for (int i = 0; i < regularEntities.size(); i++) {
            regularEntities.get(i).update(dt);
        }
        bulletManager.update(dt);
        eatingManager.eating();

        camera.position.set(hero.getPosition().x, hero.getPosition().y, 0);
        camera.update();
    }


    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        viewport.apply();
    }

    // P is for pause, R is for resume
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

