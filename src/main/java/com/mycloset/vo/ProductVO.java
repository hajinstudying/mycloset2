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
	
	// 페이징 관련 속성(필드, 멤버변수)
	private String pageNum = "1"; // 요청 페이지번호 (기본값을 1)
	private Integer listcount = 10; // 한 페이지에 보여줄 게시물 갯수
	private Integer pagePerBlock = 10; // // 한 화면에 보여질 페이지 번호 갯수(페이지 블럭)

}
