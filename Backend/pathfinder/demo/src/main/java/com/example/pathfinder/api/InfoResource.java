package com.example.pathfinder.api;

import com.example.pathfinder.services.GraphService;
import com.example.pathfinder.services.VehiclesService;

import com.example.pathfinder.vehicle.Vehicles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

@RestController
@RequestMapping("/info")
public class InfoResource {
    @Autowired
    private VehiclesService vehiclesService;

    @GetMapping("/vehicles")
    public Map<String, Object> get_vehicles() {
        LinkedList<Vehicles> vehicles = vehiclesService.getVehicles();
        Map<String, Object> response = new HashMap<>();
        response.put("vehicles", vehicles);

        return response;
    }

}