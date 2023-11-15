package com.example.pathfinder.vehicle;

public class Minibus extends Vehicles {
    public Minibus(double fuelconsumption, int maxspeed, String fueltype) {

        this.setFuelconsumption(fuelconsumption);
        this.setMaxspeed(maxspeed);
        this.setFueltype(fueltype);
        this.setName("Minibus");
    }
}
