package com.example.pathfinder.vehicle;

public class Car extends  Vehicles {
    private static String NAME = "car";


    public Car(double fuelconsumption, int maxspeed, String fueltype) {

        this.setFuelconsumption(fuelconsumption);
        this.setMaxspeed(maxspeed);
        this.setFueltype(fueltype);
    }

}
