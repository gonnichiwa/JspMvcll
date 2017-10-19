<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>비밀번호 확인</title>
</head>
<body>
	<h3>게시글 삭제를 위한 비밀번호 확인</h3>
	<form action="boardDeletePasswordChk.bbs" method="post">
		<input type="password" name="password"/>
		<input type="hidden" name="inputNum" value="${inputNum }"/>
		<input type="submit" value="입력"/>
	</form>

</body>
</html>