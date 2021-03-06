package board.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class BoardDAO {
	
	DataSource ds;
	public static final int WRITING_PER_PAGE = 10;
	
	public BoardDAO(){
		try {
			Context initContext = (Context) new InitialContext().lookup("java:comp/env/");
			ds = (DataSource) initContext.lookup("jdbc/oraclexeDataSource");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 게시판 목록 조회 기능
	public ArrayList<BoardDTO> boardList(String curPage){
		
		ArrayList<BoardDTO> list = new ArrayList<BoardDTO>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = ds.getConnection();
			String sql = "select "
					+ "num,name,password,subject,content,write_date,write_time,ref,step,lev,read_cnt,child_cnt"
						+ ", (select count(*) from reply where rpy_parent_num=board.num) as replyCount "
					+ "from board "
					+ "where rownum > ? and rownum < ? order by ref desc, step asc";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, WRITING_PER_PAGE * (Integer.parseInt(curPage)-1));
			pstmt.setInt(2, WRITING_PER_PAGE);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				
				int num = rs.getInt("num");
				String name = rs.getString("name");
				String password = rs.getString("password");
				String subject = rs.getString("subject");
				String content = rs.getString("content");
				String writeDate = rs.getString("write_date");
				String writeTime = rs.getString("write_time");
				int ref = rs.getInt("ref");
				int step = rs.getInt("step");
				int lev = rs.getInt("lev");
				int read_cnt = rs.getInt("read_cnt");
				int child_cnt = rs.getInt("child_cnt");
				int replyCount = rs.getInt("replyCount");
				
//				System.out.println("replyCount in BoardDAO : " + replyCount);
				
				BoardDTO writing = new BoardDTO();
				writing.setNum(num);
				writing.setName(name);
				writing.setPassword(password);
				writing.setSubject(subject);
				writing.setContent(content);
				writing.setWrite_date(writeDate);
				writing.setWrite_time(writeTime);
				writing.setRef(ref);
				writing.setStep(step);
				writing.setLev(lev);
				writing.setRead_cnt(read_cnt);
				writing.setChild_cnt(child_cnt);
				writing.setReplyCount(replyCount);
				
				list.add(writing);
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			try {
				if(rs !=null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return list;
	}
	
	// 페이징 처리
	public int boardPageCnt(){
		
		int pageCnt = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = ds.getConnection();
			String sql = "select count(*) as num from board";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				pageCnt = rs.getInt("num") / WRITING_PER_PAGE + 1;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs !=null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return pageCnt;
	}
	
	// 새 글을 작성하기 위한 글번호 받아오기
	public int getInsertDataNumber() {
		int num = 1;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = ds.getConnection();
			String sql = "select NVL(max(num),0)+1 as num from board";
			
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				num = rs.getInt("num");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs !=null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return num;
	}
	
	
	// 게시판 쓰기 기능 구현
	public void insertData(String subject, String name, String password, String content, int num) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = ds.getConnection();
			
			String sql = "insert into BOARD (num,name,password,subject,content,write_date,write_time,ref,step,lev,read_cnt,child_cnt) "
					+ "values (?,?,?,?,?,to_char(sysdate,'yyyy-mm-dd'),to_char(sysdate,'hh24:mi:ss'),?,0,0,0,0)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, num);
			pstmt.setString(2, name);
			pstmt.setString(3, password);
			pstmt.setString(4, subject);
			pstmt.setString(5, content);
			pstmt.setInt(6, num);
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	// DB에 접근하여 선택한 글번호로 읽기 기능 구현
	public BoardDTO boardRead(String inputNum) {
		// DTO 객체 생성
		BoardDTO writing = new BoardDTO();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = ds.getConnection();
			System.out.println("inputNum : " + inputNum);
			// 글을 읽기때문에 선택한 row의 읽기 횟수를 1회 증가시킨다.
			String sql = "update board set read_cnt = read_cnt + 1 where num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(inputNum));
			pstmt.executeUpdate();
			
			// 읽어온 글을 생성시킨 DTO 인스턴스에 담는다
			String readSql = "select num, name, password, subject, content, write_date, write_time, ref, step, lev, read_cnt, child_cnt from board where num= ? ";
			pstmt = conn.prepareStatement(readSql);
			pstmt.setInt(1, Integer.parseInt(inputNum));
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				int num = rs.getInt("num");
				String name = rs.getString("name");
				String password = rs.getString("password");
				String subject = rs.getString("subject");
				String content = rs.getString("content");
				String writeDate = rs.getString("write_date");
				String writeTime = rs.getString("write_time");
				int ref = rs.getInt("ref");
				int step = rs.getInt("step");
				int lev = rs.getInt("lev");
				int read_cnt = rs.getInt("read_cnt");
				int child_cnt = rs.getInt("child_cnt");
				
				writing.setNum(num);
				writing.setName(name);
				writing.setPassword(password);
				writing.setSubject(subject);
				writing.setContent(content);
				writing.setWrite_date(writeDate);
				writing.setWrite_time(writeTime);
				writing.setRef(ref);
				writing.setStep(step);
				writing.setLev(lev);
				writing.setRead_cnt(read_cnt);
				writing.setChild_cnt(child_cnt);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return writing;
	}

	// (수정/삭제용) 선택 글번호의 사용자 입력 패스워드 비교
	public boolean isPasswordOk(String inputNum, String inputPassword) {
		
		boolean isTrue = false;
		String pwd = "";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = ds.getConnection();
			String sql = "select password from board where num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, inputNum);
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				pwd = rs.getString(1);
			}
			
			// 입력 패스워드와 DB 패스워드 비교
			if(inputPassword.equals(pwd)){
				System.out.println("입력 비번 결과 일치");
				isTrue = true;
			} else {
				System.out.println("입력 비번 결과 xxxxxxxxx");
				isTrue = false;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return isTrue;
	}

	// 해당 글번호의 내용 가져오기
	public BoardDTO getUpdateContent(String inputNum) {
		// 글내용 담을 객체 인스턴스 생성
		BoardDTO writing = new BoardDTO();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = ds.getConnection();
			String sql = "select num,name,password,subject,content,Write_date,Write_time,ref,step,lev,read_cnt,child_cnt from board where num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, inputNum);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				int num = rs.getInt("num");
				String name = rs.getString("name");
				String password = rs.getString("password");
				String subject = rs.getString("subject");
				String content = rs.getString("content");
				String writeDate = rs.getString("write_date");
				String writeTime = rs.getString("write_time");
				int ref = rs.getInt("ref");
				int step = rs.getInt("step");
				int lev = rs.getInt("lev");
				int read_cnt = rs.getInt("read_cnt");
				int child_cnt = rs.getInt("child_cnt");
				
				writing.setNum(num);
				writing.setName(name);
				writing.setPassword(password);
				writing.setSubject(subject);
				writing.setContent(content);
				writing.setWrite_date(writeDate);
				writing.setWrite_time(writeTime);
				writing.setRef(ref);
				writing.setStep(step);
				writing.setLev(lev);
				writing.setRead_cnt(read_cnt);
				writing.setChild_cnt(child_cnt);
			}
			
		} catch (Exception e) {

		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return writing;
	}

	// 수정 글 내용 업데이트하기
	public void updateContent(String inputNum, String subject, String name, String password, String content) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = ds.getConnection();
			String sql = "update board set subject = ?, content = ?, password = ?, name = ? where num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, subject);
			pstmt.setString(2, content);
			pstmt.setString(3, password);
			pstmt.setString(4, name);
			pstmt.setString(5, inputNum);
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {

		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	// 선택한 글번호 기준 행 삭제
	public void deleteContent(String inputNum) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = ds.getConnection();
			String sql = "delete board where num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, inputNum);
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {

		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}		
	}

	// 입력한 reply DB insert
	public void insertReply(String parentNum, String replyAuthor, String replyContent) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = ds.getConnection();
			
			String sql = "INSERT INTO reply (rpy_num,rpy_author,rpy_content,rpy_date,rpy_parent_num)" + 
						" VALUES (seq_rpy_num.nextval,?,?,to_char(sysdate,'YYYY-MM-DD HH24:MI:SS'),?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, replyAuthor);
			pstmt.setString(2, replyContent);
			pstmt.setInt(3, Integer.parseInt(parentNum));
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
	}

	// 해당 글의 모든 댓글을 가져옴
	public ArrayList<ReplyDTO> getReplyList(String inputNum) {
		ArrayList<ReplyDTO> list = new ArrayList<ReplyDTO>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = ds.getConnection();
			String sql = "select * from reply where rpy_parent_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(inputNum));
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				
				ReplyDTO writing = new ReplyDTO();
				
				writing.setRpy_num(rs.getInt(1));
				writing.setRpy_author(rs.getString(2));
				writing.setRpy_content(rs.getString(3));
				writing.setRpy_date(rs.getString(4));
				writing.setRpy_parent_num(rs.getString(5));
				
				list.add(writing);
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			try {
				if(rs !=null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return list;
	}
	
	// 선택 댓글 삭제
	public void deleteReply(String rpy_num) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = ds.getConnection();
			String sql = "delete reply where rpy_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(rpy_num));
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {

		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	
}// class
