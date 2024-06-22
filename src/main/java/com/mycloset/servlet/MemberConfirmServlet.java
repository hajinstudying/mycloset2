package com.mycloset.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mycloset.dao.LoginDAO;
import com.mycloset.vo.MemberVO;

/**
 * 본인인증 서블릿
 * - doGet(): 본인인증 폼 제공
 * - doPost() : 비밀번호 인증 처리
 */
@WebServlet("/memberConfirm")
public class MemberConfirmServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public MemberConfirmServlet() {
        super();
    }

	/**
	 * 본인인증 폼 화면으로 이동
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("memberConfirm 서블릿의 doGet()이 실행되었습니다.");
		RequestDispatcher rd = request.getRequestDispatcher("/memberConfirm.jsp");
		rd.forward(request, response);
	}

	/**
	 * 본인인증 확인 처리
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("memberConfirm 서블릿의 doPost()가 실행되었습니다.");
		
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
            
			// 인증 성공시 index.jsp로 이동
			String contextPath = request.getContextPath();
			response.sendRedirect(contextPath + "/myPageForm.jsp");
		} else {
			// 인증 실패 시 에러 메세지 세팅
			request.setAttribute("error", "비밀번호를 정확히 입력해주세요.");
			RequestDispatcher rd = request.getRequestDispatcher("/memberConfirm.jsp");
			rd.forward(request, response);
		}
	}
}
