package com.example.pathfinder.vehicle;

public abstract class Vehicle {
    private double fuelconsumption;
    private int maxspeed;
    private String fueltype;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public double getFuelconsumption() {
        return fuelconsumption;
    }

    public void setFuelconsumption(double fuelconsumption) {
        this.fuelconsumption = fuelconsumption;
    }

    public int getMaxspeed() {
        return maxspeed;
    }

    public void setMaxspeed(int maxspeed) {
        this.maxspeed = maxspeed;
    }

    public String getFueltype() {
        return fueltype;
    }

    public void setFueltype(String fueltype) {
        this.fueltype = fueltype;
    }
}
