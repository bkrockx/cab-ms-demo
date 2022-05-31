package com.demo.cab.ms.service.impl;

import com.demo.cab.ms.entity.Driver;
import com.demo.cab.ms.entity.Rider;
import com.demo.cab.ms.entity.enums.PersonaType;
import com.demo.cab.ms.exceptions.BadRequest;
import com.demo.cab.ms.repositories.DriverRepository;
import com.demo.cab.ms.service.IDriverService;
import com.demo.cab.ms.utils.PasswordUtil;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class DriverServiceImpl implements IDriverService {

  private static final Logger LOGGER = LoggerFactory.getLogger(DriverServiceImpl.class);

  @Autowired
  private DriverRepository driverRepository;

  @Autowired
  private PasswordUtil passwordUtil;

  @Override
  public Driver register(Driver driver) {
    if(ObjectUtils.isEmpty(driver) || ObjectUtils.isEmpty(driver.getPhoneNumber())) {
      throw new BadRequest("invalid driver registration request body");
    }
    LOGGER.info("registering driver with phone number {} ",driver.getPhoneNumber());
    driver.setPersonaType(PersonaType.Driver);
    driver.setPassword(passwordUtil.hashPassword(driver.getPassword()));
    return driverRepository.save(driver);
  }

  @Override
  public Boolean verify(String id, String password) {
    LOGGER.info("verifying rider with id  {} ",id);
    Optional<Driver> driverOptional = driverRepository.findById(id);
    if(!driverOptional.isPresent()){
      throw new BadRequest("rider not found");
    }
    return passwordUtil.checkPassword(password,driverOptional.get().getPassword());
  }

}
