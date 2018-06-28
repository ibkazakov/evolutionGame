package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.WorldState;
import com.mygdx.game.managers.GameScreen;

public abstract class Entity {
    protected TextureRegion texture;
    protected Vector2 position = new Vector2();
    protected Vector2 velocity = new Vector2();
    protected float angle;
    protected float angle_velocity;
    protected int radius;
    protected int score;
    public GameScreen gs;

    public void setGs(GameScreen gs) {
        this.gs = gs;
    }

    protected boolean active = true;

    // copying position and velocity vectors, no copying links
    public Entity(TextureRegion texture, Vector2 position, Vector2 velocity, float angle, float angle_velocity, int radius, int score) {
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
            batch.draw(texture, position.x - radius, position.y - radius, radius, radius, radius * 2, radius * 2, 1.0f, 1.0f, angle);
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
            position.x += WorldState.WORLD_WIGTH;
        }
        if (position.x > WorldState.WORLD_WIGTH) {
            position.x -= WorldState.WORLD_WIGTH;
        }
        if (position.y < 0.0f) {
            position.y += WorldState.WORLD_HEIGHT;
        }
        if (position.y > WorldState.WORLD_HEIGHT) {
            position.y -= WorldState.WORLD_HEIGHT;
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
