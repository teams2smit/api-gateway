package com.pms.gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallBackController {

    @GetMapping("/stock-service-fallback")
    public String stockServiceFallback(){
        return "Stock service is down at this time!!";
    }

    @GetMapping("/auth-service-fallback")
    public String authorizationServiceFallback(){
        return "Authorization service is down at this time!!";
    }

    @GetMapping("/supply-service-fallback")
    public String supplyServiceFallback(){
        return "Supply service is down at this time!!";
    }

    @GetMapping("/schedule-service-fallback")
    public String scheduleServiceFallback(){
        return "Schedule service is down at this time!!";
    }
}
