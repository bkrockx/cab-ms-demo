package com.demo.cab.ms.repositories;

import com.demo.cab.ms.entity.Rider;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RiderRepository extends MongoRepository<Rider, String> {

}
