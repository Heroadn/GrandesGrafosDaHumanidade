package com.example.pathfinder.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloResource {
    @GetMapping("/hello")
    public String index() {
        return "Здравствуйте мои товарищ! Как дела? Вся власть Советам!";
    }

}