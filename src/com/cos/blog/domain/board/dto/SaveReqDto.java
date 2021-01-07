package com.cos.blog.domain.board.dto;

import lombok.Data;

@Data
public class SaveReqDto {
	int userId;
	String title;
	String content;
}
