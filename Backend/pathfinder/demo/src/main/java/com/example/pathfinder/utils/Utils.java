package com.example.pathfinder.utils;

import com.example.pathfinder.graph.Graph;
import com.example.pathfinder.graph.Edge;
import com.example.pathfinder.graph.NodeCoordinate;

import java.io.*;
import java.util.LinkedList;


public class Utils {

    public static LinkedList<NodeCoordinate> getCoordinatesFromCsv(String fileName) {
        LinkedList<NodeCoordinate> allCoordinates = new LinkedList<>();

        executeFromCsv(fileName, null, allCoordinates);

        return allCoordinates;
    }

    public static Graph createGraphFromCsv(String fileName) {
        System.out.println("Will read from the CSV file and generate a Graph");
        Graph main_graph = new Graph();

        executeFromCsv(fileName, main_graph, null);

        return main_graph;
    }


    public static void insertOnGraph(String[] values, Graph graph){
        if (values.length >= 3) {
            String source = values[0].trim();
            String destination = values[1].trim();
            int weight = Integer.parseInt(values[2].trim());

            Edge edge = new Edge(source, destination, weight);
            graph.addEdge(edge);
        }
    }

    public static void insertNodeCoordinate(String[] values, LinkedList<NodeCoordinate> allCoordinates){
        if (values.length >= 3) {
            String nodeName = values[0].trim();
            float raw_x = Float.parseFloat(values[1].trim())*100;
            float raw_y = Float.parseFloat(values[2].trim())*100;

            int x = Math.round(raw_x)*2;
            int y = 1000-Math.round(raw_y)*2;

            NodeCoordinate coordinate = new NodeCoordinate(nodeName, x, y);
            allCoordinates.add(coordinate);
        }
    }

    public static void executeFromCsv(String fileName, Graph graph, LinkedList<NodeCoordinate> allCoordinates){
        try {
            InputStream inputStream
                    = Utils.class.getClassLoader().getResourceAsStream(fileName);
            BufferedReader reader
                    = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");

                if (graph != null) {
                    insertOnGraph(values, graph);
                } else if (allCoordinates != null) {
                    insertNodeCoordinate(values, allCoordinates);
                }
            }

        } catch (IOException | NullPointerException | NumberFormatException e) {
            System.out.println("An error occured while reading the CSV!: " + fileName + " ; Exception: " + e);
            e.printStackTrace();
        }
    }
}



