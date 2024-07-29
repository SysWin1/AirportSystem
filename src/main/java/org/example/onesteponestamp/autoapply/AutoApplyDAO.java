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
import java.time.LocalTime;
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
  public List<UserAutoApplyDTO> selectAutoApply(String passportNo, Country country) {

    String sql = "select * from autoapply where passport_no = ? and country_code = ?";
    List<UserAutoApplyDTO> list = new ArrayList<>();

    try {
      PreparedStatement pstmt = conn.prepareStatement(sql);

      pstmt.setString(1, passportNo);
      pstmt.setString(2, country.toString());

      ResultSet rs = pstmt.executeQuery();
      getResults(rs, list);

      rs.close();
      pstmt.close();

    } catch (SQLException e) {
      e.printStackTrace();
    }

    return list;
  }

  public List<UserAutoApplyDTO> adminSelectAutoApply(String countryCode, String inout, LocalDate date,
      String searchKeyword) {

    List<UserAutoApplyDTO> list;

    StringBuilder sql = new StringBuilder();
    sql.append("select * from autoapply where created_at between ? and ?");

    // 전체 vs 내국인 vs 외국인
    if (countryCode.equals("KOR")) {
      sql.append(" and country_code = 'KOR' and country_code is not null");
    } else if (countryCode != null) {
      sql.append(" and country_code != 'KOR' and country_code is not null");
    }

    // 전체 vs 입국 vs 출국
    if (inout.equals("IN")) {
      sql.append(" and inout = 'IN'");
    } else if (inout.equals("OUT")) {
      sql.append(" and inout = 'OUT'");
    }

    // 신청명 + 여권번호 + 영문명 or 조회
    if (searchKeyword != null) {
      sql.append(" and (apply_no like ? OR passport_no LIKE ? OR english_name LIKE ?) ");
    }

    try (PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
      System.out.println(sql.toString());

      // 신청일자에 의해 조회 됨.
      pstmt.setTimestamp(1, Timestamp.valueOf(date.atStartOfDay()));
      pstmt.setTimestamp(2, Timestamp.valueOf(date.atTime(LocalTime.MAX)));
      pstmt.setString(3, "%" + searchKeyword + "%");
      pstmt.setString(4, "%" + searchKeyword + "%");
      pstmt.setString(5, "%" + searchKeyword + "%");

      ResultSet rs = pstmt.executeQuery();
      list = new ArrayList<>();
      getResults(rs, list);

      rs.close();

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return list;
  }

  private void getResults(ResultSet rs, List<UserAutoApplyDTO> list) throws SQLException {
    while (rs.next()) {
      list.add(
          UserAutoApplyDTO.builder()
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
  }
}
