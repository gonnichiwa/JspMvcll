package board.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.BoardDAO;
import board.model.BoardDTO;

public class BoardReadCmd implements BoardCmd {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		
		// 읽어올 글번호를 받아옴
		String inputNum = request.getParameter("num");
		// DAO 객체 인스턴스 생성
		BoardDAO dao = new BoardDAO();
		// 읽어올 글번호를 기준으로 DAO 객체의 특정 글 가져오기 (.jsp 페이지로 데이터를 나타내기 위해 DTO 객체 이용함)
		BoardDTO writing = dao.boardRead(inputNum);
		
		// 데이터를 나타낼 .jsp 파일로 쓸 attribute 지정
		request.setAttribute("boardRead", writing);
		
	}

}
