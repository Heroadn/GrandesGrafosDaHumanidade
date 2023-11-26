package com.example.pathfinder.services;

import com.example.pathfinder.vehicle.*;

import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehiclesService {
    private final LinkedList<Vehicle> vehicles;

    @Autowired
    public VehiclesService() {
        this.vehicles = new LinkedList<>();
        this.vehicles.add(new Vehicle(30, 90, "Diesel", "Bus"));
        this.vehicles.add(new Vehicle(20, 100, "Gas", "Car"));
        this.vehicles.add(new Vehicle(25, 90, "Gas", "Minibus"));
        this.vehicles.add(new Vehicle(10, 80, "Gas", "Motorcycle"));
        this.vehicles.add(new Vehicle(40, 80, "Diesel", "Truck"));
    }

    public LinkedList<Vehicle> getVehicles() {
        return vehicles;
    }
}

