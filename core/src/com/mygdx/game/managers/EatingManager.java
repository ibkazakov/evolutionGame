package com.mygdx.game.managers;

import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.active_entities.ActiveEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EatingManager {
    private ArrayList<List<Entity>> canEat = new ArrayList<List<Entity>>();
    private ArrayList<List<Entity>> canBeEaten = new ArrayList<List<Entity>>();

    public void addToCanEat(List<Entity> c) {
        canEat.add(c);
    }

    public void addToCanBeEaten(List<Entity> c) {
        canBeEaten.add(c);
    }

    public void eating() {
        for(int i = 0; i < canEat.size(); i++) {
            for(int k = 0; k < canEat.get(i).size(); k++) {
                Entity activeEntity = canEat.get(i).get(k);
                for(int j = 0; j < canBeEaten.size(); j++) {
                    for (int l = 0; l < canBeEaten.get(j).size(); l++) {
                        Entity entity = canBeEaten.get(j).get(l);
                        if ((activeEntity != entity) && isCover(activeEntity, entity)
                                && entity.isActive() && activeEntity.isActive()) {
                            ((ActiveEntity)activeEntity).eat(entity);
                        }
                    }
                }
            }
        }
    }

    private boolean isCover(Entity e1, Entity e2) {
        float distance2 = e1.getPosition().dst2(e2.getPosition());
        int radius_diff2 = (e1.getRadius() - e2.getRadius())*(e1.getRadius() - e2.getRadius());
        return ((e1.getRadius() > e2.getRadius()) && (distance2 < radius_diff2));
    }
}
