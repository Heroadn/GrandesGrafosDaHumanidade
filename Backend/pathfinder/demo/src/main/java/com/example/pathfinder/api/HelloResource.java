package com.example.pathfinder.api;

import com.example.pathfinder.graph.Graph;
import com.example.pathfinder.services.GraphService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloResource {

    private final GraphService graphService;

    public HelloResource(GraphService graphService) {
        this.graphService = graphService;
    }

    @GetMapping("/")
    public String index() {
        //The following lines are just intended to test the application for a while,
        // they should be removed later
        Graph graph = graphService.getGraph();
        graph.printGraph();
        return "Salve salve! Essa rota é só pra ver se tá tudo funcionando de fato";
    }

}