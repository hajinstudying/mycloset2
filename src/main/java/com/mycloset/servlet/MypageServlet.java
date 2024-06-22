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
import com.mycloset.vo.MemberVO;

/**
 * 마이페이지 폼 제공 및 수정 처리
 * - doGet : 마이페이지 jsp 화면 제공
 * - doPost : 회원 정보 수정 처리
 */
@WebServlet("/mypage")
public class MypageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public MypageServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("mypage 서블릿의 doGet()이 실행되었습니다.");
		
		// 마이페이지 폼으로 이동
		RequestDispatcher rd = request.getRequestDispatcher("/myPageForm.jsp");
		rd.forward(request, response);
	}

	/**
	 * 마이페이지에서 수정한 개인정보 업데이트
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("mypage 서블릿의 doPost()가 실행되었습니다.");
		request.setCharacterEncoding("utf-8");
        String contextPath = request.getContextPath();
        
        // 수정된 값 파라미터 전달받아서 MemberVO 객체 생성
        String memberId = request.getParameter("memberId");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        MemberVO memberVO = new MemberVO(memberId, password, name, email);
    
        MemberDAO memberDAO = new MemberDAO();
        int row = memberDAO.updateMember(memberVO);
        
        // 정보 수정 결과 확인
        if (row > 0) { // 정보수정 성공
            System.out.println("정보수정 성공, 로그아웃 페이지로 이동");
            HttpSession ses = request.getSession();
            ses.setAttribute("message", "정보가 수정되었습니다. 다시 로그인해주세요.");
            response.sendRedirect(contextPath + "/logout");
        } else { // 정보수정 중 오류 발생
            System.out.println("정보수정 실패, 본인인증 페이지로 이동");
            RequestDispatcher rd = request.getRequestDispatcher("/memberConfirm");
            rd.forward(request, response);
        }
	}
}
