package board.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.BoardDAO;

public class ReplyInsertCmd implements BoardCmd {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {


		// parameters
		String parentNum = request.getParameter("num");
		String replyAuthor = request.getParameter("rpyAuthor");
		String replyContent = request.getParameter("rpycontent");
		
		
		new BoardDAO().insertReply(parentNum,replyAuthor,replyContent);
		
//		System.out.println("DB INSERT 완료");
	}

}
