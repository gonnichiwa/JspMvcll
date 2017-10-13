package board.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.command.BoardCmd;
import board.command.BoardListCmd;
import board.command.BoardReadCmd;
import board.command.BoardWriteCmd;

@WebServlet("*.bbs")
public class BoardFrontController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	/**
	 *@see HttpServlet#HttpServlet()
	 */
	public BoardFrontController(){
		super();
	}
	
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String requestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String cmdURI = requestURI.substring(contextPath.length());
		
		BoardCmd cmd = null;
		String viewPage = null;
		
		// 글 목록 조회 처리
		if(cmdURI.equals("/boardList.bbs")){
			cmd = new BoardListCmd();
			cmd.execute(request, response);
			viewPage = "view/boardList.jsp";
		}
		
		// 새글 쓰기를 눌렀을 때 글 쓰기 페이지 출력
		if(cmdURI.equals("/boardWriteForm.bbs")){
			viewPage = "view/boardWrite.jsp";
		}
		
		// 새글 등록
		if(cmdURI.equals("/boardWrite.bbs")){
			cmd = new BoardWriteCmd();
			cmd.execute(request, response);
			viewPage = "/boardList.bbs";
		}
		
		// 사용자가 선택한 글 열람
		if(cmdURI.equals("/boardRead.bbs")){
			cmd = new BoardReadCmd();
			cmd.execute(request, response);
			viewPage = "view/boardRead.jsp";
		}
		
		
		
		RequestDispatcher dis = request.getRequestDispatcher(viewPage);
		dis.forward(request, response);
		
	}
}
