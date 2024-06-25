package com.mycloset.servlet;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
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
import com.mycloset.vo.ProductVO;

@WebServlet("/productInsert")
public class ProductInsertServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(ProductInsertServlet.class.getName());

    public ProductInsertServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("/productInsertForm.jsp");
        rd.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        System.out.println("do-post");
        ProductVO productVO = new ProductVO();
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
             
                    if (item.getFieldName().equals("productName")) {
                    	
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
        
            productVO.setProductName(productName);
            productVO.setPrice(price);
            productVO.setCategoryId(categoryId);
            productVO.setFileName(fileName);
            
            ProductDAO productDAO = ProductDAO.getInstance();
            int row = productDAO.insertProduct(productVO);
            

            if (row > 0) {
                String contextPath = request.getContextPath();
                response.sendRedirect(contextPath + "/index.jsp");
            } else {
                request.setAttribute("error", "상품 등록에 실패했습니다.");
                RequestDispatcher rd = request.getRequestDispatcher("/productInsertForm.jsp");
                rd.forward(request, response);
            }
        } catch (Exception e) {
            System.out.println("product 저장중 오류 발생");
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "파일 업로드 중 오류가 발생했습니다.");
        }
    }
}
