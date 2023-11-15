package com.example.pathfinder.services;

import com.example.pathfinder.graph.Graph;
import com.example.pathfinder.utils.Utils;

import org.springframework.stereotype.Service;

@Service
public class GraphService {
    private final Graph graph;

    public GraphService() {
        this.graph = Utils.createGraphFromCsv("paths.csv");
    }

    public Graph getGraph() {
        return graph;
    }
}