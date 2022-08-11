package com.demo.cab.ms.entity;


import com.demo.cab.ms.entity.enums.PersonaType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Builder
@Document(collection = "users")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Persona {

  @Id
  private String id;
  private String name;
  private String phoneNumber;
  private String countryCode;
  private PersonaType personaType;
  private String password;

}
