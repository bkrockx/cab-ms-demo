package com.demo.cab.ms.entity;

import com.demo.cab.ms.entity.enums.PersonaType;
import java.io.Serializable;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "users")
public class Rider extends Persona implements Serializable {

  private List<String> bookingId;

  @Builder(builderMethodName = "riderBuilder")
  public Rider(String id, String name, String phoneNumber, String countryCode, PersonaType personaType,String password,
      List<String> bookingId) {
    super(id, name, phoneNumber, countryCode,personaType,password);
    this.bookingId = bookingId;
  }

}
