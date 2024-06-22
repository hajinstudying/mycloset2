package com.mycloset.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.mycloset.vo.ProductVO;

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
	
	/* 상품 정보 수정 메소드 */
	
	public int updateProduct(ProductVO productVO) {
		//디버깅 문자열
		System.out.println("ProductDAO의 updateProdct() 실행");
		//반환값 일단 초기화
		int row = 0;
		
		try {
			// 커넥션 풀에서 연결 가져오기
			conn = dataSource.getConnection();
			// 상품정보 수정 쿼리
			String sql = "UPDATE product SET product_name = ?, price = ?, " +
						 " category_id = ? WHERE product_no = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, productVO.getProductName());
			pstmt.setInt(2, productVO.getPrice());
			pstmt.setInt(3, productVO.getCategoryId());
			pstmt.setInt(4, productVO.getProductNo());
			
			row = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("updateBoard() ERR : " + e.getMessage());
			e.printStackTrace();
		} finally {
			closeResource();
		}
		return row;
		
		
	}
}
