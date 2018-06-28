package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.Food;
import com.mygdx.game.entities.active_entities.Hero;
import com.mygdx.game.managers.Assets;
import com.mygdx.game.managers.GameScreen;
import com.mygdx.game.entities.Entity;

import java.util.List;

public class MiniMap {
    private Vector2 position;
    private Vector2 tmp;
    private GameScreen gs;
    private TextureRegion texBack, texHero, texConsumable, texEnemy;
    private float gameScanRadius;
    private float mapRadius;
    private float pointRadius = 2.0f;

    public MiniMap(GameScreen gs) {
        this.gs = gs;
        this.position = new Vector2(1170, 100);
        this.texBack = Assets.getInstance().getTexture("mapBack");
        this.texHero = Assets.getInstance().getTexture("heroPoint");
        this.texConsumable = Assets.getInstance().getTexture("foodpoint");
        this.texEnemy = Assets.getInstance().getTexture("enemypoint");
        this.gameScanRadius = 1000;
        this.mapRadius = 100;
        this.tmp = new Vector2(0, 0);
    }

    public void render(SpriteBatch batch) {
        Hero hero = gs.getHero();
        List<Entity> enemyList = gs.getEnemyList();
        List<Entity> foodList = gs.getFoods();

        batch.draw(texBack, position.x - mapRadius, position.y - mapRadius, mapRadius,
                mapRadius, mapRadius *2, mapRadius * 2, 1.0f, 1.0f,0.0f);

        if (hero.isActive()) {
            batch.draw(texHero, position.x - pointRadius, position.y - pointRadius, pointRadius, pointRadius,
                    pointRadius * 2, pointRadius * 2, 1.0f, 1.0f, 0.0f);

        }

        // foods
        for(int i = 0; i < foodList.size(); i++) {
            tmp.set(foodList.get(i).getPosition());
            if (hero.getPosition().dst(tmp) < gameScanRadius && foodList.get(i).isActive()) {
                tmp.sub(hero.getPosition());
                tmp.scl(mapRadius / gameScanRadius);
                batch.draw(texConsumable, position.x - pointRadius + tmp.x, position.y - pointRadius + tmp.y, pointRadius, pointRadius,
                        pointRadius * 2, pointRadius * 2, 1.0f, 1.0f, 0.0f);
            }
        }

        //enemies
        for(int i = 0; i < enemyList.size(); i++) {
            tmp.set(enemyList.get(i).getPosition());
            if (hero.getPosition().dst(tmp) < gameScanRadius && enemyList.get(i).isActive()) {
                tmp.sub(hero.getPosition());
                tmp.scl(mapRadius / gameScanRadius);
                batch.draw(texEnemy, position.x - pointRadius + tmp.x, position.y - pointRadius + tmp.y, pointRadius, pointRadius,
                        pointRadius * 2, pointRadius * 2, 1.0f, 1.0f, 0.0f);
            }
        }
    }

}
