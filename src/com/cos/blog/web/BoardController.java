package com.cos.blog.web;

import java.util.Iterator;
import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cos.blog.domain.board.Board;
import com.cos.blog.domain.board.dto.DeleteReqDto;
import com.cos.blog.domain.board.dto.DetailRespDto;
import com.cos.blog.domain.board.dto.SaveReqDto;
import com.cos.blog.domain.user.User;
import com.cos.blog.service.BoardService;
import com.cos.blog.service.UserService;
import com.cos.blog.util.Script;
import com.google.gson.Gson;

//http://localhost:8080/blog/board
@WebServlet("/board")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	
 public BoardController() {
     super();
 }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cmd = request.getParameter("cmd");
		System.out.println(cmd);
		BoardService boardService = new BoardService();
		// http://localhost:8080/blog/user?cmd=saveForm
		HttpSession session = request.getSession(); 
		if (cmd.equals("saveForm")) {
			// 그냥하면 안되고 인증이 필요!
			User principal = (User) session.getAttribute("principal");
			
			if(principal != null) {
				RequestDispatcher dispatcher = request.getRequestDispatcher("board/saveForm.jsp");
				dispatcher.forward(request, response);
			}else {
				RequestDispatcher dispatcher = request.getRequestDispatcher("user/loginForm.jsp");
				dispatcher.forward(request, response);
			}
			
		}else if(cmd.equals("save")) {
			int userId = Integer.parseInt(request.getParameter("userId"));
			String title = request.getParameter("title");
			String content = request.getParameter("content");

			SaveReqDto dto = new SaveReqDto();
			dto.setUserId(userId);
			dto.setTitle(title);
			dto.setContent(content);
			int result = boardService.글쓰기(dto);
			if(result == 1) { //정상
				RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
				dispatcher.forward(request, response);
			}else {
				Script.back(response, "글쓰기실패");
			}
		} else if(cmd.equals("list")) {
			int page = Integer.parseInt(request.getParameter("page"));  // 최초 : 0, Next : 1, Next: 2
			List<Board> boards = boardService.목록보기(page);
			int 개수 = (int) Math.ceil(boardService.목록개수()/4);
			
			System.out.println(boards.size());
			
			if(page == 개수) {
				request.setAttribute("nextEnd", true);	
			}

			if(page == 0) {
				request.setAttribute("preEnd", true);
			}
			
			double currentpercent = (double)page/(개수)*100;
			request.setAttribute("currentpercent", currentpercent);
			request.setAttribute("boards", boards);
			// request에 담고
			// RequestDispathcer 만들어서 이동
			RequestDispatcher dispatcher = request.getRequestDispatcher("board/list.jsp");
			dispatcher.forward(request, response);
			
	
		}else if(cmd.equals("detail")) {
			int id = Integer.parseInt(request.getParameter("id"));
			DetailRespDto dto = boardService.글상세보기(id); //board테이블 + user테이블 = 조인된 데이터가 필요!
			request.setAttribute("detail", dto);
			RequestDispatcher dispatcher = request.getRequestDispatcher("board/detail.jsp");
			dispatcher.forward(request, response);

			
		}else if(cmd.equals("delete")) {
			//요청받은 json데이터를 자바 오브젝트로 파싱
			BufferedReader br = request.getReader();
			String data = br.readLine();
			
			//gson으로 json파싱
			Gson gson = new Gson();
			DeleteReqDto dto = gson.fromJson(data, DeleteReqDto.class);
			String status;
			System.out.println(dto.getId());
			System.out.println("data : " + data);
			
			//DB에서 id값으로 글 삭제
			int result = boardService.글삭제(dto.getId());
			
			//응답할 json데이터 생성
			if(result == 1) {
				status = "ok";
			}else {
				status = "fail";
			}
			String respData = gson.toJson(status); //json데이터로 변환
			System.out.println(respData);
			PrintWriter out = response.getWriter();
			out.print(respData); //json이 아닌 String로 보내면 작동 하징 ㅏㄶ음
			out.flush();
		}
		
		
	}

}
