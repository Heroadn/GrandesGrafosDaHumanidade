package com.example.pathfinder.graph;

class NodeDistance implements Comparable<NodeDistance> {
    String vertex;
    int distance;

    public NodeDistance(String vertex, int distance) {
        this.vertex = vertex;
        this.distance = distance;
    }

    @Override
    public int compareTo(NodeDistance other) {
        return Integer.compare(this.distance, other.distance);
    }
}
