<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회보 보기</title>
</head>
<body style="margin:0;padding:0;height:100vh;">
	<c:import url="header.jsp" />
	<iframe src="web/viewer.html?file=회보.pdf" width="100%" height="100%"></iframe>
</body>
</html>