package com.example.pathfinder;

import com.example.pathfinder.hints.HintsRegistrar;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportRuntimeHints;

@SpringBootApplication
@ImportRuntimeHints(HintsRegistrar.class)
public class PathFinderApplication {
	public static void main(String[] args) {
		SpringApplication.run(PathFinderApplication.class, args);
	}

}
 