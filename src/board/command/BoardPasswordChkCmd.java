package board.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.BoardDAO;

public class BoardPasswordChkCmd implements BoardCmd {

	public boolean passwordCheck;
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		
		String inputNum = request.getParameter("num");
		String inputPassword = request.getParameter("password");
		
		passwordCheck = new BoardDAO().isPasswordOk(inputNum,inputPassword);

	}

}
