package com.demo.cab.ms.service.impl;

import com.demo.cab.ms.entity.Rider;
import com.demo.cab.ms.entity.enums.PersonaType;
import com.demo.cab.ms.exceptions.BadRequest;
import com.demo.cab.ms.repositories.RiderRepository;
import com.demo.cab.ms.service.IRiderService;
import com.demo.cab.ms.utils.PasswordUtil;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class RiderServiceImpl implements IRiderService {


  private static final Logger LOGGER = LoggerFactory.getLogger(RiderServiceImpl.class);

  @Autowired
  private RiderRepository riderRepository;

  @Autowired
  private PasswordUtil passwordUtil;

  @Override
  public Rider register(Rider rider) {
    if(ObjectUtils.isEmpty(rider) || ObjectUtils.isEmpty(rider.getPhoneNumber())) {
      throw new BadRequest("invalid rider registration request body");
    }
    LOGGER.info("registering rider with phone number {} ",rider.getPhoneNumber());
    rider.setPersonaType(PersonaType.Rider);
    rider.setPassword(passwordUtil.hashPassword(rider.getPassword()));
    return riderRepository.save(rider);
  }

  @Override
  public Boolean verify(String id, String password) {
    LOGGER.info("verifying rider with id  {} ",id);
    Optional<Rider> riderOptional = riderRepository.findById(id);
    if(!riderOptional.isPresent()){
      throw new BadRequest("rider not found");
    }
    return passwordUtil.checkPassword(password,riderOptional.get().getPassword());
  }
}
