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
public class CategoryVO {
	private int catgoryId;		 // 카테고리id (기본키)
	private String categoryName; // 카테고리명
}