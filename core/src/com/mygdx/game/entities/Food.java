package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.Entity;


public class Food extends Entity {
    private static Texture[] foods = new Texture[]{
            new Texture("food1.jpg"),
            new Texture("food2.png"),
            new Texture("food3.png")
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
        position.set(MathUtils.random(0,1280), MathUtils.random(0,720));
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

    private void respawn() {
        active = true;
        timeStorage = 0.0f;
        texture =  foods[(int)(Math.random() * foods.length)];
        position.set(MathUtils.random(0,1280), MathUtils.random(0,720));
        angle = MathUtils.random(360);
        angle_velocity = MathUtils.randomSign() * FOOD_ANGLE_VELOCITY;
    }

    public void beEaten() {
        active = false;
    }

}
