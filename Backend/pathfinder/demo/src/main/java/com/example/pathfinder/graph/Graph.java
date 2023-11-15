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

        //Will also add the same path in reverse order for bidirectionality
        Edge reverseEdge = new Edge(edge.getDestinationId(), edge.getSourceId(), edge.getWeight());
        adjacencyList.get(edge.getDestinationId()).add(reverseEdge);
    }

    public LinkedList<String> getNodes() {
        LinkedList<String> graphNodes = new LinkedList<>();

        for (String k : adjacencyList.keySet()) {
            graphNodes.add(k);
        }

        return graphNodes;
    }

    public Map<String, LinkedList<Map<String, Object>>> getGraph() {
        Map<String, LinkedList<Map<String, Object>>> graphContent = new HashMap<>();

        for (String key : adjacencyList.keySet()) {
            if (!graphContent.containsKey(key)) {
                graphContent.put(key, new LinkedList<>());
            }

            for (Edge edge : adjacencyList.get(key)) {
                graphContent.get(key).add(edge.toMap());
            }
        }

        return graphContent;
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