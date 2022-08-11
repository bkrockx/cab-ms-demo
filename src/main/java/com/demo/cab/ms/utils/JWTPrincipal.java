package com.demo.cab.ms.utils;

import com.auth0.jwt.interfaces.Claim;
import com.demo.cab.ms.entity.Persona;
import com.demo.cab.ms.exceptions.BadRequest;
import java.security.Principal;
import java.util.Map;
import javax.security.auth.Subject;

public class JWTPrincipal implements Principal {

  private Persona persona;

  public JWTPrincipal(Map<String, Claim> resellerMap) {
    this.persona = Persona.builder()
        .phoneNumber(resellerMap.get("phoneNumber") != null ? resellerMap.get("phoneNumber").asString() : null)
        .name(resellerMap.get("name") != null ? resellerMap.get("name").asString() : null)
        .id(resellerMap.get("id") != null ? resellerMap.get("id").asString() : null)
        .build();
  }

  public Persona getPersona() {
    if (this.persona == null) {
      throw new BadRequest("Unauthorized User");
    }
    return this.persona;
  }

  public void setPersona(Persona persona) {
    this.persona = persona;
  }


  @Override
  public String getName() {
    return null;
  }

  @Override
  public boolean implies(Subject subject) {
    return false;
  }
}

