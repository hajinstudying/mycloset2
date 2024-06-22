package com.mycloset.vo;

import java.sql.Date;

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
public class ImageVO {
	private int imageId;
	private int productNo;
	private String imagePath;
	private String description;
	private Date regDate;
	
}
