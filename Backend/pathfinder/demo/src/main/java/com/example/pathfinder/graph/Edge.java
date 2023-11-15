package com.example.pathfinder.graph;

import java.util.Map;
import java.util.HashMap;

public class Edge {
    private String sourceId;
    private String destinationId;
    private int weight;

    public String getSourceId() {
        return sourceId;
    }

    public String getDestinationId() {
        return destinationId;
    }

    public int getWeight() {
        return weight;
    }

    public Edge(String sourceId, String destinationId, int weight) {
        this.sourceId = sourceId;
        this.destinationId = destinationId;
        this.weight = weight;
    }

    public String toString() {
        String destination = this.destinationId;
        String source = this.sourceId;
        int weight = this.weight;

        return String.format("(%s) is connected to %s with weight %d", source, destination, weight);
    }

    public java.util.Map<String, Object> toMap() {
        Map<String, Object> edgeMap = new HashMap<>();
        edgeMap.put("destination", this.destinationId);
        edgeMap.put("source", this.sourceId);
        edgeMap.put("weight", this.weight);

        return edgeMap;
    }
}