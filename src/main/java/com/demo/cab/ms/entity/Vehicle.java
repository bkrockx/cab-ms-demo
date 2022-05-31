package com.demo.cab.ms.entity;

import java.io.Serializable;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Builder
@Document(collection = "vehicle")
public class Vehicle implements Serializable {

  @Id
  private String vehicleNumber;
  private Double lat;
  private Double lon;
  private String type;
  private boolean isAvailable;
  private String driverId;

}
