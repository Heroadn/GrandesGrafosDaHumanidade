package com.example.pathfinder.api;

import com.example.pathfinder.utils.VehicleUtils;
import com.example.pathfinder.vehicle.TravelCost;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class ExpensesResource {

    @GetMapping(value = "/expenses", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TravelCost> Expenses(@RequestParam(value = "distance") double distance,
                                               @RequestParam(value = "vehicle_type") String vehicletype) {

        try {
            return ResponseEntity.ok(VehicleUtils.gettraveldata(distance, vehicletype));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }


}

