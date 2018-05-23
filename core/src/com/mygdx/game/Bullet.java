package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Bullet extends Entity {
    private static final float BULLET_VELOCITY = 400.0f;
    private static final float BULLET_ANGLE_VELOCITY = 500.0f;

    private static final float LIMIT_LIFETIME = 2.0f;
    private float lifetimeStorage = 0.0f;

    public Bullet(Vector2 position, Vector2 dir, int score) {
        super(new Texture("bullet.png"),
                position,
                dir.nor().scl(BULLET_VELOCITY),
                0.0f,
                BULLET_ANGLE_VELOCITY,
                20,
                -score);

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
