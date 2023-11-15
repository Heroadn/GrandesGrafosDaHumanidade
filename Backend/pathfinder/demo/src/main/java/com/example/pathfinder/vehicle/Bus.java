package com.example.pathfinder.vehicle;

public class Bus extends Vehicles {
    private static String NAME = "Bus";


    public Bus(double fuelconsumption, int maxspeed, String fueltype) {
        this.setFuelconsumption(fuelconsumption);
        this.setMaxspeed(maxspeed);
        this.setFueltype(fueltype);
    }

}
