package board.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.BoardDAO;
import board.model.BoardDTO;

public class BoardUpdateFormCmd implements BoardCmd {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {

		// 요청받은 글번호 파라미터를 이용하여 해당 글 내용을 DB에 접근하여 가져온다
		String inputNum = request.getParameter("num");
		
		BoardDTO writing = new BoardDAO().getUpdateContent(inputNum);
		
		request.setAttribute("boardUpdateForm", writing);
	}

}
