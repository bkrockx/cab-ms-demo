package com.demo.cab.ms.service.impl;

import com.demo.cab.ms.entity.Vehicle;
import com.demo.cab.ms.exceptions.BadRequest;
import com.demo.cab.ms.repositories.VehicleRepository;
import com.demo.cab.ms.service.IVehicleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class VehicleServiceImpl implements IVehicleService {

  private static final Logger LOGGER = LoggerFactory.getLogger(VehicleServiceImpl.class);

  @Autowired
  private VehicleRepository vehicleRepository;

  @Override
  public Vehicle registerVehicle(Vehicle vehicle) {
    if(ObjectUtils.isEmpty(vehicle) || ObjectUtils.isEmpty(vehicle.getVehicleNumber())){
      throw new BadRequest("invalid vehicle registration request body");
    }
    LOGGER.info("registering vehicle for vehicle number {}", vehicle.getVehicleNumber());
    vehicle = vehicleRepository.save(vehicle);
    return vehicle;
  }

  @Override
  public Vehicle update(Vehicle vehicle) {
    if(ObjectUtils.isEmpty(vehicle) || ObjectUtils.isEmpty(vehicle.getVehicleNumber())){
      throw new BadRequest("invalid vehicle update request body");
    }
    LOGGER.info("updating vehicle for vehicle number {}", vehicle.getVehicleNumber());
    vehicle = vehicleRepository.findByVehicleNumber(vehicle.getVehicleNumber());
    return vehicleRepository.save(vehicle);
  }

  @Override
  public Vehicle updateLocation(Vehicle vehicle) {
    if(ObjectUtils.isEmpty(vehicle) || ObjectUtils.isEmpty(vehicle.getLat()) || ObjectUtils.isEmpty(vehicle.getLon())){
      throw new BadRequest("invalid vehicle registration request body");
    }
    LOGGER.info("updating location for vehicle for vehicle number {}", vehicle.getVehicleNumber());
    vehicle = vehicleRepository.save(vehicle);
    return vehicle;
  }

  @Override
  public Vehicle find(Double lat, Double lon) {
    if(ObjectUtils.isEmpty(lat) || ObjectUtils.isEmpty(lon)){
      throw new BadRequest("invalid lat or lon information");
    }
    LOGGER.info("searching for vehicles by lat {} and lon {}",lat,lon);
    return vehicleRepository.findByLatAndLon(lat,lon);
  }

  @Override
  public Vehicle findByVehicleNumber(String vehicleNumber) {
    if(ObjectUtils.isEmpty(vehicleNumber)){
      throw new BadRequest("invalid vehicleNumber");
    }
    LOGGER.info("searching for vehicles by vehicle number {}",vehicleNumber);
    return vehicleRepository.findByVehicleNumber(vehicleNumber);
  }

}
