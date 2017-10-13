package board.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BoardPasswordChkCmd implements BoardCmd {

	public boolean passwordCheck;
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		
		String inputNum = request.getParameter("num");
		String password = request.getParameter("password");

	}

}
