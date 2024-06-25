package com.mycloset.servlet;

import java.io.File;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.mycloset.dao.ProductDAO;
import com.mycloset.vo.MemberVO;
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
		ProductDAO productDAO = ProductDAO.getInstance();
		ProductVO productVO = productDAO.getProduct(productNo);
		request.setAttribute("productVO", productVO);
		
		// productUpdateForm.jsp로 이동
		RequestDispatcher rd = request.getRequestDispatcher("/productUpdateForm.jsp");
		rd.forward(request, response);
	}

	/**
	 * 상품 수정 처리
	 * - 상품 수정 폼에서 '수정'버튼 눌러서 submit 되었을 때 전달되는 요청처리
	 * - DB에 수정된 상품정보 저장
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
		if(!("admin".equals(memberId))) {
			response.sendRedirect(contextPath + "/login");
			return; // 더이상 다음 코드 실행 안함.
		}
		
		//디버깅
		System.out.println(Integer.parseInt(request.getParameter("productNo")));
			
		// 파라미터 전달받기
		int productNo = Integer.parseInt(request.getParameter("productNo"));
		String productName = request.getParameter("productName");
		int price = Integer.parseInt(request.getParameter("price"));
		int categoryId = Integer.parseInt(request.getParameter("categoryId"));
		Part filePart = request.getPart("fileName"); // 첨부파일 이름 추출
		String fileName = null;

		// 파일 업로드 처리 시작(첨부파일이 있을 경우)
		if (filePart != null && filePart.getSize() > 0) { // 첨부파일이 존재하면
			fileName = filePart.getSubmittedFileName(); // 파일명 추출
			if (fileName != null && !fileName.isEmpty()) {
				/*
				 * getServletContext : GenericServlet 클래스의 메소드로 ServletContext 객체를 반환
				 * ServletContext : - 웹 어플리케이션의 환경 정보를 가지고 있는 객체. - 예를들면 웹 어플리케이션의 루트 경로를 얻어낼 수
				 * 있음. - getRealPath("/") : 웹 어플리케이션의 루트 경로를 갖고옴 - "upload" : 현재 웹 어플리케이션의 루트에서
				 * upload 폴더의 실제 경로를 갖고옴 이클립스에서 미리 upload 폴더를 생성해놓아야함.
				 */
				String uploadPath = getServletContext().getRealPath("/") + "img"; // 파일 저장 경로
				// 위에서 만든 경로로 파일 객체 생성, 없으면 생성, 있으면 생성하지 않음
				// 생성된 파일 경로 객체는 파일을 저장할 경로를 나타냄
				// File 객체는 경로와 파일을 핸들링 할 수 있는 객체
				File uploadDir = new File(uploadPath);
				// upload 폴더가 없으면 생성
				if (!uploadDir.exists())
					uploadDir.mkdir();
				// File.separator : 파일 경로 구분자
				// 파일 저장 경로에 파일명으로 파일 저장, 물리적으로 파일을 저장
				filePart.write(uploadPath + File.separator + fileName);
			}
		} // end 첨부파일이 존재하면
		
		
		// 파라미터 객체에 담기
		ProductVO productVO = new ProductVO(productNo, productName, price, categoryId, fileName);
		
		// DAO 메소드 호출해서 수정처리
		ProductDAO productDAO = ProductDAO.getInstance(); //싱글톤 연결 객체 
		int row = productDAO.updateProduct(productVO);
        
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
