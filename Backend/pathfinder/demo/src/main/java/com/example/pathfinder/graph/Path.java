package com.example.pathfinder.graph;

import java.util.LinkedList;

public class Path {
    private LinkedList<String> paths;

    public LinkedList<String> getPaths() {
        return paths;
    }

    public int getDistance() {
        return distance;
    }

    private int distance;

    public Path(LinkedList<String> paths, int distance) {
        this.paths = paths;
        this.distance = distance;
    }
}
