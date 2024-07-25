package org.example.onesteponestamp.autoapply;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.example.onesteponestamp.common.Country;
import org.example.onesteponestamp.common.VisaType;

public class AutoApplyService {
  private AutoApplyDAO autoApplyDAO;

  public AutoApplyService() {
    this.autoApplyDAO = new AutoApplyDAO();
  }

  public void createAutoApply(String passportNo, Country countryCode, String englishName, String gender,
      LocalDate issueDate, LocalDate expiryDate, LocalDate birth, VisaType visaType, String inout,
      Country inoutCountry, LocalDate expectedInOutDate) {

    // 신청서 번호 (uuid -> base62 인코딩로 변환)
    String applyNo = UUIDShortener.generateShortUUID();
    LocalDateTime createdAt = LocalDateTime.now(); // 신청서 생성일

    // 신청서 제출
    autoApplyDAO.insertAutoApply(applyNo, passportNo, countryCode, englishName, gender, issueDate,
        expiryDate, birth, visaType, inout, inoutCountry, expectedInOutDate, createdAt);

    // todo : 제출된 신청서 보여주기.
    // 이거 없으면 사용자는 신청번호를 알 수 없어서 조회할 수 없음.

  }
}
