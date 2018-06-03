package org.laki;

import java.util.Comparator;

public class Entity {
    int x;
    int y;
    int value;
    char cameFrom;

    public Entity(int x, int y, int value, char cameFrom) {
        this.x = x;
        this.y = y;
        this.value = value;
        this.cameFrom = cameFrom;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public char getCameFrom() {
        return cameFrom;
    }

    public void setCameFrom(char cameFrom) {
        this.cameFrom = cameFrom;
    }

    @Override
    public boolean equals(Object obj) {
        Entity entity = (Entity)obj;
        return entity.getX() == this.x && entity.getY() == this.y && entity.getValue() == this.getValue()
                && entity.cameFrom == this.cameFrom;
    }

    @Override
    public String toString() {
        return String.valueOf(this.getValue());
    }
}
