package com.example.pathfinder.vehicle;

public final class Vehicle {
    private final double fuelconsumption;
    private final int maxspeed;
    private final String fueltype;
    private final String name;
    public String getName() {
        return name;
    }



    public double getFuelconsumption() {
        return fuelconsumption;
    }



    public int getMaxspeed() {
        return maxspeed;
    }



    public String getFueltype() {
        return fueltype;
    }




    public  Vehicle(double fuelconsumption, int maxspeed, String fueltype, String name) {
        this.fuelconsumption = fuelconsumption;
        this.maxspeed = maxspeed;
        this.fueltype = fueltype;
        this.name = name;
    }
}
