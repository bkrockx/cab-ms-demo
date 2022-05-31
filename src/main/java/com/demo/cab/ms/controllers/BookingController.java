package com.demo.cab.ms.controllers;

import com.demo.cab.ms.entity.Booking;
import com.demo.cab.ms.interceptors.annotations.JwtSecure;
import com.demo.cab.ms.service.IBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/booking")
public class BookingController {

  @Autowired
  private IBookingService bookingService;


  @JwtSecure
  @RequestMapping(value = "/v1/initiate", method = RequestMethod.POST, produces = "application/json")
  public Booking initiateBooking(@RequestParam(required = true) String riderUserId,
      @RequestParam(required = true) Double srclat, @RequestParam(required = true) Double srclon,
      @RequestParam(required = true) Double destlat, @RequestParam(required = true) Double destlon, @RequestParam(required = true) Double distance) {
    return bookingService.initiateBooking(riderUserId,srclat,srclon,destlat,destlon, distance);
  }

  @JwtSecure
  @RequestMapping(value = "/v1/confirm", method = RequestMethod.POST, produces = "application/json")
  public Booking confirmBooking(@RequestParam(required = true) String bookingId,
      @RequestParam(required = true) String driverId) {
    return bookingService.confirmBooking(bookingId,driverId);
  }

  @JwtSecure
  @RequestMapping(value = "/v1/fair", method = RequestMethod.GET, produces = "application/json")
  public double getFairOfRide(@RequestParam(required = true) String bookingId) {
    return bookingService.getFairOfRide(bookingId);
  }

  @JwtSecure
  @RequestMapping(value = "/v1/details", method = RequestMethod.GET, produces = "application/json")
  public Booking getBookingDetails(@RequestParam(required = true) String bookingId) {
    return bookingService.getBookingDetails(bookingId);
  }

  @JwtSecure
  @RequestMapping(value = "/v1/update", method = RequestMethod.PUT, produces = "application/json")
  public Booking updateBooking(@RequestBody(required = true) Booking booking) {
    return bookingService.updateBooking(booking);
  }

  @JwtSecure
  @RequestMapping(value = "/v1/end", method = RequestMethod.PUT, produces = "application/json")
  public Boolean endRide(@RequestParam(required = true) String bookingId) {
    return bookingService.endTrip(System.currentTimeMillis(),bookingId);
  }





}


