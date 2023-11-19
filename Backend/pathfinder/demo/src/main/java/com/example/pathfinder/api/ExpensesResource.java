package com.example.pathfinder.api;

import com.example.pathfinder.vehicle.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/expenses")
public class ExpensesResource {

    public @GetMapping(value = "/all",produces = MediaType.APPLICATION_JSON_VALUE)
    String Expenses(@RequestParam(value ="distance")
                    double distance,
                    @RequestParam(value ="vehicle_type") String vehicletype)
    {

        //return la;// retorna o JSON para o usuario
        return null;
    }
    private int getFuelcomsumption(double distance, String vehicletype){
        /*
       Object<T> selectedvehicle = null;
        switch (vehicletype){
            case "minibus":
                selectedvehicle = minibus;
            case "bus":
                selectedvehicle = bus;
            case "car":
                selectedvehicle = car;
            case "motorcycle":
                selectedvehicle = motorcycle;
            case "truck":
                selectedvehicle = truck;
            default:
                raise Invalidvalue;
        }
        gasexpend = distance/ selectedvehicle.getFuelconsumption;*/

        return 0;
    }

}

