package com.example.pathfinder.response;

import com.example.pathfinder.vehicle.Vehicle;

import java.util.LinkedList;
import java.util.List;

public class VehicleResponse {
    public List<Vehicle> vehicles;

    public VehicleResponse(LinkedList<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }
}
