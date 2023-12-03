package com.example.pathfinder.vehicle;

public class TravelCost {
    public double totalFuelConsumption;
    public double carFuelConsumption;
    public double travelTimeHours;
    public double numberOfDrivers;
    public double foodExpenses;

    public TravelCost(
            double totalFuelConsumption,
            double carFuelConsumption,
            double travelTimeHours,
            double numberOfDrivers,
            double foodExpenses)
    {
        this.totalFuelConsumption = totalFuelConsumption;
        this.carFuelConsumption = carFuelConsumption;
        this.travelTimeHours = travelTimeHours;
        this.numberOfDrivers = numberOfDrivers;
        this.foodExpenses = foodExpenses;
    }

    public double getTotalFuelConsumption() {
        return totalFuelConsumption;
    }

    public void setTotalFuelConsumption(double totalFuelConsumption) {
        this.totalFuelConsumption = totalFuelConsumption;
    }

    public double getCarFuelConsumption() {
        return carFuelConsumption;
    }

    public void setCarFuelConsumption(double carFuelConsumption) {
        this.carFuelConsumption = carFuelConsumption;
    }

    public double getTravelTimeHours() {
        return travelTimeHours;
    }

    public void setTravelTimeHours(double travelTimeHours) {
        this.travelTimeHours = travelTimeHours;
    }

    public double getNumberOfDrivers() {
        return numberOfDrivers;
    }

    public void setNumberOfDrivers(double numberOfDrivers) {
        this.numberOfDrivers = numberOfDrivers;
    }

    public double getFoodExpenses() {
        return foodExpenses;
    }

    public void setFoodExpenses(double foodExpenses) {
        this.foodExpenses = foodExpenses;
    }
}
