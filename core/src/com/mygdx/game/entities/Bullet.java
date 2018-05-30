package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Bullet extends Entity {
    private static final float BULLET_VELOCITY = 400.0f;
    private static final float BULLET_ANGLE_VELOCITY = 500.0f;

    private static final float LIMIT_LIFETIME = 2.0f;
    private float lifetimeStorage = 0.0f;

    private static Texture bulletTexture = new Texture("bullet.png");

    // tmp
    private static Vector2 trashVector = new Vector2(0.0f, 0.0f);

    public Bullet() {
        super(bulletTexture, trashVector, trashVector, 0, 0, 0, 0);
        active = false;
    }

    public void initParameters(Vector2 position, Vector2 dir, int score) {
        this.position.set(position);
        this.velocity.set(dir);
        velocity.nor();
        velocity.scl(BULLET_VELOCITY);
        this.angle = MathUtils.random(360);
        this.angle_velocity = BULLET_ANGLE_VELOCITY;
        this.radius = 20;
        this.score = -score;
        active = true;
    }

    public Bullet(Vector2 position, Vector2 dir, int score) {
        super(bulletTexture,
                position,
                dir,
                MathUtils.random(360),
                BULLET_ANGLE_VELOCITY,
                20,
                -score);
        velocity.nor();
        velocity.scl(BULLET_VELOCITY);
    }

    public void beEaten() {
        active = false;
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        lifetimeStorage += dt;
        if (lifetimeStorage > LIMIT_LIFETIME) {
            active = false;
        }
    }
}
