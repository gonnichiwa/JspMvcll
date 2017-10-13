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
import board.command.BoardPasswordChkCmd;
import board.command.BoardReadCmd;
import board.command.BoardUpdateFrmCmd;
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
		
		/* 글 수정 */
		
		// 수정할 글번호 비번확인 페이지로 넘기기
		if(cmdURI.equals("/boardUpdatePasswordChk.bbs")){
			cmd = new BoardUpdateFrmCmd();
			cmd.execute(request, response);
			viewPage = "view/boardUpdatePasswordChk.jsp";
		}
		
		// 사용자 입력 비번 비교하여 다음 요청 (boardUpdateForm.jsp OR boardUpdateError.jsp)로 넘기기
		if(cmdURI.equals("/boardUpdatePasswordChk.bbs")){
			cmd = new BoardPasswordChkCmd();
			cmd.execute(request, response);
			viewPage = "/boardUpdateForm.bbs";
		}
		
		
		// 글 삭제
		
		
		
		RequestDispatcher dis = request.getRequestDispatcher(viewPage);
		dis.forward(request, response);
		
	}
}
