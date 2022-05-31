package com.demo.cab.ms.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.demo.cab.ms.entity.Persona;
import com.demo.cab.ms.exceptions.BadRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Slf4j
@Service
public class JwtService {


  @Value("${jwt.secret}")
  private String secret;


  public String generateTokenForPersona(Persona persona) {
    Algorithm algorithm = Algorithm.HMAC512(secret);
    SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
    timeFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    if (ObjectUtils.isEmpty(persona)) {
      throw new BadRequest("Invalid JWT");
    }
    if (ObjectUtils.isEmpty(persona)) {
      throw new BadRequest("Invalid JWT  user not present");
    }
    LOGGER.debug("creating jwt token for user {} while enriching the token", persona);
    if (ObjectUtils.isEmpty(persona.getPhoneNumber())) {
      LOGGER.error("Throwing bad request exception while enriching the token since phone number of the user is null");
      throw new BadRequest("administrator has phone number as null which is expected to be non null");
    }
    String decodedJWT = JWT.create()
        .withClaim("mobile", persona.getPhoneNumber())
        .withClaim("id", persona.getId())
        .withClaim("name", persona.getName())
        .withClaim("time_stamp", timeFormat.format(new Date()))
        .sign(algorithm);
    LOGGER.debug("Created jwt for administrator {},The token created is {}", persona, decodedJWT);
    return decodedJWT;
  }

}

