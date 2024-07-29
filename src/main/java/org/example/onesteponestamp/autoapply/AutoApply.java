package org.example.onesteponestamp.autoapply;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.example.onesteponestamp.common.Country;
import org.example.onesteponestamp.common.VisaType;

@Getter
@ToString
@Builder
@AllArgsConstructor
public class AutoApply {

  private String applyNo;
  private String passportNo;
  private Country countryCode;
  private String englishName;
  private String gender;
  private LocalDate issueDate;
  private LocalDate expiryDate;
  private LocalDate birth;
  private VisaType visaType;
  private String inout;
  private Country inoutCountry;
  private LocalDate expectedInOutDate;
  private String approvalStatus;
  private String rejectReason;
  private LocalDateTime createdAt;
}
