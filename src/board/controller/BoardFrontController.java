package board.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.command.BoardCmd;
import board.command.BoardDeleteCmd;
import board.command.BoardDeletePwdFrmCmd;
import board.command.BoardListCmd;
import board.command.BoardPasswordChkCmd;
import board.command.BoardReadCmd;
import board.command.BoardReplyDeleteCmd;
import board.command.BoardUpdateCmd;
import board.command.BoardUpdateFormCmd;
import board.command.BoardUpdateFrmCmd;
import board.command.BoardWriteCmd;
import board.command.ReplyInsertCmd;

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
		if(cmdURI.equals("/boardUpdatePasswordFrm.bbs")){
			cmd = new BoardUpdateFrmCmd();
			cmd.execute(request, response);
			viewPage = "view/boardUpdatePasswordChk.jsp";
		}
		
		// 사용자 입력 비번 비교하여 다음 요청 (boardUpdateForm.jsp OR boardUpdateError.jsp)로 넘기기
		if(cmdURI.equals("/boardUpdatePasswordChk.bbs")){
			cmd = new BoardPasswordChkCmd();
			cmd.execute(request, response);
			BoardPasswordChkCmd checkCmd = (BoardPasswordChkCmd) cmd;
			
			// Cmd 클래스로 값비교 하여 받은 passwordCheck 값에 따라 다음 요청 분기
			if(checkCmd.passwordCheck){
				viewPage = "/boardUpdateForm.bbs";
			} else {
				viewPage = "/boardPwdError.bbs";
			}
		}
		
		// 입력 비번 맞았을 때 : 글 수정 페이지로 요청 및 원래 글 내용 출력 
		if(cmdURI.equals("/boardUpdateForm.bbs")){
			cmd = new BoardUpdateFormCmd();
			cmd.execute(request, response);
			viewPage = "view/boardUpdateForm.jsp";
		}
		
		// 글 수정 처리 후 목록보기 요청
		if(cmdURI.equals("/boardUpdate.bbs")){
			cmd = new BoardUpdateCmd();
			cmd.execute(request, response);
			viewPage = "/boardList.bbs";
		}

		/* 글 삭제 */
		
		// 삭제할 글번호 비번 확인 페이지로 넘기기
		if(cmdURI.equals("/boardDeletePasswordFrm.bbs")){
			cmd = new BoardDeletePwdFrmCmd();
			cmd.execute(request, response);
			viewPage = "view/boardDeletePasswordChk.jsp";
		}
		
		// 삭제 비밀번호 입력 비교 하여 다음 페이지 분기
		if(cmdURI.equals("/boardDeletePasswordChk.bbs")){
			cmd = new BoardPasswordChkCmd();
			cmd.execute(request, response);
			BoardPasswordChkCmd checkCmd = (BoardPasswordChkCmd) cmd;
			
			// Cmd 클래스로 값비교 하여 받은 passwordCheck 값에 따라 다음 요청 분기
			if(checkCmd.passwordCheck){
				viewPage = "/boardDelete.bbs";
			} else {
				viewPage = "/boardPwdError.bbs";
			}
		}
		
		// 삭제 처리 후 목록 보기로 이동
		if(cmdURI.equals("/boardDelete.bbs")){
			cmd = new BoardDeleteCmd();
			cmd.execute(request, response);
			viewPage = "/boardList.bbs";
		}
		
		
		/* 댓글 기능 */
		
		// 댓글 테이블 입력
		if(cmdURI.equals("/boardReplyInsert.bbs")){
			cmd = new ReplyInsertCmd();
			cmd.execute(request, response);
			viewPage = "/boardGetReplys.bbs";
		}
		
		// 해당 글의 모든 댓글을 보여준다.
		if(cmdURI.equals("/boardGetReplys.bbs")){
			cmd = new BoardReadCmd();
			cmd.execute(request, response);
			viewPage = "/boardRead.bbs";
		}
		
		// 댓글 삭제
		if(cmdURI.equals("/boardReplyDelete.bbs")){
			cmd = new BoardReplyDeleteCmd();
			cmd.execute(request, response);
			viewPage = "/boardRead.bbs";
		}
		
		
		
		
		
		RequestDispatcher dis = request.getRequestDispatcher(viewPage);
		dis.forward(request, response);
		
	}
}
