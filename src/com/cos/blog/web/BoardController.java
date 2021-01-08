package com.cos.blog.web;

import java.util.Iterator;
import java.util.List;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cos.blog.domain.board.Board;
import com.cos.blog.domain.board.dto.DetailRespDto;
import com.cos.blog.domain.board.dto.SaveReqDto;
import com.cos.blog.domain.user.User;
import com.cos.blog.service.BoardService;
import com.cos.blog.service.UserService;
import com.cos.blog.util.Script;

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

		}
	}

}
