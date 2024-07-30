package org.example.onesteponestamp.immigration;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ImmigrationDTO {

  private Integer idx;
  private String applyNo;
  private String passportNo;
  private String countryCode;
  private String inOut;
  private LocalDate inOutDate;
  private String visaType;
  private String inOutCountry;

  public static ImmigrationDTO fromResultSet(Integer idx, ResultSet rs) throws SQLException {
    ImmigrationDTO dto = ImmigrationDTO.builder()
        .idx(idx)
        .applyNo(rs.getString("APPLY_NO"))
        .passportNo(rs.getString("PASSPORT_NO"))
        .countryCode(rs.getString("COUNTRY_CODE"))
        .inOut(rs.getString("INOUT"))
        .inOutDate(rs.getDate("INOUT_DATE").toLocalDate())
        .visaType(rs.getString("VISA_TYPE"))
        .inOutCountry(rs.getString("INOUT_COUNTRY"))
        .build();
    return dto;
  }
}