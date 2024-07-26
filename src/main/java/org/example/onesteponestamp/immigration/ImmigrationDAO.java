package org.example.onesteponestamp.immigration;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.example.onesteponestamp.common.DBConnectionManager;

public class ImmigrationDAO implements ImmigrationService {

  private final Connection conn;
  CallableStatement cs;

  public ImmigrationDAO() {
    conn = DBConnectionManager.getInstance().getConnection();
  }

  /*
  출입국 게이트 통과 시 사전신청번호를 통해 자격정보 확인 후
  출입국 테이블 등록 및 외국인일시 외국인 테이블 기록 프로시저 실행
   */
  @Override
  public Boolean Immigration(String applyNo) {
    String sql = "{call ImmigrationProcess(?)}";
    System.out.println("test");
    try {
      //사전 신청번호만 입력
      CallableStatement cs = conn.prepareCall(sql);
      cs.setString(1, applyNo);
      cs.execute();

    } catch (SQLException e) {
      // 신청정보 x, 거절자, 입국일이 다른 승객 대면심사이동
      return Boolean.FALSE;
    }
    return Boolean.TRUE;
  }

  /*
  조건에 따른 입출국인 목록 확인
  1. 전체 = ALL, 내국인 = KOR, 외국인 = FOREIGNER / selectDate의 default값은 sysdate / ALL, IN, OUT 필터
   */
  @Override
  public List<ImmigrationDTO> ImmigrationListSearch(String countryCode, LocalDate date,
      String inOut) {
    //반환 목록 담을 리스트 생성
    List<ImmigrationDTO> result = new ArrayList<>();

    //필터된 조건에 따라 sql문 수정
    StringBuilder sql = new StringBuilder(
        "select * from immigrationhistory where TRUNC(inout_date)=?");

    if ("KOR".equals(countryCode)) {
      sql.append(" AND country_code = 'KOR'");
    } else if ("FOREIGNER".equals(countryCode)) {
      sql.append(" AND country_code != 'KOR'");
    }

    if ("IN".equals(inOut)) {
      sql.append(" AND INOUT = 'IN'");
    } else if ("OUT".equals(inOut)) {
      sql.append(" AND INOUT = 'OUT'");
    }

    System.out.println(date.toString());
    try {
      PreparedStatement ps = conn.prepareStatement(sql.toString());
      ps.setDate(1, Date.valueOf(date));
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        //승객 한명씩 받아와서 리스트에 담기
        ImmigrationDTO dto = ImmigrationDTO.builder()
            .applyNo(rs.getString("APPLY_NO"))
            .passportNo(rs.getString("PASSPORT_NO"))
            .countryCode(rs.getString("COUNTRY_CODE"))
            .inOut(rs.getString("INOUT"))
            .inOutDate(rs.getDate("INOUT_DATE"))
            .visaType(rs.getString("VISA_TYPE"))
            .inOutCountry(rs.getString("INOUT_COUNTRY"))
            .build();
        System.out.println(dto.getApplyNo());
        result.add(dto);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return result;
  }
}