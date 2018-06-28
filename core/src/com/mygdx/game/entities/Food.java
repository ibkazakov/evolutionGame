package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.WorldState;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.managers.Assets;


public class Food extends Entity {
    private static TextureRegion[] foods = new TextureRegion[]{
            Assets.getInstance().getTexture("food1"),
            Assets.getInstance().getTexture("food2"),
            Assets.getInstance().getTexture("food3")
    };

    private static final float RESPAWN_DELAY = 9.0f;
    private static final float FOOD_ANGLE_VELOCITY = 150.0f;

    private static Vector2 trash_vector = new Vector2(0.0f, 0.0f);

    private float timeStorage = 0.0f;


    public Food() {
        super(foods[(int)(Math.random() * foods.length)],
                trash_vector,
                trash_vector,
                MathUtils.random(360),
                MathUtils.randomSign() * FOOD_ANGLE_VELOCITY,
                20,
                400);
        position.set(MathUtils.random(0,WorldState.WORLD_WIGTH), MathUtils.random(0,WorldState.WORLD_HEIGHT));
        velocity.set(0.0f, 0.0f);
    }

    @Override
    public void update(float dt) {
        if (active) {
            super.update(dt);
        } else {
            timeStorage += dt;
            if (timeStorage > RESPAWN_DELAY) {
                respawn();
            }
        }
    }

    // That's why I don't need FoodManager. Quality of foods is stable.
    private void respawn() {
        active = true;
        timeStorage = 0.0f;
        texture =  foods[(int)(Math.random() * foods.length)];
        position.set(MathUtils.random(0,WorldState.WORLD_WIGTH), MathUtils.random(0,WorldState.WORLD_HEIGHT));
        angle = MathUtils.random(360);
        angle_velocity = MathUtils.randomSign() * FOOD_ANGLE_VELOCITY;
    }

    public void beEaten() {
        active = false;
    }

}
