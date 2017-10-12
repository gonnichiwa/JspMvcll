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
		
		/*************************/
		/*************************/
		/* 게시글 수정/삭제를 위한 GuideLine */
		/*************************/
		/*************************/

		// 게시글의 수정 (boardRead.jsp 참조하여 요청 URL을 파악한다.)
		// 1. 사용자가 게시글을 수정버튼을 눌렀을 때 게시글 수정을 위한 비번 확인 페이지 (boardUpdatePasswordChk.jsp)를 띄운다. (선택한 글의 비번 정보가 필요함.)
		// 사용자가 입력한 비번이 일치할 시 -- 1. 게시글 수정 페이지 (BoardUpdateForm.jsp)에 사용자가 선택한 글의 content가 다 나온다.
		// 							2. 게시글 수정 버튼을 눌렀을 때 (boardUpdate.bbs)를 요청하여 DB 업데이트 시키고 boardList.bbs 요청처리
		// 사용자가 입력한 비번이 불일치할 시 비번이 틀렸다는 페이지(boardUpdateError.jsp) 띄우고 요청 끝냄
		
		
		// 게시글의 삭제 (boardRead.jsp 참조하여 요청 URL을 파악한다.)
		// 1. 게시글 삭제를 위한 비번 확인 페이지 (boardDeletePasswordChk.jsp)를 띄운다. (선택한 글의 비번 정보가 필요함.)
		// 2. DB에서 해당 게시글 번호의 비밀번호를 가져와 사용자가 입력한 비번과 비교한다.
		// 사용자가 입력한 비번이 일치할 시 -- 3. boardList.bbs 바로 호출
		// 사용자가 입력한 비번이 불일치할 시 비번이 틀렸다는 페이지(boardUpdateError.jsp) 띄우고 요청 끝냄
		
		
		RequestDispatcher dis = request.getRequestDispatcher(viewPage);
		dis.forward(request, response);
		
	}
}
