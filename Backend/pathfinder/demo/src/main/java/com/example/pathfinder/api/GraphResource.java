package com.example.pathfinder.api;

import com.example.pathfinder.graph.Graph;
import com.example.pathfinder.services.GraphService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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

}