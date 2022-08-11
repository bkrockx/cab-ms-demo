package com.demo.cab.ms.service;

import com.demo.cab.ms.entity.Booking;

public interface IBookingService {

  Booking initiateBooking(String riderUserId, Double srclat, Double srclon, Double destlat, Double destlon, Double distance);

  Booking confirmBooking(String bookingId, String driverId);

  double getFairOfRide(String bookingId);

  Booking getBookingDetails(String bookingId);

  Booking updateBooking(Booking booking);

  Boolean endTrip(Long timeStamp, String bookingId);

}
