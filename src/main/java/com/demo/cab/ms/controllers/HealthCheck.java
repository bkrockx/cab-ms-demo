package com.demo.cab.ms.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheck {
    @RequestMapping("/healthcheck")
    @ResponseBody
    public String health() {
        return "ok";
    }
}
