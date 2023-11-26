package com.example.pathfinder.api;

import com.example.pathfinder.utils.VehicleUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/expenses")
public class ExpensesResource {





    public @GetMapping(value = "/all",produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity <Map<String, java.lang.Object>> Expenses(@RequestParam(value ="distance")
                    double distance,
                    @RequestParam(value ="vehicle_type") String vehicletype)
    {

       try {
           Map<String, Object> travelData = VehicleUtils.gettraveldata(distance,vehicletype);


           return (ResponseEntity<Map<String, Object>>) travelData;// retorna o JSON para o usuario

       }catch (IllegalArgumentException e) {
           return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("invalid param");

       }
        return null;
    }


}

