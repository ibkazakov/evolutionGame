package com.mygdx.game.entities.active_entities;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.WorldState;
import com.mygdx.game.managers.Assets;
import com.mygdx.game.managers.BulletManager;

public class Enemy extends ActiveEntity {
    private ActiveEntity targetLink;
    private static final float RESPAWN_DELAY = 4.0f;

    private float respawnTimeStorage = 0.0f;

    public Enemy(ActiveEntity targetLink, BulletManager bulletManager) {
        super(Assets.getInstance().getTexture("enemy"),
                new Vector2(MathUtils.random(0, WorldState.WORLD_WIGTH), MathUtils.random(0, WorldState.WORLD_HEIGHT)),
                new Vector2(0.0f, 0.0f),
                MathUtils.random(360), 0.0f,
                (int)(targetLink.getRadius() * 1.5), (int)(targetLink.getScore() * 1.5 * 1.5),
                bulletManager);
        this.targetLink = targetLink;
    }

    public void setTargetLink(ActiveEntity targetLink) {
        this.targetLink = targetLink;
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
        radius = targetLink.getRadius() * 2;
        score = targetLink.getScore() * 4;
        respawnTimeStorage = 0.0f;
        active = true;
    }

//enemy's artifactial intellegence

    // object creation elimination(use in keepDistance, keepAngleToHero)
    private Vector2 dir = new Vector2();

    private int turn = 0;

    private void ai() {
        if (targetLink.isActive()) {
            if (score < targetLink.getScore()) {
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
        dir.set(targetLink.getPosition()).sub(position).nor();
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
        dir.set(targetLink.getPosition()).sub(position);
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
