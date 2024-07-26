package org.example.onesteponestamp.autoapply;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.example.onesteponestamp.common.Country;
import org.example.onesteponestamp.common.VisaType;

public class AutoApplyService {

  private final AutoApplyDAO autoApplyDAO;

  public AutoApplyService() {
    this.autoApplyDAO = new AutoApplyDAO();
  }

  /**
   * 자동 입출국 신청서 생성 DAO 호출
   *
   * @return 신청 번호 uuid -> 팝업창에 넣기
   */
  public String createAutoApply(String passportNo, Country countryCode, String englishName,
      String gender, LocalDate issueDate, LocalDate expiryDate, LocalDate birth, VisaType visaType,
      String inout, Country inoutCountry, LocalDate expectedInOutDate) {

    // 신청서 번호 (uuid -> base62 인코딩로 변환)
    String applyNo = UUIDShortener.generateShortUUID();
    LocalDateTime createdAt = LocalDateTime.now(); // 신청서 생성일

    // 신청서 제출
    autoApplyDAO.insertAutoApply(applyNo, passportNo, countryCode, englishName, gender, issueDate,
        expiryDate, birth, visaType, inout, inoutCountry, expectedInOutDate, createdAt);

    return applyNo;
  }
}
