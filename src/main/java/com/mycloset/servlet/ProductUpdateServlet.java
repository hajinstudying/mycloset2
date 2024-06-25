package com.mycloset.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mycloset.dao.ProductDAO;
import com.mycloset.vo.ProductVO;

/**
 * 상품 정보를 수정하는 서블릿
 * - doGet(): 상품 정보 수정폼으로 이동
 * - doPost(): 상품정보 수정 처리
 */
@WebServlet("/productUpdate")
public class ProductUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ProductUpdateServlet() {
        super();
    }

    /**
     * 상품 정보 수정폼으로 이동시킴
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 디버깅 문자열
		System.out.println("productUpdate 서블릿의 doGet()이 실행되었습니다.");
		
		// 전달된 상품번호 파라미터 추출
		int productNo = Integer.parseInt(request.getParameter("productNo"));
		
		// 싱글톤 DB 연결 객체 가져옴
		ProductDAO prouctDAO = ProductDAO.getInstance();

		// productUpdateForm.jsp로 이동
		RequestDispatcher rd = request.getRequestDispatcher("/productUpdateForm.jsp");
	}

	/**
	 * 상품 수정 처리
	 * - 상품 수정 폼에서 '수정'버튼 눌러서 submit 되었을 때 전달되는 요청처리
	 * - DB에 수정된 상품정보 저장
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 디버깅 문자열
		System.out.println("productUpdate 서블릿의 doPost()가 실행되었습니다.");
		
		// 파라미터 전달받아서 바로 객체에 저장
		request.setCharacterEncoding("utf-8");
		int productNo = Integer.parseInt(request.getParameter("productNo"));
		ProductVO productVO = new ProductVO();
		productVO.setProductNo(productNo);
		productVO.setProductName(request.getParameter("productName"));
		productVO.setPrice(Integer.parseInt(request.getParameter("price")));
		productVO.setCategoryId(Integer.parseInt(request.getParameter("categoryId")));
		
		// DAO 메소드 호출해서 수정처리
		ProductDAO productDAO = ProductDAO.getInstance(); //싱글톤 연결 객체 
		int row = productDAO.updateProduct(productVO);
		
		// 컨텍스트패스 경로
		String contextPath = request.getContextPath();
        
        // row를 통해서 분기
        if (row > 0) { // 정상 수정시 페이지이동
			System.out.println("상품정보 수정 성공");
			response.sendRedirect(contextPath + "/productDetail?productNo=" + productNo); 
		} else { // 수정 실패
			request.setAttribute("error", "게시물 수정에 실패했습니다.");
			RequestDispatcher rd = request.getRequestDispatcher("/productUpdate?productNo=" + productNo);
			rd.forward(request, response);
		}		
	}
}
