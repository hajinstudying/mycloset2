package com.mycloset.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/*
 * 상품 관련 DAO (Data Access Object) 
 * - 상품 등록
 * - 상품 목록 / 상세조회
 * - 상품 수정 / 삭제
 */

public class ProductDAO {

	private DataSource dataSource;
	private Connection conn = null; 
	private PreparedStatement pstmt = null; 
	private ResultSet rs = null; 
	private static ProductDAO instance;
	
	/* 생성자 */
	private ProductDAO() {
		try {
			Context ctx = new InitialContext(); 
			dataSource = (DataSource)ctx.lookup("java:comp/env/jdbc/oracle");
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	/* DB 전담 객체 생성 메소드 */
	public static ProductDAO getInstance() {
		if(instance == null ) {
			instance = new ProductDAO();
		}
		return instance;
	}
		
	/* 커넥션 자원 반납 메소드 */
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
