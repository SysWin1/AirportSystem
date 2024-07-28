package org.example.onesteponestamp.immigration;

import java.sql.Date;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ImmigrationDTO {

  private String applyNo;
  private String passportNo;
  private String countryCode;
  private String inOut;
  private LocalDate inOutDate;
  private String visaType;
  private String inOutCountry;
}
