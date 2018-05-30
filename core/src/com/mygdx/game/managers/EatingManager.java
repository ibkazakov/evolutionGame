package com.mygdx.game.managers;

import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.active_entities.ActiveEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EatingManager {
    private ArrayList<Collection<ActiveEntity>> canEat = new ArrayList<Collection<ActiveEntity>>();
    private ArrayList<Collection<Entity>> canBeEaten = new ArrayList<Collection<Entity>>();

    public void addToCanEat(Collection<ActiveEntity> c) {
        canEat.add(c);
    }

    public void addToCanBeEaten(Collection<Entity> c) {
        canBeEaten.add(c);
    }

    public void eating() {
        for(int i = 0; i < canEat.size(); i++) {
            for(ActiveEntity activeEntity : canEat.get(i)) {
                for(int j = 0; j < canBeEaten.size(); j++) {
                    for (Entity entity : canBeEaten.get(j)) {
                        if ((activeEntity != entity) && isCover(activeEntity, entity)
                                && entity.isActive() && activeEntity.isActive()) {
                            activeEntity.eat(entity);
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
