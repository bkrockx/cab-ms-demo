package com.demo.cab.ms.controllers;

import com.demo.cab.ms.entity.Driver;
import com.demo.cab.ms.interceptors.annotations.JwtSecure;
import com.demo.cab.ms.service.IDriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/driver")
public class DriverController {

  @Autowired
  private IDriverService driverService;

  @JwtSecure
  @RequestMapping(value = "/v1", method = RequestMethod.POST, produces = "application/json")
  public Driver register(@RequestBody(required = true) Driver driver) {
    return driverService.register(driver);
  }

  @JwtSecure
  @RequestMapping(value = "/v1/verify", method = RequestMethod.GET, produces = "application/json")
  public Boolean verify(@RequestParam(required = true) String id, @RequestParam(required = true) String password) {
    return driverService.verify(id,password);
  }

}
