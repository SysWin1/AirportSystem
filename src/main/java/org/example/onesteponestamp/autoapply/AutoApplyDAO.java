package org.example.onesteponestamp.autoapply;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.example.onesteponestamp.common.Country;
import org.example.onesteponestamp.common.DBConnectionManager;
import org.example.onesteponestamp.common.VisaType;

/**
 * 자동 출입국 신청서 form 데이터 저장
 */
public class AutoApplyDAO {
  private Connection conn;

  public AutoApplyDAO() {
    conn = DBConnectionManager.getInstance().getConnection();
  }

  public void insertAutoApply(
      String applyNo,
      String passportNo,
      Country countryCode,
      String englishName,
      String gender,
      LocalDate issueDate,
      LocalDate expiryDate,
      LocalDate birth,
      VisaType visaType,
      String inout,
      Country inoutCountry,
      LocalDate expectedInOutDate,
      LocalDateTime createdAt
  ){

    String sql = "{call autoapplyinsert(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";

    try {
      CallableStatement cstmt = conn.prepareCall(sql);

      cstmt.setString(1, applyNo);
      cstmt.setString(2, passportNo);
      cstmt.setString(3, countryCode.getCountryCode());
      cstmt.setString(4, englishName);
      cstmt.setString(5,gender);
      cstmt.setDate(6, Date.valueOf(issueDate));
      cstmt.setDate(7, Date.valueOf(expiryDate));
      cstmt.setDate(8, Date.valueOf(birth));
      cstmt.setString(9, visaType.name());
      cstmt.setString(10, inout);
      cstmt.setString(11, inoutCountry.getCountryCode());
      cstmt.setDate(12, Date.valueOf(expectedInOutDate));
      cstmt.setTimestamp(13, Timestamp.valueOf(createdAt));
      
      cstmt.execute(); // 실행

    } catch (SQLException e) {
      e.printStackTrace();
    } finally {

    }
  }
}
