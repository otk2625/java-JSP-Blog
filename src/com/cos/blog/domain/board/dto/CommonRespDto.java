package com.cos.blog.domain.board.dto;

import lombok.Data;

@Data
//공통적으로 쓰는 응답용Dto
public  class CommonRespDto<T> {
	private int statusCode; // 1, -1
	private T data; //어떤 객체든지 전달 가능하게 
}
