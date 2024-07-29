package org.example.onesteponestamp.foreigner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.example.onesteponestamp.common.DBConnectionManager;

@Builder
@Getter
public class ForeignerDAO {

  // 1. 외국인 목록 조회
  // Get all Foreigner records
  public static List<ForeignerDTO> getAllForeigners() {
    String sql = "select * from foreignvisitor where departure_status='STAY'";
    List<ForeignerDTO> foreigners = new ArrayList<>();
    try {
      Connection conn = DBConnectionManager.getInstance().getConnection();
      PreparedStatement pstmt = conn.prepareStatement(sql);
      ResultSet rs = pstmt.executeQuery();
      while (rs.next()) {
        foreigners.add(
            ForeignerDTO.builder()
                .applicationId(rs.getString("APPLY_NO"))
                .passportNo(rs.getString("PASSPORT_NO"))
                .countryCode(rs.getString("COUNTRY_CODE"))
                .visaExpiry(rs.getDate("VISA_EXPIRY_DATE"))
                .departureStatus(rs.getString("DEPARTURE_STATUS"))
                .illegalStay(rs.getString("ILLEGAL_STAY"))
                .build()
        );
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return foreigners;
  }

  // 2. 불법 체류 외국인 목록 조회.
  //  우선 connection을 받아서 조회 쿼리 실행 => return 값을 리스트 형식으로 받기.
  public static List<ForeignerDTO> getIllegalForeigners() {
    String sql = "select * from foreignvisitor where illegal_stay='ILLEGAL'";
    List<ForeignerDTO> foreigners = new ArrayList<>();
    try {
      Connection conn = DBConnectionManager.getInstance().getConnection();
      PreparedStatement pstmt = conn.prepareStatement(sql);
      ResultSet rs = pstmt.executeQuery();
      while (rs.next()) {
        foreigners.add(
            ForeignerDTO.builder()
                .applicationId(rs.getString("APPLY_NO"))
                .passportNo(rs.getString("PASSPORT_NO"))
                .countryCode(rs.getString("COUNTRY_CODE"))
                .visaExpiry(rs.getDate("VISA_EXPIRY_DATE"))
                .departureStatus(rs.getString("DEPARTURE_STATUS"))
                .illegalStay(rs.getString("ILLEGAL_STAY"))
                .build()
        );
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return foreigners;
  }
}
