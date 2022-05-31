package com.demo.cab.ms.service.impl;

import com.demo.cab.ms.entity.Booking;
import com.demo.cab.ms.entity.Vehicle;
import com.demo.cab.ms.entity.enums.BookingStatus;
import com.demo.cab.ms.exceptions.BadRequest;
import com.demo.cab.ms.repositories.BookingRepository;
import com.demo.cab.ms.service.IBookingService;
import com.demo.cab.ms.service.IVehicleService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class BookingServiceImpl implements IBookingService {


  private static final Logger LOGGER = LoggerFactory.getLogger(BookingServiceImpl.class);

  @Autowired
  private BookingRepository bookingRepository;

  @Autowired
  private IVehicleService vehicleService;

  private static final double BOOKING_FEE = 20.0;

  @Override
  public Booking initiateBooking(String riderUserId, Double srclat, Double srclon, Double destlat, Double destlon, Double distance) {
    if(ObjectUtils.isEmpty(riderUserId) || ObjectUtils.isEmpty(srclat) || ObjectUtils.isEmpty(srclon)){
      throw new BadRequest("invalid booking request. Please check riderId, lat, long");
    }
    LOGGER.info("initiating booking for rider {}", riderUserId);
    Booking booking = Booking.builder().riderUserId(riderUserId)
        .startTime(System.currentTimeMillis()).sourceLat(srclat).sourceLon(srclon).distance(distance).
            destinationLat(destlat).destinationLon(destlon).
            bookingStatus(BookingStatus.INITIATED).build();
    LOGGER.info("saving booking for rider {}", riderUserId);
    return bookingRepository.save(booking);
  }

  @Override
  public Booking confirmBooking(String bookingId, String driverId) {
    if(ObjectUtils.isEmpty(bookingId) || ObjectUtils.isEmpty(driverId)){
      throw new BadRequest("invalid booking confirmation request. Please check bookingId, driverId");
    }
    LOGGER.info("confirming booking {}", bookingId);
    Optional<Booking> booking = bookingRepository.findById(bookingId);
    if(!booking.isPresent()){
      throw new BadRequest("booking not found");
    }
    Booking currentBooking = booking.get();
    Vehicle vehicle = vehicleService.find(currentBooking.getSourceLat(),currentBooking.getSourceLon());
    currentBooking.setVehicleNumber(vehicle.getVehicleNumber());
    currentBooking.setDriverUserId(vehicle.getDriverId());
    currentBooking.setBookingStatus(BookingStatus.CONFIRMED);
    vehicle.setAvailable(false);
    LOGGER.info("saving booking for driver {}", driverId);
    return bookingRepository.save(currentBooking);
  }

  @Override
  public double getFairOfRide(String bookingId) {
    if(ObjectUtils.isEmpty(bookingId)){
      throw new BadRequest("invalid bookingId");
    }
    Optional<Booking> booking = bookingRepository.findById(bookingId);
    if(!booking.isPresent()){
      LOGGER.error("fetching booking details for bookingId {}", bookingId);
      throw new BadRequest("booking not found");
    }
    Booking currentBooking = booking.get();
    double distance = currentBooking.getDistance();
    double baseFair = (5*distance);
    double cgst = 0.025 * baseFair;
    double sgst = 0.025 * baseFair;
    double fair = BOOKING_FEE + baseFair + cgst + sgst;
    return fair;
  }

  @Override
  public Booking getBookingDetails(String bookingId) {
    LOGGER.info("fetching booking details for bookingId {}", bookingId);
    Optional<Booking> booking = bookingRepository.findById(bookingId);
    if(!booking.isPresent()){
      throw new BadRequest("booking not found");
    }
    Booking bookingFromDb = booking.get();
    if(!BookingStatus.CONFIRMED.equals(bookingFromDb.getBookingStatus())){
      throw new BadRequest("booking not confirmed");
    }
    return bookingFromDb;
  }

  @Override
  public Booking updateBooking(Booking booking) {
    if(ObjectUtils.isEmpty(booking) || ObjectUtils.isEmpty(booking.getId())){
      throw new BadRequest("invalid booking update request. Please check bookingId");
    }
    LOGGER.info("updating booking {}", booking.getId());
    Optional<Booking> currentBooking = bookingRepository.findById(booking.getId());
    if(!currentBooking.isPresent()){
      throw new BadRequest("booking not found");
    }
    Booking bookingFromDb = currentBooking.get();
    if(!ObjectUtils.isEmpty(booking.getDestinationLat()))
      bookingFromDb.setDestinationLat(booking.getDestinationLat());
    if(!ObjectUtils.isEmpty(booking.getDestinationLat()))
      bookingFromDb.setDestinationLon(booking.getDestinationLon());
    return bookingRepository.save(bookingFromDb);
  }

  @Override
  public Boolean endTrip(Long timeStamp, String bookingId) {
    if(ObjectUtils.isEmpty(bookingId)){
      throw new BadRequest("invalid bookingId.");
    }
    Optional<Booking> currentBooking = bookingRepository.findById(bookingId);
    if(!currentBooking.isPresent()){
      throw new BadRequest("booking not found");
    }
    Booking bookingFromDb = currentBooking.get();
    bookingFromDb.setEndTime(timeStamp);
    bookingFromDb.setBookingStatus(BookingStatus.ENDED);
    String vehicleNumberInThisBooking = bookingFromDb.getVehicleNumber();
    Vehicle vehicleInThisBooking = vehicleService.findByVehicleNumber(vehicleNumberInThisBooking);
    vehicleInThisBooking.setAvailable(true);
    vehicleService.update(vehicleInThisBooking);
    bookingRepository.save(bookingFromDb);
    return true;
  }


}
