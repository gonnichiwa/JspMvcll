package board.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.BoardDAO;

public class BoardDeleteCmd implements BoardCmd {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		
		String inputNum = request.getParameter("num");
		
		System.out.println("inputNum for Delete in BoardDeleteCmd : " + inputNum);

		new BoardDAO().deleteContent(inputNum);
	}

}
