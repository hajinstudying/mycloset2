package com.mycloset.servlet;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

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
	private static final Logger logger = Logger.getLogger(ProductInsertServlet.class.getName());
       
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
		//디버깅
		System.out.println("productUpdate 서블릿의 doPost() 실행");
		
		// 파라미터 인코딩
		request.setCharacterEncoding("utf-8");
		String contextPath = request.getContextPath();
		
		// 1. 로그인 여부 확인
		MemberVO memberVO = null;
		HttpSession session = request.getSession(false);
		if (session != null) {
			memberVO = (MemberVO) session.getAttribute("member");
		}
		if (memberVO == null) {
			response.sendRedirect(contextPath + "/login");
			return;
		}
		
		//2. admin 여부 확인
		String memberId = memberVO.getMemberId();
		if (!"admin".equals(memberId)) {
			response.sendRedirect(contextPath + "/login" +
		"&message=" + java.net.URLEncoder.encode("관리자만 상품을 수정할 수 있습니다", "UTF-8"));
			return;
		}
		
		//3. 
		ProductVO productVO = new ProductVO();
		int productNo = 0;
		String productName = null;
		int price = 0;
		int categoryId = 0;
		String fileName = null;
		
		// 업로드 폴더 경로 설정 및 생성
        String uploadPath = getServletContext().getRealPath("/") + "img";
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        
        // 파일 업로드 설정
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setRepository(uploadDir);
        factory.setSizeThreshold(1024 * 1024);
        
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setFileSizeMax(1024 * 1024 * 10); // 10 MB
        upload.setSizeMax(1024 * 1024 * 15); // 15 MB

        try {
            List<FileItem> items = upload.parseRequest(request);
            System.out.println("items" + items.size());
            for (FileItem item : items) {
                if (item.isFormField()) {
             
                	if (item.getFieldName().equals("productNo")) {
                		String productNoStr = item.getString("utf-8");
                        if (productNoStr != null && !productNoStr.isEmpty()) {
                            productNo = Integer.parseInt(productNoStr);
                        }
                         
                	} else if (item.getFieldName().equals("productName")) {
                    	
                        productName = item.getString("utf-8");
                        System.out.println("productName :" + productName);
                    } else if (item.getFieldName().equals("price")) {
                        String priceStr = item.getString("utf-8");
                        if (priceStr != null && !priceStr.isEmpty()) {
                            price = Integer.parseInt(priceStr);
                        }
                    } else if (item.getFieldName().equals("categoryId")) {
                        String categoryIdStr = item.getString("utf-8");
                        if (categoryIdStr != null && !categoryIdStr.isEmpty()) {
                            categoryId = Integer.parseInt(categoryIdStr);
                        }
                    }
                } else {
                    if (item.getFieldName().equals("fileName") && item.getSize() > 0) {
                        fileName = new File(item.getName()).getName();
                        File uploadFile = new File(uploadPath + File.separator + fileName);
                        item.write(uploadFile);
                        logger.info("Uploaded file: " + uploadFile.getAbsolutePath() + " (" + item.getSize() + " bytes)");
                    }
                }
            }
            
            productVO.setProductNo(productNo);
            productVO.setProductName(productName);
            productVO.setPrice(price);
            productVO.setCategoryId(categoryId);
            productVO.setFileName(fileName);
            

            ProductDAO productDAO = ProductDAO.getInstance();
            int row = productDAO.updateProduct(productVO);
            
            if (row > 0) {
                response.sendRedirect(contextPath + "/productList");
            } else {
                request.setAttribute("error", "상품 수정에 실패했습니다.");
                RequestDispatcher rd = request.getRequestDispatcher("/productDetail?productNo=" + productNo);
                rd.forward(request, response);
            }
        } catch (Exception e) {
            System.out.println("product 저장중 오류 발생");
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "파일 업로드 중 오류가 발생했습니다.");
        }
    }
}
