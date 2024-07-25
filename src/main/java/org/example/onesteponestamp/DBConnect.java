package org.example.onesteponestamp;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {

  public static void main(String[] args) {
    Connection conn = null;

    try {
      //1. 오라클 JDBC DRIVER를 메모리로 로딩
      Class.forName("oracle.jdbc.OracleDriver");

      //2.DB 연결
      conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/xe", "java", "oracle");
      System.out.println("연결 성공");

      //3. DB 작업




    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      if(conn != null) {
        try {
          conn.close();//열었던 자원 해제
        } catch(SQLException e) {

        }
        //4. DB 연결 해제
        System.out.println("연결 끊기");
      }

    }
  }
}

