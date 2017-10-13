package board.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BoardUpdateFrmCmd implements BoardCmd {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		
		// boardRead.jsp에서 넘어온 num 파라미터 값을 
		String inputNum = request.getParameter("num");
		// boardUpdatePasswordChk.jsp로 넘기기
		request.setAttribute("num", inputNum);

	}

}
