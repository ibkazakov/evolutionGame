package com.mygdx.game.managers;

                                                                                                                                                                                                                                                                                                                                                import com.badlogic.gdx.audio.Sound;
                                                                                                                                                                                                                                                                                                                                                import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.Bullet;
import com.mygdx.game.entities.Entity;

import java.util.*;

public class BulletManager extends ObjectPool<Entity> {

    /*
    private Sound bulletSound;

    public void setBulletSound(Sound bulletSound) {
        this.bulletSound = bulletSound;
    }
    */

    public BulletManager(int capacity, GameScreen gs) {
        super(capacity, gs);
    }

    public Entity newObject() {
        return new Bullet();
    }


    public synchronized void throwBullet(Vector2 position, Vector2 dir, int score) {
        Bullet bullet = (Bullet) getActiveElement();
        bullet.initParameters(position, dir, score);
        // bulletSound.play();
    }

    public void update(float dt) {
        for (int i = 0; i < activeObjects.size(); i++) {
            activeObjects.get(i).update(dt);
        }
        checkPool();
    }

    public void render(SpriteBatch batch) {
        for (int i = 0; i < activeObjects.size(); i++) {
            activeObjects.get(i).render(batch);
        }
    }

    public void dispose() {
        activeObjects.clear();
        freeObjects.clear();
    }

    public List<Entity> getActual_bullets() {
        return activeObjects;
    }
}