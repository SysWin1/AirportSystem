package org.example.onesteponestamp.immigration;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import org.example.onesteponestamp.common.DBConnectionManager;

public class ImmigrationDAO implements ImmigrationService {

  private final Connection conn;
  CallableStatement cs;

  public ImmigrationDAO() {
    conn = DBConnectionManager.getInstance().getConnection();
  }

  @Override
  public String Immigration(String applyNo) {
    String sql = "{call ImmigrationProcess(?)}";
    try {
      CallableStatement cs = conn.prepareCall(sql);
      cs.setString(1, applyNo);
      cs.execute();

    } catch (SQLException e) {
      return "대면심사로 이동해 주세요";
    }

    return "게이트를 통과하세요";
  }

  @Override
  public List<ImmigrationDTO> ImmigrationListSearch(String countryCode, String selectDate) {
    return List.of();
  }
}
