package org.example.onesteponestamp.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Country {
  USA("USA","United States", 0),
  GBR("GBR", "United Kingdom", 0),
  JPN("JPN","Japan", 0),
  FRA("FRA","France", 0),
  VNM("VNM","Vietnam", 0),
  KOR("KOR","South Korea", 0),
  PRK("PRK","North Korea", 1),
  IRQ("IRQ","Iraq", 1),
  RUS("RUS","Russia", 1);

  private final String countryCode;
  private final String countryName;
  private final int dangerLevel;
}
