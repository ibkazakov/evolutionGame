package com.mygdx.game.managers;

import com.mygdx.game.entities.Entity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public abstract class ObjectPool<T extends Entity> {
    protected List<T> activeObjects = new LinkedList<T>();
    protected List<T> freeObjects = new ArrayList<T>();


    private int capacity;
    private GameScreen gs;

    public ObjectPool(int capacity, GameScreen gs) {
        this.capacity = capacity;
        this.gs = gs;
        for (int i = 0; i < capacity; i++) {
            T temp = newObject();
            temp.setGs(gs);
            freeObjects.add(temp);
        }
    }

    public void free(int index) {
        freeObjects.add(activeObjects.remove(index));
    }


    protected abstract T newObject();

    public synchronized T getActiveElement() {

        if (freeObjects.size() == 0) {
            for (int i = 0; i < capacity; i++) {
                T temp = newObject();
                temp.setGs(gs);
                freeObjects.add(temp);
            }
            capacity *= 2;
        }


        T temp = freeObjects.remove(freeObjects.size() - 1);
        temp.setActive(true);
        activeObjects.add(temp);

        return temp;
    }


    public void checkPool() {
        for (int i = activeObjects.size() - 1; i >= 0; i--) {
            if (!activeObjects.get(i).isActive()) {
                free(i);
            }
        }

    }

}
