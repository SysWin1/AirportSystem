package org.example.onesteponestamp.common;

import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Country {
  USA("United States", "SAFETY"),
  GBR("United Kingdom", "SAFETY"),
  JPN("Japan", "SAFETY"),
  FRA("France", "SAFETY"),
  VNM("Vietnam", "SAFETY"),
  KOR("South Korea", "SAFETY"),
  PRK("North Korea", "DANGER"),
  IRQ("Iraq", "DANGER"),
  RUS("Russia", "DANGER");

  private final String countryName;
  private final String isDanger;

  private static final Map<String, Country> NAME_TO_COUNTRY_MAP = new HashMap<>();

  static {
    for (Country c : Country.values()) {
      NAME_TO_COUNTRY_MAP.put(c.getCountryName(), c);
    }
  }

  /**
   * 나라 이름으로 Country enum 타입 찾기
   *
   * @param countryName 나라 이름(영어 풀네임)
   * @return Country(Enum)
   */
  public static Country getCountry(String countryName) {
    if (!NAME_TO_COUNTRY_MAP.containsKey(countryName)) {
      throw new IllegalArgumentException("Country code " + countryName + " not found");
    }
    return NAME_TO_COUNTRY_MAP.get(countryName);
  }
}
