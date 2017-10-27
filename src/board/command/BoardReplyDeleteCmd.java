package board.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.BoardDAO;

public class BoardReplyDeleteCmd implements BoardCmd {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		
		// 요청 파라미터
		String rpy_num = request.getParameter("rpy_num");
		
		System.out.println("선택 댓글 고유번호 : " + rpy_num);
		// DB에서 해당 글 삭제
		new BoardDAO().deleteReply(rpy_num);

	}

}
