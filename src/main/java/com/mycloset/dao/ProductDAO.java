package com.mycloset.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.mycloset.vo.ProductVO;

/*
 * 상품 관련 DAO (Data Access Object) 
 * - 상품 등록
 * - 상품 목록 / 상세조회
 * - 상품 수정 / 삭제
 */

public class ProductDAO {

	private DataSource dataSource;
	private Connection conn = null; 
	private PreparedStatement pstmt = null; 
	private ResultSet rs = null; 
	private static ProductDAO instance;
	
	/* 생성자 */
	private ProductDAO() {
	    try {
	        Context ctx = new InitialContext();
	        dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/oracle");
	        if (dataSource == null) {
	            throw new Exception("DataSource is null");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        System.out.println("ProductDAO: DataSource initialization failed.");
	    }
	}

	/* DB 전담 객체 생성 메소드 */
	public static ProductDAO getInstance() {
		if(instance == null ) {
			instance = new ProductDAO();
		}
		return instance;
	}
		
	/* 커넥션 자원 반납 메소드 */
	public void closeResource() {
		try {
			if (rs != null) rs.close();
			if (pstmt != null) pstmt.close();
			if (conn != null) conn.close();
		} catch (Exception e) {
			System.out.println("closeResource() ERR : " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	/* 상품 정보 수정 메소드 */
	
	public int updateProduct(ProductVO productVO) {
		//디버깅 문자열
		System.out.println("ProductDAO의 updateProdct() 실행");
		//반환값 일단 초기화
		int row = 0;
		
		try {
			// 커넥션 풀에서 연결 가져오기
			conn = dataSource.getConnection();
			// 상품정보 수정 쿼리
			String sql = "UPDATE product SET product_name = ?, price = ?, " +
						 " category_id = ? WHERE product_no = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, productVO.getProductName());
			pstmt.setInt(2, productVO.getPrice());
			pstmt.setInt(3, productVO.getCategoryId());
			pstmt.setInt(4, productVO.getProductNo());
			
			row = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("updateBoard() ERR : " + e.getMessage());
			e.printStackTrace();
		} finally {
			closeResource();
		}
		return row;
	}
	/**
    * 게시물 조회 메소드
    */
	public List<ProductVO> getProductList(ProductVO productVO){
	      List<ProductVO> productList = new ArrayList<>();
	       
	      int start = 0; // 시작 게시물 번호
	      int end = 0; // 끝 게시물 번호
	      /**
	       * [첫 게시물과 끝 게시물 구하는 공식]
	       * 1. row_number() 사용
	       * - 시작 게시물 번호: (사용자가 요청한 페이지 -1) * 한페이지에 보여줄 게시물 수 +1
	       * - 끝 게시물 번호: 시작번호 + 한페이지에 보여줄 게시물 수 -1
	       * 2.Fetch ~ Next 구문 사용
	       *  - 건너 뛸 시작 게시물 번호: (사용자가 요청한 페이지 -1) * 한페이지에 보여줄 게시물 수 
	       *  - 가져 올 끝 게시물 번호: 시작번호 + 한페이지에 보여줄 게시물 수 
	       */
	      
	      // 건너 뛸 게시물 수
	      start = (Integer.parseInt(productVO.getPageNum()) -1) * productVO.getListcount() +1;
	      
	      // 가져올 게시물 수
	      end = start + productVO.getListcount() -1;
	      
	      try {
	    	  
	    	  if (dataSource == null) {
	              throw new SQLException("DataSource is null");
	          }
	    	  
	    	  StringBuffer sql = new StringBuffer(); // String 유사한 문자열 객체
	    	  sql.append("SELECT a.product_No, a.product_name, a.price, a.category_id, c.category_name, a.file_name ");
	    	  sql.append("FROM ( ");
	    	  sql.append("SELECT p.*, ROW_NUMBER() OVER (ORDER BY p.product_No ASC) AS row_num ");
	    	  sql.append("FROM product p ");
	    	  sql.append(") a ");
	    	  sql.append("JOIN category c ON a.category_id = c.category_id ");
	    	  sql.append("WHERE a.row_num BETWEEN ? AND ? "); 
	          
	         conn = dataSource.getConnection(); // 커넥션 얻어오기
	         pstmt = conn.prepareStatement(sql.toString());
	         pstmt.setInt(1, start);
	         pstmt.setInt(2, end);
	         
	         rs = pstmt.executeQuery();
	         
	         while(rs.next()) {
	                ProductVO product = new ProductVO();
	                product.setProductNo(rs.getInt("product_No"));
	                product.setProductName(rs.getString("product_name"));
	        	 	product.setPrice(rs.getInt("price"));
	        	 	product.setCategoryId(rs.getInt("category_id"));
	        	 	product.setFileName(rs.getString("file_name"));
	        	 	product.setCategoryName(rs.getString("category_name"));
	        	 	System.out.println(product);
	                productList.add(product);         
	         }
	      }catch (SQLException e) {
	         System.out.println("getproductList ERR : " + e.getMessage());
	         e.printStackTrace();   // 콘솔에 오류
	      }finally {
	         closeResource();
	      }
	      return productList;
	   }
	/**
    * 검색 기능 메소드
    */
	  public List<ProductVO> searchProductList(String keyword){
      List<ProductVO> productList = new ArrayList<>();
      
      try {
         conn = dataSource.getConnection();
         
         String sql = "SELECT p.product_No, p.product_name, p.price, p.category_id, c.category_name "
         		+ "FROM product p "
         		+ "JOIN category c ON p.category_id = c.category_id "
         		+ "WHERE (p.product_No LIKE ?) OR (p.product_name LIKE ?) ";
         pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, "%" + keyword + "%");
         pstmt.setString(2, "%" + keyword + "%");
         rs = pstmt.executeQuery();
         
         while(rs.next()) {
        	 	ProductVO productVO = new ProductVO();
        	 	productVO.setProductNo(rs.getInt("product_No"));
        	 	productVO.setProductName(rs.getString("product_name"));
        	 	productVO.setPrice(rs.getInt("price"));
        	 	productVO.setCategoryId(rs.getInt("category_id"));
        	 	productVO.setCategoryName(rs.getString("category_name"));
                System.out.println("board " + productVO);
                productList.add(productVO);         
         }
         System.out.println("searchProductList" + productList.size());
      }catch (SQLException e) {
         System.out.println("getproductList ERR : " + e.getMessage());
         e.printStackTrace();   // 콘솔에 오류
      }finally {
         closeResource();
      }
      return productList;
   }
	  /*
	    * 전체 게시물의 객수를 조회하는 메소드
	    */

	    public int getAllCount() {
		int totalcount = 0;
		StringBuffer sql= new StringBuffer();
		sql.append("select count(*) as totalCount ");
		sql.append(" from product ");
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			if(rs.next()) {
				totalcount = rs.getInt("totalcount");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return totalcount;
	}
	    
	    /*
	     * 상품 입력 메서드
	     */
	    public int insertProduct(ProductVO productVO) {
	        int row = 0;
	        try {
	            conn = dataSource.getConnection();
	            conn.setAutoCommit(false); // 트랜잭션 시작(오토 커밋 중지)

	            // 상품 번호 가져오기
	            String getNextVal = "SELECT seq_product_no.NEXTVAL AS nextval FROM dual";
	            pstmt = conn.prepareStatement(getNextVal);
	            rs = pstmt.executeQuery();
	            int nextVal = 0;
	            if (rs.next()) {
	                nextVal = rs.getInt("nextval");
	            }

	            // 상품 삽입 SQL
	            String sql = "INSERT INTO product (product_no, product_name, price, category_id, file_name) VALUES (?, ?, ?, ?, ?)";
	            pstmt = conn.prepareStatement(sql);
	            pstmt.setInt(1, nextVal);
	            pstmt.setString(2, productVO.getProductName());
	            pstmt.setInt(3, productVO.getPrice());
	            pstmt.setInt(4, productVO.getCategoryId());
	            pstmt.setString(5, productVO.getFileName());
	            

	            row = pstmt.executeUpdate();

	            conn.commit(); // 트랜잭션 커밋
	        } catch (SQLException e) {
	            e.printStackTrace();
	            try {
	                conn.rollback(); // 롤백
	            } catch (SQLException e1) {
	                e1.printStackTrace();
	            }
	        } finally {
	            closeResource(); // 리소스 해제
	        }
	        return row;
	    }
	    
	    
	    /* 상품 상세 조회 메소드 */
	    public ProductVO getProduct(int productNo) {
	    	System.out.println("ProductDAO의 getProduct() 실행");
	    	ProductVO productVO = null;
	    	
	    	try {
	    		conn = dataSource.getConnection();
	    		
	    		// 게시물 조회 쿼리
	    		String sql = "SELECT p.product_name, p.price, p.category_id, c.category_name, p.file_name "
	    					+ " FROM product p "
	    					+ " JOIN category c ON p.category_id = c.category_id "
	    					+ " WHERE p.product_no = ?";
	    		pstmt = conn.prepareStatement(sql);
	    		pstmt.setInt(1, productNo);
	    		
	    		rs = pstmt.executeQuery();
	    		if (rs.next()) {
	    			productVO = new ProductVO();
	    			productVO.setProductNo(productNo);
	    			productVO.setProductName(rs.getString("product_name"));
	    			productVO.setPrice(rs.getInt("price"));
	    			productVO.setCategoryId(rs.getInt("category_id"));
	    			productVO.setCategoryName(rs.getString("category_name"));
	    			productVO.setFileName(rs.getString("file_name")); 		// 첨부 파일 이름 세팅
	    		}     
	    	} catch (SQLException e) {
				System.out.println("getProduct() ERR : " + e.getMessage());
				e.printStackTrace();
			} finally {
				closeResource();
			}
			// 게시물 객체 반환
			return productVO;
	    }
	    
}
