package com.cos.blog.web;


import java.util.List;

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
import com.cos.blog.domain.board.dto.CommonRespDto;

import com.cos.blog.domain.board.dto.DetailRespDto;
import com.cos.blog.domain.board.dto.SaveReqDto;
import com.cos.blog.domain.board.dto.UpdateReqDto;
import com.cos.blog.domain.reply.Reply;
import com.cos.blog.domain.reply.dto.ReplyDto;
import com.cos.blog.domain.user.User;
import com.cos.blog.service.BoardService;
import com.cos.blog.service.ReplyService;

import com.cos.blog.util.Script;
import com.google.gson.Gson;

//http://localhost:8080/blog/board
@WebServlet("/board")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public BoardController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doProcess(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String cmd = request.getParameter("cmd");
		System.out.println(cmd);
		BoardService boardService = new BoardService();
		ReplyService replyService = new ReplyService();
		// http://localhost:8080/blog/user?cmd=saveForm
		HttpSession session = request.getSession();
		if (cmd.equals("saveForm")) {
			// 그냥하면 안되고 인증이 필요!
			User principal = (User) session.getAttribute("principal");

			if (principal != null) {
				RequestDispatcher dispatcher = request.getRequestDispatcher("board/saveForm.jsp");
				dispatcher.forward(request, response);
			} else {
				RequestDispatcher dispatcher = request.getRequestDispatcher("user/loginForm.jsp");
				dispatcher.forward(request, response);
			}

		} else if (cmd.equals("save")) {
			int userId = Integer.parseInt(request.getParameter("userId"));
			String title = request.getParameter("title");
			String content = request.getParameter("content");

			SaveReqDto dto = new SaveReqDto();
			dto.setUserId(userId);
			dto.setTitle(title);
			dto.setContent(content);
			int result = boardService.글쓰기(dto);
			if (result == 1) { // 정상
				RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
				dispatcher.forward(request, response);
			} else {
				Script.back(response, "글쓰기실패");
			}
		} else if (cmd.equals("list")) {
			int page = Integer.parseInt(request.getParameter("page")); // 최초 : 0, Next : 1, Next: 2
			List<Board> boards = boardService.목록보기(page);
			int 개수 = (int) Math.ceil(boardService.목록개수() / 4);

			System.out.println(boards.size());

			if (page == 개수) {
				request.setAttribute("nextEnd", true);
			}

			if (page == 0) {
				request.setAttribute("preEnd", true);
			}

			double currentpercent = (double) page / (개수) * 100;
			request.setAttribute("currentpercent", currentpercent);
			request.setAttribute("boards", boards);
			// request에 담고
			// RequestDispathcer 만들어서 이동
			RequestDispatcher dispatcher = request.getRequestDispatcher("board/list.jsp");
			dispatcher.forward(request, response);

		} else if (cmd.equals("detail")) {
			int id = Integer.parseInt(request.getParameter("id"));
			
			DetailRespDto dto = boardService.글상세보기(id); // board테이블 + user테이블 = 조인된 데이터가 필요!
			List<ReplyDto> replys = replyService.댓글목록(id);
			
			request.setAttribute("detail", dto);
			request.setAttribute("replys", replys); //댓글 목록 보여주기
			System.out.println("userid있나 : "+replys);
			RequestDispatcher dispatcher = request.getRequestDispatcher("board/detail.jsp");
			dispatcher.forward(request, response);

		} else if (cmd.equals("delete")) {
			// 요청받은 json데이터를 자바 오브젝트로 파싱
//			BufferedReader br = request.getReader(); //body데이터를 읽는다
//			String data = br.readLine();
//			
//			//gson으로 json파싱
//			Gson gson = new Gson();
//			DeleteReqDto dto = gson.fromJson(data, DeleteReqDto.class);

			int id = Integer.parseInt(request.getParameter("id")); // 이렇게 한번에 받아오기 가능

//			String status;

			// DB에서 id값으로 글 삭제
//			int result = boardService.글삭제(dto.getId());
			int result = boardService.글삭제(id);

//			//응답할 json데이터 생성
//			if(result == 1) {
//				status = "ok";
//			}else {
//				status = "fail";
//			}
//			String respData = gson.toJson(status); //json데이터로 변환

			CommonRespDto<String> commonRespDto = new CommonRespDto<>();
			commonRespDto.setStatusCode(result);
			commonRespDto.setData("성공");

			Gson gson = new Gson();
			String respData = gson.toJson(commonRespDto);
			PrintWriter out = response.getWriter();
			out.print(respData); // json이 아닌 String로 보내면 작동 하징 ㅏㄶ음
			out.flush();
		} else if (cmd.equals("updateForm")) {
			int id = Integer.parseInt(request.getParameter("id"));
			DetailRespDto dto = boardService.글상세보기(id);
			request.setAttribute("detail", dto);

			RequestDispatcher dispatcher = request.getRequestDispatcher("board/updateForm.jsp");
			dispatcher.forward(request, response);
		} else if (cmd.equals("update")) {
			int id = Integer.parseInt(request.getParameter("id"));
			String title = request.getParameter("title");
			String content = request.getParameter("content");

			UpdateReqDto dto = new UpdateReqDto();
			dto.setId(id);
			dto.setTitle(title);
			dto.setContent(content);

			int result = boardService.글수정하기(dto);

			if (result == 1) {
				// 고민해보세요. 왜 RequestDispatcher 안썻는지... 한번 써보세요. detail.jsp 호출

				response.sendRedirect("/blog/board?cmd=detail&id=" + id);
			} else {
				Script.back(response, "글 수정에 실패하였습니다.");
			}
		} else if (cmd.equals("search")) {
			int page = Integer.parseInt(request.getParameter("page"));  // 최초 : 0, Next : 1, Next: 2
			String keyword = (String)request.getParameter("keyword");
			System.out.println(keyword);
			List<Board> boards = boardService.검색목록보기(page,keyword);
			int 개수 = (int) Math.ceil(boardService.검색목록개수(keyword)/4);
			
			System.out.println(boards.size());
			
			request.setAttribute("searchOn", true);	
			
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
			
		}

	}

}
