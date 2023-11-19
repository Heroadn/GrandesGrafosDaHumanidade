package com.example.pathfinder.api;

import com.example.pathfinder.graph.Graph;
import com.example.pathfinder.graph.Path;
import com.example.pathfinder.services.GraphService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

@RestController
@RequestMapping("/graph")
public class GraphResource {
    @Autowired
    private GraphService graphService;

    @GetMapping("/test")
    public String test() {
        Graph graph = graphService.getGraph();
        graph.printGraph();

        System.out.println(graph.dijkstra("cidade_a", "cidade_f"));
        return "Rota de teste do grafo principal! Deve imprimir o grafo no console da aplicação!";
    }

    @GetMapping("/print")
    public Map<String, String> print() {
        Graph graph = graphService.getGraph();
        Map<String, String> response = new HashMap<>();
        response.put("msg", graph.printGraph());
        return response;
    }

    @GetMapping("/nodes")
    public Map<String, LinkedList<String>> getNodes() {
        Graph graph = graphService.getGraph();
        Map<String, LinkedList<String>> response = new HashMap<>();
        response.put("nodes", graph.getNodes());
        return response;
    }

    @GetMapping("/content")
    public Map<String, Map<String, LinkedList<Map<String, Object>>>>  getContent() {
        Graph graph = graphService.getGraph();
        Map<String, Map<String, LinkedList<Map<String, Object>>>> response = new HashMap<>();
        response.put("graph", graph.getGraph());
        return response;
    }

    @GetMapping("/shortest_path/{pointA}/{pointB}")
    public ResponseEntity<Map<String, Object>> getShortestPath(@PathVariable String pointA, @PathVariable String pointB) {
        Graph graph = graphService.getGraph();
        Path path_obj = graph.dijkstra(pointA, pointB);

        Map<String, Object> response = new HashMap<>();

        if (path_obj == null){
            response.put("msg", "Couldn't find a path from "+pointA+" to "+pointB);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        response.put("shortest_distance", path_obj.getDistance());
        response.put("shortest_path", path_obj.getPaths());
        return ResponseEntity.ok(response);
    }

}