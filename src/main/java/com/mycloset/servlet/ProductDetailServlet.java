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
 * 상품 상세 정보 조회 서블릿
 * - doGet(): productDetail.jsp 로 연결해서 정보 출력
 */
@WebServlet("/productDetail")
public class ProductDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ProductDetailServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int productNo = Integer.parseInt(request.getParameter("productNo"));
		ProductDAO productDAO = ProductDAO.getInstance();
		ProductVO productVO = productDAO.getProduct(productNo);
		
		request.setAttribute("productVO", productVO);
		RequestDispatcher rd = request.getRequestDispatcher("/productDetail.jsp");
		rd.forward(request, response);
		
		
	}


}
