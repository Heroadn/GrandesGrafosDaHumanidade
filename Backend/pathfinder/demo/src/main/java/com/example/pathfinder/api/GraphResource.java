package com.example.pathfinder.api;

import com.example.pathfinder.graph.Graph;
import com.example.pathfinder.services.GraphService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

@RestController
public class GraphResource {

    private final GraphService graphService;

    public GraphResource(GraphService graphService) {
        this.graphService = graphService;
    }

    @GetMapping("/graph/test")
    public String test() {
        Graph graph = graphService.getGraph();
        graph.printGraph();
        return "Rota de teste do grafo principal! Deve imprimir o grafo no console da aplicação!";
    }

    @GetMapping("/graph/nodes")
    public Map<String, LinkedList<String>> getNodes() {
        Graph graph = graphService.getGraph();
        Map<String, LinkedList<String>> response = new HashMap<>();
        response.put("nodes", graph.getNodes());
        return response;
    }

    @GetMapping("/graph/content")
    public Map<String, Map<String, LinkedList<Map<String, Object>>>>  getContent() {
        Graph graph = graphService.getGraph();
        Map<String, Map<String, LinkedList<Map<String, Object>>>> response = new HashMap<>();
        response.put("graph", graph.getGraph());
        return response;
    }

}