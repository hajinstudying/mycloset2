package com.mycloset.servlet;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mycloset.dao.ProductDAO;
import com.mycloset.vo.MemberVO;

/**
 * 상품 정보를 삭제하는 서블릿
 * - doGet(): 상품 정보 삭제 확인 폼으로 이동
 * - doPost(): 상품 정보 삭제 처리
 */
@WebServlet("/productDelete")
public class ProductDeleteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ProductDeleteServlet() {
        super();
    }

    /**
     * 상품 정보 삭제 확인 폼으로 이동
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 디버깅 문자열
        System.out.println("ProductDeleteServlet의 doGet() 실행");

        // 전달된 상품번호 파라미터 추출
        try {
            int productNo = Integer.parseInt(request.getParameter("productNo"));
            request.setAttribute("productNo", productNo);

            // productDeleteConfirm.jsp로 이동
            RequestDispatcher rd = request.getRequestDispatcher("/productDeleteConfirm.jsp");
            rd.forward(request, response);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid product number");
        }
    }

    /**
     * 상품 삭제 처리
     * - 상품 삭제 확인 폼에서 '삭제'버튼 눌러서 submit 되었을 때 전달되는 요청처리
     * - DB에서 해당 상품정보 삭제
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 파라미터 인코딩
        request.setCharacterEncoding("utf-8");

        // 1. 로그인 여부 먼저 확인, 미로그인시 로그인 폼으로 이동
        String contextPath = request.getContextPath();
        MemberVO memberVO = null;

        HttpSession session = request.getSession(false); // 세션객체 얻기, 없으면 생성안함.
        if (session != null) {
            memberVO = (MemberVO) session.getAttribute("member");
        }
        if (memberVO == null) {
            response.sendRedirect(contextPath + "/login");
            return; // 더이상 다음 코드 실행 안함.
        }
        String memberId = memberVO.getMemberId(); // 세션에서 작성자 추출
        if (!"admin".equals(memberId)) {
            response.sendRedirect(contextPath + "/login");
            return; // 더이상 다음 코드 실행 안함.
        }

        // 디버깅
        System.out.println("삭제할 상품번호: " + request.getParameter("productNo"));

        try {
            // 파라미터 전달받기
            int productNo = Integer.parseInt(request.getParameter("productNo"));

            // DAO 메소드 호출해서 삭제처리
            ProductDAO productDAO = ProductDAO.getInstance(); // 싱글톤 연결 객체 
            int row = productDAO.deleteProduct(productNo);

            // row를 통해서 분기
            if (row > 0) { // 정상 삭제 시 페이지 이동
                System.out.println("상품정보 삭제 성공");
                response.sendRedirect(contextPath + "/productList");
            } else { // 삭제 실패
                request.setAttribute("error", "게시물 삭제에 실패했습니다.");
                RequestDispatcher rd = request.getRequestDispatcher("/productDelete?productNo=" + productNo);
                rd.forward(request, response);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid product number");
            RequestDispatcher rd = request.getRequestDispatcher("/productDelete");
            rd.forward(request, response);
        }
    }
}
