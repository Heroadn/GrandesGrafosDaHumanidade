package com.example.pathfinder.vehicle;

public abstract class  Vehicles {
    private double fuelconsumption;
    private int maxspeed;
    private String fueltype;


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
