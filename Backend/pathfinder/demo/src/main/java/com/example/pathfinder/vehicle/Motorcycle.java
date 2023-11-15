package com.example.pathfinder.vehicle;

public class Motorcycle extends  Vehicles {
    private static String NAME = "Motorcycle";


    public Motorcycle(double fuelconsumption, int maxspeed, String fueltype) {
        this.setFuelconsumption(fuelconsumption);
        this.setMaxspeed(maxspeed);
        this.setFueltype(fueltype);
    }

}
