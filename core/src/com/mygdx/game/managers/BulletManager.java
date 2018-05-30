package com.mygdx.game.managers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.Bullet;
import com.mygdx.game.entities.Entity;

import java.util.*;

public class BulletManager {
    private Collection<Entity> actual_bullets = new LinkedHashSet<Entity>();
    private Collection<Entity> free_bullets = new LinkedHashSet<Entity>();

    private Collection<Entity> for_deletion = new LinkedHashSet<Entity>();


    private int capacity;

    public BulletManager(int capacity) {
       for(int i = 0; i < capacity; i++) {
           free_bullets.add(new Bullet());
       }
       this.capacity = capacity;
    }

    public Collection<Entity> getActual_bullets() {
        return actual_bullets;
    }


    public synchronized void throwBullet(Vector2 position, Vector2 dir, int score) {
        // we need more bullets if there are no free bullets
        if (free_bullets.size() == 0) {
            for(int i = 0; i < capacity; i++) {
                free_bullets.add(new Bullet());
            }
            capacity *= 2;
        }

        // normal work
        Bullet bullet = (Bullet) free_bullets.toArray()[0];
        bullet.initParameters(position, dir, score);
        free_bullets.remove(bullet);
        actual_bullets.add(bullet);
    }

    public void update(float dt) {
        for(Entity bullet : actual_bullets)  {
            bullet.update(dt);
            if (!bullet.isActive()) {
                for_deletion.add(bullet);
                // if we simply use actual_bullets.remove(bullet), we can get ConcurrentModificationException
            }
        }
        for(Entity bullet : for_deletion) {
            actual_bullets.remove(bullet);
            free_bullets.add(bullet);
        }
        for_deletion.clear();
    }

    public void render(SpriteBatch batch) {
        for(Entity bullet : actual_bullets) {
            bullet.render(batch);
        }
    }

    public void dispose() {
        actual_bullets.clear();
        free_bullets.clear();
    }
}
