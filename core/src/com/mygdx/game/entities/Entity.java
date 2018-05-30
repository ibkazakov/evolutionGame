package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public abstract class Entity {
    protected Texture texture;
    protected Vector2 position = new Vector2();
    protected Vector2 velocity = new Vector2();
    protected float angle;
    protected float angle_velocity;
    protected int radius;
    protected int score;

    protected boolean active = true;

    // copying position and velocity vectors, no copying links
    public Entity(Texture texture, Vector2 position, Vector2 velocity, float angle, float angle_velocity, int radius, int score) {
        this.texture = texture;
        this.position.set(position);
        this.velocity.set(velocity);
        this.angle = angle;
        this.angle_velocity = angle_velocity;
        this.radius = radius;
        this.score = score;
    }

    public int getRadius() {
        return radius;
    }

    public void render(SpriteBatch batch) {
        if (active) {
            batch.draw(texture, position.x - radius, position.y - radius, radius, radius, radius * 2, radius * 2, 1.0f, 1.0f, angle, 0, 0, 512, 512, false, false);
        }
    }

    public void update(float dt) {
        // moving
        position.mulAdd(velocity, dt);
        if (angle < 0.0f) {
            angle += 360.0f;
        }
        if (angle > 360.0f) {
            angle -= 360.0f;
        }
        angle += angle_velocity * dt;

        //cyclic universe
        if (position.x < 0.0f) {
            position.x += 1280.0f;
        }
        if (position.x > 1280.0f) {
            position.x -= 1280.0f;
        }
        if (position.y < 0.0f) {
            position.y += 720.0f;
        }
        if (position.y > 720.0f) {
            position.y -= 720.0f;
        }
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public abstract void beEaten();

    public boolean isActive() {
        return active;
    }

    public int getScore() {
        return score;
    }

    public Vector2 getPosition() {
        return position;
    }
}
