package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.util.List;

public abstract class ActiveEntity extends Entity {
    private static final float TRIBE_FACTOR = 2.0f;
    private static final float BULLET_DELAY = 0.5f;
    private static final int MIN_RADIUS = 20;

    private float bulletTimeStorage = 0.0f;
    private boolean bulletLock = false;

    private List<Bullet> bulletsLink;

    public ActiveEntity(Texture texture, Vector2 position, Vector2 velocity,
                        float angle, float angle_velocity, int radius, int score, List<Bullet> bulletsLink) {
        super(texture, position, velocity, angle, angle_velocity, radius, score);
        this.bulletsLink = bulletsLink;
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

    public void tryEat(Entity anotherEntity) {
        if (active && anotherEntity.active && this != anotherEntity && (radius > anotherEntity.radius)) {
            float distance2 = position.cpy().sub(anotherEntity.position).len2();
            int radius_diff2 = (radius - anotherEntity.radius) * (radius - anotherEntity.radius);
            if (distance2 < radius_diff2) {
                // eating
                anotherEntity.active = false;
                score += anotherEntity.score;
            }
        }
    }

    public void throwBullet() {
        if (!bulletLock && active) {
            bulletLock = true;
            Vector2 dir = new Vector2(MathUtils.cosDeg(angle), MathUtils.sinDeg(angle));
            Vector2 bullet_position = position.cpy().add(dir.cpy().scl(radius));
            bulletsLink.add(new Bullet(bullet_position, dir, score / 3));
        }
    }

    protected abstract void respawn();

}
