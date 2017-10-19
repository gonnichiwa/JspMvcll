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
			<a href="boardUpdatePasswordFrm.bbs?num=${boardRead.num }">[수정]</a>
			<a href="boardDeletePasswordFrm.bbs?num=${boardRead.num }">[삭제]</a>
			<a href="boardReplyForm.bbs?num=${boardRead.num }">[답글]</a>
		</td>
	</tr>
</table>
	<form action="boardReplyInsert.bbs" method="post">
			<input type="hidden" name="num" value="${boardRead.num }"/>
			<input type="text" name="rpyAuthor" maxlength="10" size="10"/> <!-- 댓글 이름 -->
			<input type="text" name="rpycontent" size="40"/> <!-- 댓글 내용 -->
			<input type="submit" value="댓글달기"/>
	</form>
<table>
	<c:forEach items="${replyList }" var="rpyDto">
	<tr>
		<td>
			${rpyDto.rpy_author } <!-- 댓글 이름 -->
		</td>
		<td>
			${rpyDto.rpy_content } <!-- 댓글 내용 -->
		</td>
		<td>
			${rpyDto.rpy_date } <!-- 댓글 내용 -->
		</td>
	</tr>
	</c:forEach>
</table>	
</body>
</html>