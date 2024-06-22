package com.mycloset.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.mycloset.vo.MemberVO;

/**
 * MemberDAO
 * - 회원가입 관련 업무에서 DB와 관련된 메소드만 모듈화한 클래스
 * - 서블릿에서 DAO로 DB관련 업무를 요청하고 결과를 반환받아서 처리를 진행한다. 
 */

public class MemberDAO {
	private DataSource dataSource;
	private Connection conn = null; 
	private PreparedStatement pstmt = null; 
	private ResultSet rs = null;
	
	/* 생성자 */
	public MemberDAO() {
		try {
			Context ctx = new InitialContext();
			dataSource = (DataSource)ctx.lookup("java:comp/env/jdbc/oracle");
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	/* 회원가입 처리 메소드 */
	public int insertMember(MemberVO memberVO) {
		System.out.println("memberDAO의 insertMember() 실행됨");
		int row = 0;
		
		try {
			conn = dataSource.getConnection();
			
			String sql = "INSERT INTO member(member_no, member_id, password, name, email) " +
						 " VALUES(seq_mno.nextval, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberVO.getMemberId());
			pstmt.setString(2, memberVO.getPassword());
			pstmt.setString(3, memberVO.getName());
			pstmt.setString(4, memberVO.getEmail());

			row = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("회원 등록 중 오류가 발생했습니다.");
		} finally {
			closeResource(); //자원 해제 메소드 호출
		}
		// 메소드 결과 값 반환
		return row;
	}
	
	/* 회원정보 수정 메소드 */
	public int updateMember(MemberVO memberVO) {
		System.out.println("memberDAO의 insertMember() 실행됨");
		int row = 0;
		
		try {
			// db 연결
			conn = dataSource.getConnection();
			// 쿼리 실행
			String sql = "UPDATE member SET password = ?, email = ? " +
						 " WHERE member_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberVO.getPassword());
			pstmt.setString(2, memberVO.getEmail());
			pstmt.setString(3, memberVO.getMemberId());

			row = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("정보 수정 중 오류가 발생했습니다.");
		} finally {
			closeResource();
		}
		return row;
	}
	
	/* 회원정보 삭제 메소드 */
	public int deleteMember(String memberId) {
		System.out.println("MemberDAO의 deleteMember() 실행됨");
		int row = 0;
		
		try {
			// db 연결
			conn = dataSource.getConnection();
			// 쿼리 실행
			String sql = "DELETE member WHERE member_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId);
		    row = pstmt.executeUpdate(); 
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("회원정보 삭제 중 오류가 발생했습니다.");
		} finally {
			closeResource();
		}
		return row;
		
	}
	
	
	/* DB 커넥션 자원 반납 메소드 */
	private void closeResource() {
		try {
			if (rs != null) rs.close();
			if (pstmt != null) pstmt.close();
			if (conn != null) conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
