package com.example.pathfinder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class PathFinderApplication {
	public static void main(String[] args) {
		SpringApplication.run(PathFinderApplication.class, args);
	}

}
 