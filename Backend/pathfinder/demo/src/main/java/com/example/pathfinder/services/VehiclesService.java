package com.example.pathfinder.services;

import com.example.pathfinder.vehicle.*;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehiclesService {
    private final LinkedList<Vehicle> vehicles;

    @Autowired
    public VehiclesService() {
        this.vehicles = new LinkedList<>();
        this.vehicles.add(new Bus(30, 90, "Diesel"));
        this.vehicles.add(new Car(20, 100, "Gas"));
        this.vehicles.add(new Minibus(25, 90, "Gas"));
        this.vehicles.add(new Motorcycle(10, 80, "Gas"));
        this.vehicles.add(new Truck(40, 80, "Diesel"));
    }

    public LinkedList<Vehicle> getVehicles() {
        return vehicles;
    }
}

