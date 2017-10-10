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
			String sql = "select * from "
					+ "(select num,name,password,subject,content,write_date,write_time,ref,step,lev,read_cnt,child_cnt from board order by ref desc, step asc) "
					+ "where rownum > ? and rownum < ?";
			
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
}
