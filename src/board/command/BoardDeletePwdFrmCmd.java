package board.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BoardDeletePwdFrmCmd implements BoardCmd {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		String inputNum = request.getParameter("num");
		
		System.out.println("num in DeletePwdFrmCmd : " + inputNum);
		
		request.setAttribute("inputNum", inputNum);
	}

}
