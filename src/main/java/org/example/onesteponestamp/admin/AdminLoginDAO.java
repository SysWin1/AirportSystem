package org.example.onesteponestamp.admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.example.onesteponestamp.common.DBConnectionManager;

public class AdminLoginDAO {

  private final Connection conn;

  public AdminLoginDAO() {
    conn = DBConnectionManager.getInstance().getConnection();
  }

  public boolean login(String id, String password) {

    String sql = "SELECT * FROM admin WHERE master_id=?";

    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setString(1, id);

      ResultSet rs = pstmt.executeQuery();
      if (rs.next()) {
        String storedPassword = rs.getString("PASSWORD");
        return verifyPassword(password, storedPassword);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  private boolean verifyPassword(String password, String storedPassword) {
    return password.equals(storedPassword);
  }
}
