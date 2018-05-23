package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.util.List;

public class Enemy extends ActiveEntity {
    private Hero heroLink;
    private static final float RESPAWN_DELAY = 4.0f;

    private float respawnTimeStorage = 0.0f;

    public Enemy(List<Bullet> bulletLink, Hero heroLink) {
        super(new Texture("enemy.jpg"),
                new Vector2(MathUtils.random(0, 1280), MathUtils.random(0,720)),
                new Vector2(0.0f, 0.0f),
                MathUtils.random(360), 0.0f,
                (int)(heroLink.radius * 1.5), (int)(heroLink.score * 1.5 * 1.5),
                bulletLink);
        this.heroLink = heroLink;
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        if (!active) {
            respawnTimeStorage += dt;
            if (respawnTimeStorage > RESPAWN_DELAY) {
                respawn();
            }
        }
        ai();
    }

    @Override
    public void beEaten() {
        active = false;
    }

    @Override
    protected void respawn() {
        position = new Vector2(MathUtils.random(0, 1280), MathUtils.random(0,720));
        velocity =  new Vector2(0.0f, 0.0f);
        angle = MathUtils.random(360);
        angle_velocity = 0.0f;
        radius = heroLink.radius * 2;
        score = heroLink.score * 4;
        respawnTimeStorage = 0.0f;
        active = true;
    }

//enemy's artifactial intellegence

    private int turn = 0;

    private void ai() {
        if (heroLink.active) {
            if (score < heroLink.score) {
                keepDistance(500.0f);
                keepAngleToHero();
                turn++;
                // game complexity parametr
                if (turn > 100) {
                    throwBullet();
                    turn = 0;
                }
            } else {
                keepDistance(0.0f);
                keepAngleToHero();
            }
        }
    }

    private void keepAngleToHero() {
        Vector2 dir = heroLink.position.cpy();
        dir.sub(position);
        dir.nor();
        float angle_to_hero = dir.angle();
        if (angle > angle_to_hero) {
            if (Math.abs(angle - angle_to_hero) <= 180.0f) {
                angle_velocity -= 1.0f;
            }
            else {
                angle_velocity += 1.0f;
            }
        }
        if (angle < angle_to_hero) {
            if (Math.abs(angle - angle_to_hero) <= 180.0f) {
                angle_velocity += 1.0f;
            }
            else {
                angle_velocity -= 1.0f;
            }
        }
    }

    private void keepDistance(float distance) {
        Vector2 dir = heroLink.position.cpy();
        dir.sub(position);
        float actual_distance = dir.len();
        dir.nor();
        if (distance < actual_distance) {
            velocity.mulAdd(dir, 1.5f);
        }
        else {
            velocity.mulAdd(dir, -1.5f);
        }
    }
}
