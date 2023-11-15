package com.example.pathfinder.graph;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Graph {
    private Map<String, LinkedList<Edge>> adjacencyList;

    public Graph() {
        this.adjacencyList = new HashMap<>();
    }

    public void addEdge(Edge edge) {
        if (!adjacencyList.containsKey(edge.getSourceId())) {
            adjacencyList.put(edge.getSourceId(), new LinkedList<>());
        }

        if (!adjacencyList.containsKey(edge.getDestinationId())) {
            adjacencyList.put(edge.getDestinationId(), new LinkedList<>());
        }

        adjacencyList.get(edge.getSourceId()).add(edge);

        // Also add the same path in reverse order for bidirectionality
        Edge reverseEdge = new Edge(edge.getDestinationId(), edge.getSourceId(), edge.getWeight());
        adjacencyList.get(edge.getDestinationId()).add(reverseEdge);
    }

    public void printGraph() {
        int i = 1;
        StringBuilder text = new StringBuilder();

        for (String k : adjacencyList.keySet()) {
            for (Edge edge : adjacencyList.get(k)) {
                text.append(String.format("vertex-%d %s\n", i, edge.toString()));
            }
            i++;
        }

        System.out.println(text.toString());
    }
}