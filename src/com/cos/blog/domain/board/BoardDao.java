package com.cos.blog.domain.board;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.cos.blog.config.DB;
import com.cos.blog.domain.board.dto.DetailRespDto;
import com.cos.blog.domain.board.dto.SaveReqDto;
import com.cos.blog.domain.board.dto.UpdateReqDto;

public class BoardDao {
	
	public int save(SaveReqDto dto) { // 회원가입
		String sql = "INSERT INTO board(userId, title, content, createDate) VALUES(?,?,?, now())";
		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, dto.getUserId());
			pstmt.setString(2, dto.getTitle());
			pstmt.setString(3, dto.getContent());
			int result = pstmt.executeUpdate();
			
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally { // 무조건 실행
			DB.close(conn, pstmt);
		}
		return -1;
	}

	public List<Board> findAll(int page){
		// SELECT 해서 Board 객체를 컬렉션에 담아서 리턴
		List<Board> boardList = new ArrayList<>();
		
		String sql = "SELECT id, userId, title, content, readCount, createDate FROM board order by id desc limit ?,4";
		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, page*4);
			rs =  pstmt.executeQuery();
			
			while(rs.next()) {
	            Board board = new Board().builder()
	            		.id(rs.getInt("id"))
	            		.userId(rs.getInt("userid"))
	            		.title(rs.getString("title"))
	            		.content(rs.getString("content"))
	            		.readCount(rs.getInt("readCount"))
	            		.createDate(rs.getTimestamp("createDate"))
	            		.build();
	            
	            boardList.add(board);
	        }
			
			return boardList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return null;
	}

	public int countAll() {
		String sql = "SELECT * from board ";
		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs =  pstmt.executeQuery();

			rs.last();
			int a = rs.getRow();
			
			//if(rs.next){return rs.getInt(1); => 개수 뽑아내기}
		
		
			return a;
		} catch (Exception e) {
			e.printStackTrace();
		} finally { // 무조건 실행
			DB.close(conn, pstmt);
		}
		return -1;
	}

	public DetailRespDto findById(int id) {
		// SELECT 해서 Board 객체를 컬렉션에 담아서 리턴
				StringBuffer sb = new StringBuffer();
				
				sb.append("select b.id, b.title, b.content, b.readCount, u.username, u.id ");
				sb.append("from board b inner join user u ");
				sb.append("on b.userid = u.id ");
				sb.append("where b.id = ? ");
				
				String sql = sb.toString();
				String readcountSql = "UPDATE board SET readCount=readCount+1  where id = ? ";

				Connection conn = DB.getConnection();
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				DetailRespDto dto;
				try {
					pstmt = conn.prepareStatement(readcountSql);
					pstmt.setInt(1, id);
					pstmt.executeUpdate();
					
					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, id);
					
					rs =  pstmt.executeQuery();
					dto = new DetailRespDto();
					if(rs.next()) {
						
						
					dto.setId(rs.getInt("b.id"));
					dto.setTitle(rs.getString("b.title"));
					dto.setContent(rs.getString("b.content"));
					dto.setReadCount(rs.getInt("b.readcount"));
					dto.setUsername(rs.getString("u.username"));
					dto.setUserid(rs.getInt("u.id"));
					}
					
					return dto;
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				
				return null;
		
	}

	public int deleteById(int id) {
		String sql = "delete from board where id = ? ";
		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
		
			int result = pstmt.executeUpdate();
			
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally { // 무조건 실행
			DB.close(conn, pstmt);
		}
		return -1;
	}

	public int update(UpdateReqDto dto) {
		String sql = "UPDATE board SET title = ?, content = ? WHERE id = ?";
		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getTitle());
			pstmt.setString(2, dto.getContent());
			pstmt.setInt(3, dto.getId());
			int result = pstmt.executeUpdate();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally { // 무조건 실행
			DB.close(conn, pstmt);
		}
		return -1;
	}

	
}
