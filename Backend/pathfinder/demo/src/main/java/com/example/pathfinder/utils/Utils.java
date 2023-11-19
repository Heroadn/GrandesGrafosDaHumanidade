package com.example.pathfinder.utils;

import com.example.pathfinder.graph.Graph;
import com.example.pathfinder.graph.Edge;
import org.springframework.core.io.ClassPathResource;

import java.io.*;

public class Utils {

    public static Graph createGraphFromCsv(String fileName) {
        System.out.println("Will read from the CSV file and generate a Graph");
        Graph graph = new Graph();

        try {
            InputStream inputStream
                    = Utils.class.getClassLoader().getResourceAsStream(fileName);
            BufferedReader reader
                    = new BufferedReader(new InputStreamReader(inputStream));

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

        } catch (IOException | NullPointerException | NumberFormatException e) {
            System.out.println("An error occured while reading the CSV!: " + fileName + " ; Exception: " + e);
            e.printStackTrace();
        }

        return graph;
    }
}



