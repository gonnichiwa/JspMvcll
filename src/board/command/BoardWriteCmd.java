package board.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.BoardDAO;

public class BoardWriteCmd implements BoardCmd {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		// DAO 객채를 생성
		BoardDAO dao = new BoardDAO();
		
		// boardWrite.jsp의 내용들을 받아온다.
		String subject = request.getParameter("subject");
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String content = request.getParameter("content");
		
		// 받은 내용을 DB에 인서트 시킨다!
		dao.insertData(subject,name,password,content);
	}

}
