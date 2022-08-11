package com.demo.cab.ms.controllers;

import com.demo.cab.ms.entity.Booking;
import com.demo.cab.ms.entity.Vehicle;
import com.demo.cab.ms.interceptors.annotations.JwtSecure;
import com.demo.cab.ms.service.IVehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vehicle")
public class VehicleController {

  @Autowired
  private IVehicleService vehicleService;

  @JwtSecure
  @RequestMapping(value = "/v1", method = RequestMethod.POST, produces = "application/json")
  public Vehicle registerVehicle(@RequestBody(required = true) Vehicle vehicle) {
    return vehicleService.registerVehicle(vehicle);
  }

  @JwtSecure
  @RequestMapping(value = "/v1/location", method = RequestMethod.POST, produces = "application/json")
  public Vehicle updateLocation(@RequestBody(required = true) Vehicle vehicle) {
    return vehicleService.updateLocation(vehicle);
  }

  @JwtSecure
  @RequestMapping(value = "/v1/find", method = RequestMethod.GET, produces = "application/json")
  public Vehicle find(@RequestParam(required = true) Double lat,
      @RequestParam(required = true) Double lon) {
    return vehicleService.find(lat,lon);
  }

}
