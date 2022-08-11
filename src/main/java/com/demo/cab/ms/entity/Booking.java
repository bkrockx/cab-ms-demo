package com.demo.cab.ms.entity;

import com.demo.cab.ms.entity.enums.BookingStatus;
import java.io.Serializable;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Builder
@Document(collection = "booking")
public class Booking implements Serializable {

  @Id
  protected String id;
  private String riderUserId;
  private String driverUserId;
  private String vehicleNumber;
  private long startTime;
  private long endTime;
  private Double sourceLat;
  private Double sourceLon;
  private Double destinationLat;
  private Double destinationLon;
  /* Ideally distance should be created by using some services which can calculate distance based on lat,lon : google map apis */
  private Double distance;
  private BookingStatus bookingStatus;

}
