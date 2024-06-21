package com.mycloset.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mycloset.dao.*;
import com.mycloset.vo.*;

/**
 * 로그인 서블릿
 * - doGet() : 로그인폼 제공
 * - doPost() : 로그인 처리
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LoginServlet() {
        super();
    }

	/**
	 * 로그인 화면 제공
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("loginServlet의 doGet()호출됨");
		RequestDispatcher rd = request.getRequestDispatcher("/loginForm.jsp");
		rd.forward(request, response);
	}

	/**
	 * 로그인폼에서 submit한 경우의 로그인 처리
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 디버깅 문자열
		System.out.println("login 서블릿의 doPost()가 실행되었습니다.");

		// 파라미터 전달받기
		request.setCharacterEncoding("utf-8");
		String memberId = request.getParameter("memberId");
		String pwd = request.getParameter("password");
		
		// DAO 영역의 메소드 호출
		LoginDAO loginDAO = new LoginDAO();
		MemberVO member = loginDAO.login(memberId, pwd);

		if (member != null) {
			// 세션에 사용자 저장
			HttpSession ses = request.getSession();
			ses.setAttribute("member", member);
			// 세션 타임아웃 설정 (30분)
            ses.setMaxInactiveInterval(30 * 60);
            
			// 로그인 성공시 index.jsp로 이동
			String contextPath = request.getContextPath();
			response.sendRedirect(contextPath + "/index.jsp");
		} else {
			// 로그인 실패 시 에러 메세지 세팅
			request.setAttribute("error", "아이디와 비밀번호를 확인하세요.");
			RequestDispatcher rd = request.getRequestDispatcher("/loginForm.jsp");
			rd.forward(request, response);
		}
	}
}
