package com.example.pathfinder.utils;

import com.example.pathfinder.graph.Graph;
import com.example.pathfinder.graph.Edge;
import com.example.pathfinder.graph.NodeCoordinate;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.function.Function;


public class Utils {

    public static LinkedList<NodeCoordinate> getCoordinatesFromCsv(String fileName) {
        LinkedList<NodeCoordinate> allCoordinates = new LinkedList<>();

        Function<String[], Void> insertTemplate
                = (values) -> insertNodeCoordinate(values, allCoordinates);
        executeFromCsv(fileName, insertTemplate);
        return allCoordinates;
    }

    public static Graph createGraphFromCsv(String fileName) {
        System.out.println("Will read from the CSV file and generate a Graph");
        Graph main_graph = new Graph();

        Function<String[], Void> insertTemplate
                = (values) -> insertOnGraph(values, main_graph);
        executeFromCsv(fileName, insertTemplate);
        return main_graph;
    }

    public static Void insertOnGraph(String[] values, Graph graph){
        if (values.length >= 3) {
            String source = values[0].trim();
            String destination = values[1].trim();
            int weight = Integer.parseInt(values[2].trim());

            Edge edge = new Edge(source, destination, weight);
            graph.addEdge(edge);
        }

        return null;
    }

    public static Void insertNodeCoordinate(String[] values, LinkedList<NodeCoordinate> allCoordinates){
        if (values.length >= 3) {
            String nodeName = values[0].trim();
            float raw_x = Float.parseFloat(values[1].trim())*100;
            float raw_y = Float.parseFloat(values[2].trim())*100;

            int x = Math.round(raw_x)*2;
            int y = 1000-Math.round(raw_y)*2;

            NodeCoordinate coordinate = new NodeCoordinate(nodeName, x, y);
            allCoordinates.add(coordinate);
        }

        return null;
    }

    public static void executeFromCsv(String fileName, Function<String[], Void> insert){
        try {
            InputStream inputStream
                    = Utils.class.getClassLoader().getResourceAsStream(fileName);
            BufferedReader reader
                    = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                insert.apply(values);
            }

        } catch (IOException | NullPointerException | NumberFormatException e) {
            System.out.println("An error occured while reading the CSV!: " + fileName + " ; Exception: " + e);
            e.printStackTrace();
        }
    }
}



