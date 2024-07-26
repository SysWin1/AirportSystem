package org.example.onesteponestamp.foreignvisitor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.example.onesteponestamp.common.DBConnectionManager;

@Getter
public class Foreigner {

  private String applicationId;
  private String passportNo;
  private String countryCode;
  private java.sql.Date visaExpiry;
  private String departureStatus;
  private String illegalStay;

  public Foreigner() {
  }

// 외국인 관리 - 기본 정보를 DB에서 받아와야함. -> 메서드 별 정보, 생성자는..
// priavte 한 field 값
// public 한 field 값  구별

  // 1. 외국인 목록 조회
  // Get all Foreigner records
  public static List<Foreigner> getAllForeigners() throws SQLException {
    String sql = "select * from foreignvisitor where departure_status='STAY' and visa_expiry_date>=trunc(sysdate) order by visa_expiry_date";
    List<Foreigner> foreigners = new ArrayList<>();
    try {
      Connection conn = DBConnectionManager.getInstance().getConnection();
      PreparedStatement pstmt = conn.prepareStatement(sql);
      ResultSet rs = pstmt.executeQuery();
      while (rs.next()) {
        Foreigner foreigner = new Foreigner();
        foreigner.applicationId = rs.getString("APPLY_NO");
        foreigner.passportNo = rs.getString("PASSPORT_NO");
        foreigner.countryCode = rs.getString("COUNTRY_CODE");
        foreigner.visaExpiry = rs.getDate("VISA_EXPIRY_DATE");
        foreigner.departureStatus = rs.getString("DEPARTURE_STATUS");
        foreigner.illegalStay = rs.getString("ILLEGAL_STAY");
        foreigners.add(foreigner);
      }
      conn.close();
    } catch (Exception e) {
    }

    return foreigners;
  }

  // 2. 불법 체류 외국인 목록 조회.
  //  우선 connection을 받아서 조회 쿼리 실행 => return 값을 리스트 형식으로 받기.
  public static List<Foreigner> getIllegalForeigners() throws SQLException {
    String sql = "select * from foreignvisitor where illegal_stay='ILLEGAL'";
    List<Foreigner> foreigners = new ArrayList<>();
    try {
      Connection conn = DBConnectionManager.getInstance().getConnection();
      PreparedStatement pstmt = conn.prepareStatement(sql);
      ResultSet rs = pstmt.executeQuery();
      while (rs.next()) {
        Foreigner foreigner = new Foreigner();
        foreigner.applicationId = rs.getString("APPLY_NO");
        foreigner.passportNo = rs.getString("PASSPORT_NO");
        foreigner.countryCode = rs.getString("COUNTRY_CODE");
        foreigner.visaExpiry = rs.getDate("VISA_EXPIRY_DATE");
        foreigner.departureStatus = rs.getString("DEPARTURE_STATUS");
        foreigner.illegalStay = rs.getString("ILLEGAL_STAY");
        foreigners.add(foreigner);
      }
      conn.close();
    } catch (Exception e) {
    }
    return foreigners;
  }

}
