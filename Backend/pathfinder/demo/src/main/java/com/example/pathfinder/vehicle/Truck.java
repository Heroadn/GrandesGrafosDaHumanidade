package com.example.pathfinder.vehicle;

public final class Truck extends Vehicles{
    public Truck(double fuelconsumption, int maxspeed, String fueltype) {
        this.setFuelconsumption(fuelconsumption);
        this.setMaxspeed(maxspeed);
        this.setFueltype(fueltype);
        this.setName("Truck");
    }


}
