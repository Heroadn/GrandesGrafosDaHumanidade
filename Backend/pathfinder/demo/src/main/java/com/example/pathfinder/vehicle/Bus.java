package com.example.pathfinder.vehicle;

public class Bus extends Vehicles {
    public Bus(double fuelconsumption, int maxspeed, String fueltype) {
        this.setFuelconsumption(fuelconsumption);
        this.setMaxspeed(maxspeed);
        this.setFueltype(fueltype);
        this.setName("Bus");
    }

}
