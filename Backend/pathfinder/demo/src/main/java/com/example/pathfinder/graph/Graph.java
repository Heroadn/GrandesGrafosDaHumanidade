package com.example.pathfinder.graph;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;

import com.example.pathfinder.utils.Utils;

public class Graph {
    private final Map<String, LinkedList<Edge>> adjacencyList;

    private final LinkedList<NodeCoordinate> graphCoordinates;

    public Graph() {

        this.adjacencyList = new HashMap<>();
        this.graphCoordinates = Utils.getCoordinatesFromCsv("node_coordinates.csv");
    }

    public void addEdge(Edge edge) {
        if (!adjacencyList.containsKey(edge.getSourceId())) {
            adjacencyList.put(edge.getSourceId(), new LinkedList<>());
        }

        if (!adjacencyList.containsKey(edge.getDestinationId())) {
            adjacencyList.put(edge.getDestinationId(), new LinkedList<>());
        }

        adjacencyList.get(edge.getSourceId()).add(edge);

        //Will also add the same path in reverse order for the graph to be bidirectional
        Edge reverseEdge = new Edge(edge.getDestinationId(), edge.getSourceId(), edge.getWeight());
        adjacencyList.get(edge.getDestinationId()).add(reverseEdge);
    }

    public LinkedList<String> getNodes() {
        return new LinkedList<>(adjacencyList.keySet());
    }

    public LinkedList<NodeCoordinate> getGraphCoordinates() {
        return graphCoordinates;
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

    public String printGraph() {
        int i = 1;
        StringBuilder text = new StringBuilder();

        for (String k : adjacencyList.keySet()) {
            for (Edge edge : adjacencyList.get(k)) {
                text.append(String.format("vertex-%d %s\n", i, edge.toString()));
            }
            i++;
        }

        String text_string = text.toString();
        System.out.println(text_string);

        return text_string;
    }

    public Path dijkstra(String startVertex, String endVertex) {
        PriorityQueue<NodeDistance> priorityQueue = new PriorityQueue<>();
        Map<String, Boolean> visited = new HashMap<>();
        Map<String, Integer> distance = new HashMap<>();
        Map<String, String> predecessor = new HashMap<>();

        for (String vertex : adjacencyList.keySet()) {
            distance.put(vertex, Integer.MAX_VALUE);
            visited.put(vertex, false);
        }

        distance.put(startVertex, 0);
        priorityQueue.add(new NodeDistance(startVertex, 0));

        while (!priorityQueue.isEmpty()) {
            NodeDistance nodeDistance = priorityQueue.poll();
            String currentVertex = nodeDistance.vertex;

            if (visited.containsKey(currentVertex) && visited.get(currentVertex)) {
                continue;
            }

            visited.put(currentVertex, true);

            if (adjacencyList.containsKey(currentVertex)) {
                for (Edge edge : adjacencyList.get(currentVertex)) {
                    int newDistance = nodeDistance.distance + edge.getWeight();

                    if (newDistance < distance.get(edge.getDestinationId())) {
                        distance.put(edge.getDestinationId(), newDistance);
                        predecessor.put(edge.getDestinationId(), currentVertex);
                        priorityQueue.add(new NodeDistance(edge.getDestinationId(), newDistance));
                    }
                }
            }
        }

        return getShortestPath(startVertex, endVertex, distance, predecessor);
    }

    public Path getShortestPath(String startVertex, String endVertex, Map<String, Integer> distance, Map<String, String> predecessor) {
        LinkedList<String> path = new LinkedList<>();
        String currentVertex = endVertex;

        while (!currentVertex.equals(startVertex)) {
            path.addFirst(currentVertex);
            if (!predecessor.containsKey(currentVertex)) {
                break;
            }
            currentVertex = predecessor.get(currentVertex);
        }

        path.addFirst(startVertex);

        if (distance.containsKey(endVertex) && distance.get(endVertex) != Integer.MAX_VALUE) {
            int shortestDistance = distance.get(endVertex);
            return new Path(path, shortestDistance);
        } else {
            return null;
        }
    }

}