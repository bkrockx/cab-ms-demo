package com.demo.cab.ms.repositories;

import com.demo.cab.ms.entity.Vehicle;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends MongoRepository<Vehicle, String> {

  Vehicle findByLatAndLon(Double lat, Double lon);
  Vehicle findByVehicleNumber(String vehicleNumber);

}
