<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>게시판 글 열람</title>
</head>
<body>
<h3>게시판 글 열람</h3>
<table>
	<tr>
		<td colspan="4" align="right"><a href="boardList.bbs">[목록으로]</a></td>
	</tr>
	<tr>
		<td>글 제목</td>
		<td colspan="3"><input type="text" name="subject" maxlength="50" size="50" value="${boardRead.subject }" disabled="disabled"/></td>
		<td>조회수 : ${boardRead.read_cnt }</td>
		<td>답글수 : ${boardRead.child_cnt }</td>
	</tr>
	<tr>
		<td>본문</td>
		<td colspan="3"><textarea name="content" rows="8" cols="45" disabled="disabled">${boardRead.content}</textarea></td>
	</tr>
	<tr>
		<td colspan="4" align="right">
			<a href="boardUpdatePasswordChk.bbs?num=${boardRead.num }">[수정]</a>
			<a href="boardDeletePasswordChk.bbs?num=${boardRead.num }">[삭제]</a>
			<a href="boardReplyForm.bbs?num=${boardRead.num }">[답글]</a>
		</td>
	</tr>
</table>
</body>
</html>