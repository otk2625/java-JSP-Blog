package com.cos.blog.service;

import com.cos.blog.domain.user.User;
import com.cos.blog.domain.user.UserDao;
import com.cos.blog.domain.user.dto.JoinReqDto;
import com.cos.blog.domain.user.dto.LoginReqDto;
import com.cos.blog.domain.user.dto.UpdateReqDto;

public class UserService {
	// 회원가입, 회원수정, 로그인, 아이디중복체크
	private UserDao userDao; 

	public UserService() {
		userDao = new UserDao();
	}
		public int 회원가입(JoinReqDto dto) {
			int result = userDao.save(dto);
			return result;
		}

		public User 로그인(LoginReqDto dto) {

			return null;
		}

		public int 회원수정(UpdateReqDto dto) {

			return -1;
		}

		public int 아이디중복체크(String username) {

			return -1;
		}
}
