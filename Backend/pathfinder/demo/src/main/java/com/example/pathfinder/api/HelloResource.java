package com.example.pathfinder.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloResource {

    @GetMapping("/")
    public String index() {
        return "Salve salve! Essa rota é só pra ver se tá funcionando de fato";
    }

}