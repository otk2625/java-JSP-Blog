package com.cos.blog.domain.reply.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class ReplyDto {
	private int id;
	private int userId;
	private int boardId;
	private String username;
	private String content;
	private Timestamp createDate;
}
