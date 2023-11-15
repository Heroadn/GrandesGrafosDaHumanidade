package com.example.pathfinder.vehicle;

public class Motorcycle extends  Vehicles {
    public Motorcycle(double fuelconsumption, int maxspeed, String fueltype) {
        this.setFuelconsumption(fuelconsumption);
        this.setMaxspeed(maxspeed);
        this.setFueltype(fueltype);
        this.setName("Motorcycle");
    }

}
