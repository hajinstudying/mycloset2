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
}
