package com.mycloset.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ProductVO {
	private int productNo;		// 상품번호 (기본키)
	private String productName; // 상품명
	private int price;			// 가격
	private int categoryId;		// 카테고리 id (외래키)
	private String categoryName;
	private String fileName;	// 이미지 필드 추가
	
	// 페이징 관련 속성(필드, 멤버변수)
	private String pageNum = "1"; // 요청 페이지번호 (기본값을 1)
	private Integer listcount = 10; // 한 페이지에 보여줄 게시물 갯수
	private Integer pagePerBlock = 10; // // 한 화면에 보여질 페이지 번호 갯수(페이지 블럭)

	// 상품상세조회 메서드에서 쓸 생성자
	public ProductVO(int productNo, String productName, int price, int categoryId, String categoryName, String fileName) {
		this.productNo = productNo;
		this.productName = productName;
		this.price = price;
		this.categoryId = categoryId;
		this.categoryName = categoryName;
		this.fileName = fileName;
	}
	// 상품수정 메서드에서 쓸 생성자
	public ProductVO(int productNo, String productName, int price, int categoryId, String fileName) {
		this.productNo = productNo;
		this.productName = productName;
		this.price = price;
		this.categoryId = categoryId;
		this.fileName = fileName;
	}
}
