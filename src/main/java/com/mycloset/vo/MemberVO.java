package com.mycloset.vo;

import java.io.Serializable;

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
public class MemberVO implements Serializable {

	private static final long serialVersionUID = 1L;
	private int memberNo;		// 회원번호(기본키)
	private String memberId;	// 아이디(유니크)
	private String password;	// 비밀번호
	private String name;		// 이름
	private String email;		// 이메일 (유니크)

	/* 회원가입용 생성자 */
	 public MemberVO(String memberId, String password, String name, String email) {
	        this.memberId = memberId;
	        this.password = password;
	        this.name = name;
	        this.email = email;
	    }
	/* 세션 보관용 생성자 */
    public MemberVO(String memberId, String name, String email) {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
    }
}
