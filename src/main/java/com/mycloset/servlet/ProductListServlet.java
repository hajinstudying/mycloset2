package com.mycloset.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mycloset.dao.ProductDAO;
import com.mycloset.util.PageNavigator;
import com.mycloset.vo.ProductVO;

/**
 * 상품 조회 서블릿
 */
@WebServlet("/productList")
public class ProductListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ProductListServlet() {
        super();
    }

	/**
	 * 상품 목록 조회
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			// 키워드 파라미터 추출
	       String keyword = request.getParameter("keyword");
	       
	       // 사용자가 요청한 페이지
	       String pageNum = request.getParameter("pageNum");
	       
	       // 처음 화면이 열릴때는 기본적으로 1페이지가 보이도록 설정
	       if( pageNum == null) {
	    	   pageNum = "1";
	       }
	       
	      ProductVO productVO = new ProductVO();
	      productVO.setPageNum(pageNum);
	       
	       List<ProductVO> productList = null;
	       // 데이터베이스 전담 객체 생성
	       //BoardDAO boardDAO = new BoardDAO();
	       ProductDAO productDAO = ProductDAO.getInstance();
	       
	       // 키워드 유무에 따른 분기
	       if(keyword != null && !keyword.isEmpty()) {
	    	   productList = productDAO.searchProductList(keyword); // 검색기능 메소드 호출
	       }else {
	    	   productList = productDAO.getProductList(productVO); // getBoardList() 호출
	       }
	       
	       // 전체 게시물 수 구하기
	       int totalCount = productDAO.getAllCount();
	       
	       // 이동해갈 페이지에서 사용할 수 있게 request 영역에 저장
	       request.setAttribute("totalCount", totalCount); // 전체 페이지 갯수
	       request.setAttribute("pageNum", pageNum); // 전체 페이지 갯수
	       
	       PageNavigator pageNavigator = new PageNavigator();
	       // jsp화면에 보여질 페이징 문자열 만들기
	       String pageNums = pageNavigator.getPageNavigator(totalCount, productVO.getListcount(), productVO.getPagePerBlock(), Integer.parseInt(pageNum));
	       
	       // 디버깅 문자열
	       System.out.println("pageNums: " + pageNums);
	       
	       // 페이징 문자열을 request 영역에 저장
	       request.setAttribute("page_navigator", pageNums);
	       
	       // request영역에 boardList 저장
	       request.setAttribute("productList", productList); 
	       System.out.println("productList " + productList.size());
	       
	       // 저장한 boardList 출력할 페이지인 boardList.jsp 이동
	       // 여기서 "/" 슬래시는 웹 애플리케이션의 컨텍스트 루트를 말한다.
	       RequestDispatcher rd = request.getRequestDispatcher("/productList.jsp");
	       rd.forward(request, response);

	}
}