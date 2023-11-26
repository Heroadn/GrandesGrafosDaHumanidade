package com.example.pathfinder.graph;

public class NodeCoordinate {
    private String name;
    private Integer x;
    private Integer y;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public NodeCoordinate(String name, Integer x, Integer y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }
}
