package com.mygdx.game.entities.active_entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.managers.BulletManager;

public class Enemy extends ActiveEntity {
    private Hero heroLink;
    private static final float RESPAWN_DELAY = 4.0f;

    private float respawnTimeStorage = 0.0f;

    public Enemy(Hero heroLink, BulletManager bulletManager) {
        super(new Texture("enemy.jpg"),
                new Vector2(MathUtils.random(0, 1280), MathUtils.random(0,720)),
                new Vector2(0.0f, 0.0f),
                MathUtils.random(360), 0.0f,
                (int)(heroLink.getRadius() * 1.5), (int)(heroLink.getScore() * 1.5 * 1.5),
                bulletManager);
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
        position.set(MathUtils.random(0, 1280), MathUtils.random(0,720));
        velocity.set(0.0f, 0.0f);
        angle = MathUtils.random(360);
        angle_velocity = 0.0f;
        radius = heroLink.getRadius() * 2;
        score = heroLink.getScore() * 4;
        respawnTimeStorage = 0.0f;
        active = true;
    }

//enemy's artifactial intellegence

    // object creation elimination(use in keepDistance, keepAngleToHero)
    private Vector2 dir = new Vector2();

    private int turn = 0;

    private void ai() {
        if (heroLink.isActive()) {
            if (score < heroLink.getScore()) {
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
        dir.set(heroLink.getPosition()).sub(position).nor();
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
        dir.set(heroLink.getPosition()).sub(position);
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
