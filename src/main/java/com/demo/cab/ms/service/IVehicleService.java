package com.demo.cab.ms.service;

import com.demo.cab.ms.entity.Vehicle;

public interface IVehicleService {

  Vehicle registerVehicle(Vehicle vehicle);

  Vehicle update(Vehicle vehicle);

  Vehicle updateLocation(Vehicle vehicle);

  Vehicle find(Double lat, Double lon);

  Vehicle findByVehicleNumber(String vehicleNumber);

}
