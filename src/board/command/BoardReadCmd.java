package board.command;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.BoardDAO;
import board.model.BoardDTO;
import board.model.ReplyDTO;

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
		
		/* 댓글 기능 추가 */
		
		ArrayList<ReplyDTO> rpyList = new ArrayList<ReplyDTO>();
		
		rpyList = dao.getReplyList(inputNum);
		
//		for (int i = 0; i < rpyList.size(); i++) {
//			System.out.println(rpyList.get(i).getRpy_num());
//			System.out.println(rpyList.get(i).getRpy_author());
//			System.out.println(rpyList.get(i).getRpy_content());
//			System.out.println(rpyList.get(i).getRpy_date());
//			System.out.println(rpyList.get(i).getRpy_parent_num());
//		}
		
		// 댓글이 있을 경우
		if(rpyList.size() >= 0){
			request.setAttribute("replyList", rpyList);
		}
	}

}
