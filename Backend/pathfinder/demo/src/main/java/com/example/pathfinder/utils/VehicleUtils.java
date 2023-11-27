package com.example.pathfinder.utils;

import com.example.pathfinder.services.VehiclesService;
import com.example.pathfinder.vehicle.Vehicle;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class VehicleUtils {
    static final double gas_price =  5.63;
    static final double diesel_price =  4.63;
    static VehiclesService vehiclesService = new VehiclesService();

    private static Vehicle getDesiredvehicle(String vehicletype){
        for (Vehicle vehicle : vehiclesService.getVehicles() ) {
            if (vehicle.getName().toUpperCase(Locale.ROOT).contains(vehicletype.toUpperCase())) {
                return vehicle;
            }
        }
        return null;
    }

    public static Map<String, Object> gettraveldata(double distance, String vehicletype){
        Map<String, Object> data = new HashMap<>();
        Vehicle selectedvehicle = getDesiredvehicle(vehicletype);

        data.put("fuel_consumption:",distance/ selectedvehicle.getFuelconsumption());

        data.put("travel_cost:",getTravelcost(distance, selectedvehicle));





        return data;
    }


    private static Map<String, Double> getTravelcost(double distance,Vehicle vehicle ){
        // Fictional data, adjust as needed
        double foodCostPerDayPerDriver = 50.0;
        int maxHoursPerDriver = 4;

        // Calculate fuel expenses
        double fuelConsumption = vehicle.getFuelconsumption() * distance;

        // Calculate travel time
        double travelTimeHours = distance / vehicle.getMaxspeed();

        // Calculate the number of drivers needed
        int numberOfDrivers = travelTimeHours > maxHoursPerDriver ? 2 : 1;

        // Calculate food expenses
        double foodExpenses = foodCostPerDayPerDriver * numberOfDrivers;

        // Create and return the results map
        Map<String, Double> results = new HashMap<>();
        results.put("fuelConsumption", fuelConsumption);
        results.put("travelTimeHours", travelTimeHours);
        results.put("numberOfDrivers", (double) numberOfDrivers);
        results.put("foodExpenses", foodExpenses);

        return results;
    }

    private VehicleUtils() {
    }
}
