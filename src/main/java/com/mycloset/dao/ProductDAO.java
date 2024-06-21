package com.mycloset.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * 상품 관련 DAO (Data Access Object) 
 * - 상품 등록
 * - 상품 목록 / 상세조회
 * - 상품 수정 / 삭제
 */

public class ProductDAO {

		// jdbc 드라이버 로딩 문자열
		// 맥북 쓰시는 분들은 'orcl' 수정
		private static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
		private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:orcl";
		private static final String DB_USER = "mycloset";
		private static final String DB_PASSWORD = "1234";

		Connection conn = null;
		PreparedStatement pstmt = null; 
		ResultSet rs = null;
		
		/*
		 * 커넥션 객체 얻는 메소드
		 */
		public void connectDB() throws SQLException, ClassNotFoundException {
			try {
				Class.forName(JDBC_DRIVER);
				conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				System.out.println("커넥션 객체를 획득했습니다.");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		/*
		 * 커넥션 자원 반납 메소드
		 */
		public void closeResource() {
			try {
				if (rs != null) rs.close();
				if (pstmt != null) pstmt.close();
				if (conn != null) conn.close();
			} catch (Exception e) {
				System.out.println("closeResource() ERR : " + e.getMessage());
				e.printStackTrace();
			}
		}
}
