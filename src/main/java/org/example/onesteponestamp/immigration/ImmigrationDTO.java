package org.example.onesteponestamp.immigration;

import java.sql.Date;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ImmigrationDTO {

  private String applyNo;
  private String passportNo;
  private String countryCode;
  private String inOut;
  private Date inOutDate;
  private String visaType;
  private String inOutCountry;
}
