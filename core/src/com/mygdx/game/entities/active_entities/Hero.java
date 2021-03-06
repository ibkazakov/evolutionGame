package com.mygdx.game.entities.active_entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.WorldState;
import com.mygdx.game.managers.Assets;
import com.mygdx.game.managers.BulletManager;

public class Hero extends ActiveEntity {

    private static final float ADD_VELOCITY = 10.0f;
    private static final float ADD_ANGLE_VELOCITY = 10.0f;
    private static final float RESPAWN_DELAY = 8.0f;

    private float respawnTimeStorage = 0.0f;

    public Hero(BulletManager bulletManager) {
        super(Assets.getInstance().getTexture("hero"), new Vector2(640.0f, 360.0f),
                new Vector2(0.0f, 0.0f), 0.0f, 0.0f, 30, 900,
                bulletManager);
    }

    @Override
    public void update(float dt) {
            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                velocity.y += ADD_VELOCITY;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.X)) {
                velocity.y -= ADD_VELOCITY;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                velocity.x += ADD_VELOCITY;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                velocity.x -= ADD_VELOCITY;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                angle_velocity += ADD_ANGLE_VELOCITY;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
                angle_velocity -= ADD_ANGLE_VELOCITY;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
                throwBullet();
            }

            super.update(dt);
            // gs.getViewport().unproject(position);

            if (!active) {
                respawnTimeStorage += dt;
                if (respawnTimeStorage > RESPAWN_DELAY) {
                    respawn();
                }
            }
    }

    @Override
    public void beEaten() {
        //game over\restart
        active = false;
    }

    @Override
    protected void respawn() {
        position.set(640.0f, 360.0f);
        velocity.set(0.0f, 0.0f);
        angle = 0.0f;
        angle_velocity = 0.0f;
        radius = 30;
        score = 900;
        respawnTimeStorage = 0.0f;
        active = true;
    }

}
