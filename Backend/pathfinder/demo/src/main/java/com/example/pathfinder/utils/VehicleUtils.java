package com.example.pathfinder.utils;

import com.example.pathfinder.services.VehiclesService;
import com.example.pathfinder.vehicle.TravelCost;
import com.example.pathfinder.vehicle.Vehicle;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class VehicleUtils {
    static final double gas_price =  5.63;
    static final double diesel_price =  4.63;
    static VehiclesService vehiclesService = new VehiclesService();

    private static Vehicle getDesiredvehicle(String vehicleType){
        for (Vehicle vehicle : vehiclesService.getVehicles() ) {
            if (vehicle.getName().toUpperCase(Locale.ROOT).contains(vehicleType.toUpperCase())) {
                return vehicle;
            }
        }
        return null;
    }

    public static TravelCost getTravelData(double distance, String vehicleType){
        Map<String, Object> data = new HashMap<>();
        Vehicle selectedVehicle = getDesiredvehicle(vehicleType);

        if (selectedVehicle != null)
            return getTravelcost(distance, selectedVehicle);
        else
            return null;
    }

    public static double roundDoubleValue(double value, int numberOfDecimals){
        double referenceForDecimals = Math.pow(10, numberOfDecimals);
        return Math.round(value * referenceForDecimals) / referenceForDecimals;
    }

    private static TravelCost getTravelcost(double distance, Vehicle vehicle ){
        // Fictional data, adjust as needed
        double foodCostPerDayPerDriver = 50.0;
        double maxHoursPerDriver = 4;

        // total fuel consumption in number of liters
        double totalFuelConsumption = (1/vehicle.getFuelconsumption()) * distance;

        double fuelConsumptionPrice;
        // price of fuel consumption in Lulas (R$)
        if (vehicle.getFueltype().equals("Diesel")) {
            fuelConsumptionPrice = roundDoubleValue(totalFuelConsumption*diesel_price, 2);
        }
        else{
            fuelConsumptionPrice = roundDoubleValue(totalFuelConsumption*gas_price, 2);
        }

        double roundedTotalFuelConsumption = roundDoubleValue(totalFuelConsumption, 2);

        // Calculate travel time
        double travelTimeHours = distance / vehicle.getMaxspeed();

        // Calculate the number of drivers needed
        double numberOfDrivers = travelTimeHours > maxHoursPerDriver ? 2 : 1;

        // Calculate food expenses
        double foodExpenses = foodCostPerDayPerDriver * numberOfDrivers;

        // Create and return the results map
        return new TravelCost(
                roundedTotalFuelConsumption,
                fuelConsumptionPrice,
                travelTimeHours,
                numberOfDrivers,
                foodExpenses);
    }

    private VehicleUtils() {
    }
}
