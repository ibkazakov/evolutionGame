package com.mygdx.game.entities.active_entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.managers.BulletManager;
import com.mygdx.game.entities.Entity;

public abstract class ActiveEntity extends Entity {
    private static final float TRIBE_FACTOR = 2.0f;
    private static final float BULLET_DELAY = 0.5f;
    private static final int MIN_RADIUS = 20;

    private float bulletTimeStorage = 0.0f;
    private boolean bulletLock = false;

    private BulletManager bulletManager;

    //object creation elimination

    //method throwBullet
    private Vector2 tB_dir = new Vector2();
    private Vector2 tB_dir_copy = new Vector2();
    private Vector2 tB_bullet_position = new Vector2();
    private Vector2 tB_position_copy = new Vector2();


    public ActiveEntity(TextureRegion texture, Vector2 position, Vector2 velocity,
                        float angle, float angle_velocity, int radius, int score,
                        BulletManager bulletManager) {
        super(texture, position, velocity, angle, angle_velocity, radius, score);
        this.bulletManager = bulletManager;
    }

    @Override
    public void update(float dt) {
        if (active) {
            //viscouis friction. Force(= ma) =  - factor * velocity
            velocity.x -= velocity.x * TRIBE_FACTOR * dt;
            velocity.y -= velocity.y * TRIBE_FACTOR * dt;
            angle_velocity -= angle_velocity * TRIBE_FACTOR * dt;

            //bullet renew
            if (bulletLock) {
                bulletTimeStorage += dt;
                if (bulletTimeStorage > BULLET_DELAY) {
                    bulletLock = false;
                    bulletTimeStorage = 0.0f;
                }
            }

            if (radius > MIN_RADIUS) {
                radius = (int) Math.sqrt(score);
            } else {
                beEaten();
            }

            super.update(dt);
        }
    }


    public void eat(Entity anotherEntity) {
        anotherEntity.beEaten();
        score += anotherEntity.getScore();
    }

    public void throwBullet() {
        if (active && !bulletLock) {
            bulletLock = true;
            tB_dir.set(MathUtils.cosDeg(angle), MathUtils.sinDeg(angle));
            tB_dir_copy.set(tB_dir);
            tB_position_copy.set(position);
            tB_bullet_position = tB_position_copy.add(tB_dir_copy.scl(radius));

            // bulletsLink.add(new Bullet(tB_bullet_position, tB_dir, score / 3));
            bulletManager.throwBullet(tB_bullet_position, tB_dir, score / 3);
        }
    }

    protected abstract void respawn();

}
