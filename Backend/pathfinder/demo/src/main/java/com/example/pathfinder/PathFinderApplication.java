package com.example.pathfinder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.pathfinder.graph.Graph;

import com.example.pathfinder.utils.Utils;

@SpringBootApplication
public class PathFinderApplication {

	public static void main(String[] args) {

		Graph graph = Utils.createGraphFromCsv("paths.csv");

		graph.printGraph();

		//SpringApplication.run(PathFinderApplication.class, args);
	}
}
