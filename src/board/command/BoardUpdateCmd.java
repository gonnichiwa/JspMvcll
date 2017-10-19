package board.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.BoardDAO;

public class BoardUpdateCmd implements BoardCmd {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		
		/* 입력폼으로부터 내용을 받아 DB에 업데이트 */
		String inputNum = request.getParameter("num");
		String subject = request.getParameter("subject");
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String content = request.getParameter("content");

		// DB에 업데이트
		new BoardDAO().updateContent(inputNum,subject,name,password,content);
		
		

	}

}
