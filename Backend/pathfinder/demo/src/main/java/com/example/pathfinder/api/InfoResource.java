package com.example.pathfinder.api;

import com.example.pathfinder.response.VehicleResponse;
import com.example.pathfinder.services.VehiclesService;

import com.example.pathfinder.vehicle.Vehicle;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;

@RestController
@RequestMapping("/info")
public class InfoResource {
    @Autowired
    private VehiclesService vehiclesService;

    @GetMapping("/vehicles")
    public ResponseEntity<VehicleResponse> get_vehicles() {
        LinkedList<Vehicle> vehicles = vehiclesService.getVehicles();
        VehicleResponse vp = new VehicleResponse(vehicles);
        return ResponseEntity.status(HttpStatus.OK).body(vp);
    }
}