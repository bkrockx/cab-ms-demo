package com.demo.cab.ms.controllers;

import com.demo.cab.ms.entity.Booking;
import com.demo.cab.ms.entity.Rider;
import com.demo.cab.ms.interceptors.annotations.JwtSecure;
import com.demo.cab.ms.service.IRiderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rider")
public class RiderController {

  @Autowired
  private IRiderService riderService;

  @JwtSecure
  @RequestMapping(value = "/v1", method = RequestMethod.POST, produces = "application/json")
  public Rider register(@RequestBody(required = true) Rider rider) {
    return riderService.register(rider);
  }

  @JwtSecure
  @RequestMapping(value = "/v1/verify", method = RequestMethod.GET, produces = "application/json")
  public Boolean verify(@RequestParam(required = true) String id, @RequestParam(required = true) String password) {
    return riderService.verify(id,password);
  }

}
