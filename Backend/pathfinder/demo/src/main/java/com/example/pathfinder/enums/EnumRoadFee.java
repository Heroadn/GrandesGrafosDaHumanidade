package com.example.pathfinder.enums;

import java.util.Arrays;

public enum EnumRoadFee {

    CAR("Car", 7.3),
    TRUCK("Truck", 14.6),
    BUS("Bus", 14.6),
    MOTORCYCLE("Motorcycle", 3.65),
    MINIBUS("Minibus", 7.3);

    private final String vehicleType;
    private final Double price;

    EnumRoadFee(String vehicleType, Double price) {
        this.vehicleType = vehicleType;
        this.price = price;
    }

    public static Double getPriceByVehicle(String vehicleType) {
        return Arrays.stream(EnumRoadFee.values())
                .filter(t -> t.getVehicleType().equals(vehicleType))
                .findFirst()
                .map(EnumRoadFee::getPrice)
                .orElse(0.0);
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public Double getPrice() {
        return price;
    }

}
