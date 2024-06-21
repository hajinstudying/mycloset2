package com.mycloset.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.mycloset.vo.*;

/**
 * LoginDAO
 * - 로그인 관련 업무에서 DB와 관련된 메소드만 모듈화한 클래스
 * - 서블릿에서 DAO로 DB관련 업무를 요청하고 결과를 반환받아서 처리를 진행한다. 
 */


public class LoginDAO {
	
	private DataSource dataSource;
	private Connection conn = null; 
	private PreparedStatement pstmt = null; 
	private ResultSet rs = null;
	
	// 커넥션 풀과 연결
	public LoginDAO() {
		try {
			Context ctx = new InitialContext(); 
			dataSource = (DataSource)ctx.lookup("java:comp/env/jdbc/oracle");
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	// 커넥션 객체 반환 메소드
	private void closeResource() {
		try {
			if (rs != null) rs.close();
			if (pstmt != null) pstmt.close();
			if (conn != null) conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 로그인 메소드
	public MemberVO login(String memberId, String password) {
		System.out.println("LoginDAO의 login() 메소드 호출됨");
		MemberVO memberVO = null;
		
		try {
			conn = dataSource.getConnection();
			
			String sql = "SELECT member_id, name, email FROM member WHERE member_id = ? AND password = ?";
			pstmt = conn.prepareStatement(sql); 
			pstmt.setString(1, memberId); 
			pstmt.setString(2, password); 

			rs = pstmt.executeQuery();
			if (rs.next()) { // 로그인 성공 시
				// setter메소드로 객체에 값 설정
				memberVO = new MemberVO(); 
				memberVO.setMemberId(rs.getString("member_id")); 
				memberVO.setName(rs.getString("name"));
				memberVO.setEmail(rs.getString("email"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("로그인 중 오류 발생");
		} finally {
			closeResource();
		}
		// 조회한 객체 반환
		return memberVO; 
	}
}
