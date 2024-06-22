package com.mycloset.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mycloset.dao.MemberDAO;

/**
 * 회원 탈퇴 처리
 * - doPost : 회원탈퇴 처리
 */
@WebServlet("/memberDelete")
public class MemberDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public MemberDeleteServlet() {
        super();
    }

	/**
	 * 회원탈퇴 처리
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("MemberDeleteServlet의 doPost() 메소드가 실행되었습니다.");
		//파라미터 전달받기
		request.setCharacterEncoding("utf-8");
		String memberId = request.getParameter("memberId");
		
		// MemberDAO 객체 생성해서 deleteMember 메소드 호출
		MemberDAO memberDAO = new MemberDAO();
		int row = memberDAO.deleteMember(memberId);
		
		String contextPath = request.getContextPath();		
		if(row > 0) { // 회원 삭제 성공 후 인덱스 페이지로 이동
			response.sendRedirect(contextPath + "/logout");
			HttpSession ses = request.getSession();
			ses.setAttribute("message", "회원탈퇴 되었습니다.");
		} else { // 회원 삭제 실패
			request.setAttribute("error", "회원 삭제에 실패했습니다.");
			RequestDispatcher rd = request.getRequestDispatcher("/memberConfirm");
			rd.forward(request, response);
		}
	}
}
