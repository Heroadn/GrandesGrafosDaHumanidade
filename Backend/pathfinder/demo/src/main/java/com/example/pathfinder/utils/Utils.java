package com.example.pathfinder.utils;

import com.example.pathfinder.graph.Graph;
import com.example.pathfinder.graph.Edge;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Utils {
    public static Graph createGraphFromCsv(String fileName) {
        Graph graph = new Graph();
        InputStream inputStream = Utils.class.getClassLoader().getResourceAsStream(fileName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        try {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 3) {
                    String source = values[0].trim();
                    String destination = values[1].trim();
                    int weight = Integer.parseInt(values[2].trim());

                    Edge edge = new Edge(source, destination, weight);
                    graph.addEdge(edge);
                }
            }

        } catch (IOException | NumberFormatException e) {
            System.out.println("An error occured while reading the CSV!");
            e.printStackTrace();
        }

        return graph;
    }
}



