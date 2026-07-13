package com.taskboard.kanban_backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// @RestController tells Spring Boot that this class is ready to handle web requests
// and will return data (like text or JSON) directly to the browser.
@RestController
public class HealthCheckController {

    // @GetMapping("/") maps a standard browser web request (HTTP GET) at the root URL
    // straight to this specific method.
    @GetMapping("/")
    public String healthCheck() {
        return "The Kanban Backend Server is up and running perfectly!";
    }
}
