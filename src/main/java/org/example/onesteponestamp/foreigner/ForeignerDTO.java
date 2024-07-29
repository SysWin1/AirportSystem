package org.example.onesteponestamp.foreigner;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ForeignerDTO {

  private String applicationId;
  private String passportNo;
  private String countryCode;
  private java.sql.Date visaExpiry;
  private String departureStatus;
  private String illegalStay;
}
