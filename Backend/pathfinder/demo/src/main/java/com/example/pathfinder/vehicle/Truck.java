package com.example.pathfinder.vehicle;

public final class Truck extends Vehicles{
    private static String NAME = "Truck";


    public Truck(double fuelconsumption, int maxspeed, String fueltype) {
        this.setFuelconsumption(fuelconsumption);
        this.setMaxspeed(maxspeed);
        this.setFueltype(fueltype);
    }


}
