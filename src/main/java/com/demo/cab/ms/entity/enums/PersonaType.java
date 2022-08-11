package com.demo.cab.ms.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PersonaType {

  Rider("Rider"),
  Driver("Driver");

  private String name;

}
