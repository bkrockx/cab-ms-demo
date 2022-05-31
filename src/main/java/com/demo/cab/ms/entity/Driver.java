package com.demo.cab.ms.entity;

import com.demo.cab.ms.entity.enums.PersonaType;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "users")
public class Driver extends Persona implements Serializable {

  Driver(String id, String name, String phoneNumber, String countryCode,
      PersonaType personaType, String password) {
    super(id, name, phoneNumber, countryCode, personaType, password);
  }

}
