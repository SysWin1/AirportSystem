package org.example.onesteponestamp.autoapply;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.example.onesteponestamp.common.Country;
import org.example.onesteponestamp.common.DBConnectionManager;
import org.example.onesteponestamp.common.VisaType;

/**
 * 자동 출입국 신청서 form 데이터 저장
 */
public class AutoApplyDAO {

  private final Connection conn;

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
  ) {

    String sql = "{call autoapplyinsert(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";

    try {
      CallableStatement cstmt = conn.prepareCall(sql);

      cstmt.setString(1, applyNo);
      cstmt.setString(2, passportNo);
      cstmt.setString(3, countryCode.toString());
      cstmt.setString(4, englishName);
      cstmt.setString(5, gender);
      cstmt.setDate(6, Date.valueOf(issueDate));
      cstmt.setDate(7, Date.valueOf(expiryDate));
      cstmt.setDate(8, Date.valueOf(birth));
      cstmt.setString(9, visaType.name());
      cstmt.setString(10, inout);
      cstmt.setString(11, inoutCountry.toString());
      cstmt.setDate(12, Date.valueOf(expectedInOutDate));
      cstmt.setTimestamp(13, Timestamp.valueOf(createdAt));

      cstmt.execute(); // 실행

      cstmt.close();
      conn.close();

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * 자동 출입국 신청 결과 목록 조회 Query
   *
   * @param passportNo 여권번호
   * @param country    나라 enum
   * @return 신청 결과 List.
   */
  public List<AutoApplyDTO> selectAutoApply(String passportNo, Country country) {

    String sql = "select * from autoapply where passport_no = ? and country_code = ?";
    List<AutoApplyDTO> list = new ArrayList<>();

    try {
      PreparedStatement pstmt = conn.prepareStatement(sql);

      pstmt.setString(1, passportNo);
      pstmt.setString(2, country.toString());

      ResultSet rs = pstmt.executeQuery();
      while (rs.next()) {
        list.add(AutoApplyDTO.builder()
            .applyNo(rs.getString("APPLY_NO"))
            .englishName(rs.getString("ENGLISH_NAME"))
            .gender(rs.getString("GENDER"))
            .visaType(VisaType.valueOf(rs.getString("VISA_TYPE")))
            .inout(rs.getString("INOUT"))
            .inoutCountry(Country.valueOf(rs.getString("INOUT_COUNTRY")))
            .expectedInOutDate(rs.getDate("EXPECTED_INOUT_DATE").toLocalDate())
            .approvalStatus(rs.getString("APPROVAL_STATUS"))
            .rejectReason(rs.getString("REJECT_REASON"))
            .createdAt(rs.getTimestamp("CREATED_AT").toLocalDateTime())
            .build()
        );
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }

    return list;
  }
}
