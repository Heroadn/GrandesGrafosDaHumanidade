package com.example.pathfinder.api;

import com.example.pathfinder.services.VehiclesService;

import com.example.pathfinder.vehicle.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/info")
public class InfoResource {
    @Autowired
    private VehiclesService vehiclesService;

    @GetMapping("/vehicles")
    public Map<String, List<Vehicle>> get_vehicles() {
        LinkedList<Vehicle> vehicles = vehiclesService.getVehicles();

        Map<String, List<Vehicle>> response = new HashMap<>();
        response.put("vehicles", vehicles);
        return response;
    }

}