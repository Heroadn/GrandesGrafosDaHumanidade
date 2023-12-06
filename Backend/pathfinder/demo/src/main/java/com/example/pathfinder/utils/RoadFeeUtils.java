package com.example.pathfinder.utils;

import com.example.pathfinder.enums.EnumRoadFee;
import com.example.pathfinder.roadfee.RoadFee;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class RoadFeeUtils {
    public static LinkedList<RoadFee>  getPathRoadFees(LinkedList<String> path, String vehicleType){
        LinkedList<RoadFee> pathRoadFees = new LinkedList<>();

        if (pathHasRoadFee("LAGES", "CORREIA PINTO", path)){
            pathRoadFees.add(new RoadFee("CORREIA PINTO", EnumRoadFee.getPriceByVehicle(vehicleType)));
        }

        if (pathHasRoadFee("SANTA CECÍLIA", "PONTE ALTA DO NORTE", path)){
            pathRoadFees.add(new RoadFee("SANTA CECÍLIA", EnumRoadFee.getPriceByVehicle(vehicleType)));
        }

        return pathRoadFees;
    }

    public static Boolean pathHasRoadFee(String start, String end, LinkedList<String> path){
        int pathLength = path.size()-1;
        int currentIndex = 0;

        for (String city: path){
            if (currentIndex < pathLength){ //Checks if it's the last city of the path
                if (city.equals(start) && path.get(currentIndex+1).equals(end)){
                    return true;
                } else if (city.equals(end) && path.get(currentIndex+1).equals(start)) {
                    return true;
                }
            }
            currentIndex++;
        }

        return false;
    }

    public static Double calculateTotalCost(LinkedList<RoadFee> roadFees) {
        Double totalCost = 0.0;

        for (RoadFee roadFee : roadFees) {
            totalCost += roadFee.getCost();
        }

        return totalCost;
    }
}
