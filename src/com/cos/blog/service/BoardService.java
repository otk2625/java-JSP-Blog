package com.cos.blog.service;



import java.util.List;

import com.cos.blog.domain.board.Board;
import com.cos.blog.domain.board.BoardDao;
import com.cos.blog.domain.board.dto.DetailRespDto;
import com.cos.blog.domain.board.dto.SaveReqDto;
import com.cos.blog.domain.board.dto.UpdateReqDto;

public class BoardService {
	
	private BoardDao boardDao;

	public BoardService() {
		boardDao = new BoardDao();
	}

	public int 글쓰기(SaveReqDto dto) {
		return boardDao.save(dto);
	}

	public List<Board> 목록보기(int page){
		return boardDao.findAll(page);
	}
	public int 목록개수(){
		return boardDao.countAll();
	}

		//하나의 서비스안에 여러가지 DB관련 로직이 섞임
	public DetailRespDto 글상세보기(int id) {
		
		
		return boardDao.findById(id);
		
	}

	public int 글삭제(int id) {
		
		return boardDao.deleteById(id);
	}

	public int 글수정하기(UpdateReqDto dto) {
		
		return boardDao.update(dto);
	}

	public List<Board> 검색목록보기(int page, String keyword) {
		
		return boardDao.searchFindAll(page, keyword);
	}

	public int 검색목록개수(String keyword) {
		
		return boardDao.searchCountAll(keyword);
	}
}
