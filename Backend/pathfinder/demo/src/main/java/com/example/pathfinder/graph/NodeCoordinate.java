package com.example.pathfinder.graph;

public class NodeCoordinate {
    private String name;
    private Float x;
    private Float y;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public NodeCoordinate(String name, float x, float y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }
}
