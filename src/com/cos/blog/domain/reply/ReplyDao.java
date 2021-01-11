package com.cos.blog.domain.reply;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.cos.blog.config.DB;
import com.cos.blog.domain.board.Board;
import com.cos.blog.domain.reply.dto.ReplyDto;
import com.cos.blog.domain.reply.dto.SaveReqDto;

public class ReplyDao {

	public int save(SaveReqDto dto) { // 댓글
		String sql = "INSERT INTO reply(userId, boardId, content, createDate) VALUES(?,?,?, now())";
		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, dto.getUserId());
			pstmt.setInt(2, dto.getBoardId());
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
	
	
	//댓글 목록
	public List<ReplyDto> findByBoardId(int boardId) {
		
		// SELECT 해서 Board 객체를 컬렉션에 담아서 리턴
				List<ReplyDto> replyList = new ArrayList<>();
				
				StringBuffer sb = new StringBuffer();
				sb.append("select r.id, r.userid, r.boardid, r.content, r.createdate, u.username ");
				sb.append("from reply r inner join user u ");
				sb.append("on r.userid = u.id ");
				sb.append("where r.boardid=? ");
				sb.append("order by createdate desc; ");
				
				
				String sql = sb.toString();
				Connection conn = DB.getConnection();
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				
				try {
					pstmt = conn.prepareStatement(sql/*, Statement.RETURN_GENERATED_KEYS*/);
					pstmt.setInt(1, boardId);
					rs =  pstmt.executeQuery();
					
					while(rs.next()) {
			          ReplyDto reply = ReplyDto.builder()
			        		  .id(rs.getInt("r.id"))
			        		  .userId(rs.getInt("r.userid"))
			        		  .boardId(rs.getInt("r.boardid"))
			        		  .content(rs.getString("r.content"))
			        		  .createDate(rs.getTimestamp("r.createdate"))
			        		  .username(rs.getString("u.username"))
			        		  .build();
			            
			            replyList.add(reply);
			        }
					
					return replyList;
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				
				return null;
	}


	public int delete(int id) {
		String sql = "delete from reply where id = ? ";
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



}
