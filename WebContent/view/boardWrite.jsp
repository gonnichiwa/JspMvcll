<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>게시판 새글 쓰기</title>
</head>
<body>
	<h3>게시판 새글 쓰기</h3>
	<form action="boardWrite.bbs" method="post">
		<table>
			<tr>
				<td colspan="4" align="right"><a href="boardList.bbs">[목록으로]</a></td>
			</tr>
			<tr>
				<td>글 제목</td>
				<td colspan="3"><input type="text" name="subject" maxlength="50" size="50"></td>
			</tr>
			<tr>
				<td>작성자</td>
				<td><input type="text" name="name" maxlength="20" size="20"></td>
				<td>비밀번호</td>
				<td><input type="password" name="password" maxlength="20" size="20"></td>
			</tr>
			<tr>
				<td>본문</td>
				<td colspan="3"><textarea name="content" rows="8" cols="45"></textarea></td>
			</tr>
			<tr>
				<td colspan="4" align="right"><input type="submit" value="글올리기"></td>
			</tr>
			
		</table>
	</form>
</body>
</html>