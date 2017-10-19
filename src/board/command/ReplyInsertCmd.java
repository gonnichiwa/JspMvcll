package board.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ReplyInsertCmd implements BoardCmd {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {


		// parameters
		String parentNum = request.getParameter("parentNum");
		String replyAuthor = request.getParameter("rpyAuthor");
		String replyContent = request.getParameter("rpycontent");
		
		// log
		System.out.println("parentNum : " + parentNum);
		System.out.println("replyAuthor : " + replyAuthor);
		System.out.println("replyContent : " + replyContent);

	}

}
