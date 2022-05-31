package com.demo.cab.ms.service;


import com.demo.cab.ms.entity.Driver;

public interface IDriverService {

  Driver register(Driver driver);

  Boolean verify(String id, String password);

}
