package org.example.onesteponestamp.autoapply;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.example.onesteponestamp.common.Country;
import org.example.onesteponestamp.common.VisaType;

/**
 * 자동 입출국 신청서 목록 조회 시 dto.
 */
@Getter
@Builder
@ToString
@AllArgsConstructor
public class UserAutoApplyDTO {

  private String applyNo;
  private String englishName;
  private String gender;
  private VisaType visaType;
  private String inout;
  private Country inoutCountry;
  private LocalDate expectedInOutDate;
  private String approvalStatus;
  private String rejectReason;
  private LocalDateTime createdAt;
}
