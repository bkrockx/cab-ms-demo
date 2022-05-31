package com.demo.cab.ms.service;

import com.demo.cab.ms.entity.Rider;

public interface IRiderService {

  Rider register(Rider rider);

  Boolean verify(String id, String password);

}
