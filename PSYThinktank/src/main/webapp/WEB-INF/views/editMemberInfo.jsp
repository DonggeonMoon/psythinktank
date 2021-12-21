<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 정보 변경</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script>
	$(document).ready(function() {
		$("#editBtn").click(function() {
			$("#form").submit();
		});
	});
</script>
</head>
<body>
	<c:import url="header.jsp" />
	<div class="container-fluid">
		<div class="text-center">
			<h2 class="m-2">회원 정보 변경</h2>
			<div class="w-75 mx-auto">
				<form id="form" class="text-start" action="member" method="post">
					<input type="hidden" name="_method" value="put">
					<div>
						<div>
							<label class="d-inline-block w-25">아이디</label>
							<div class="d-inline">
								<input class="d-inline-block form-control w-50 my-2" type="text" id="memberId" name="memberId" value="<c:out value='${memberInfo.memberId }' />" readonly>
							</div>
						</div>
						<div>
							<label class="d-inline-block w-25">새 비밀번호</label>
							<div class="d-inline">
								<input class="d-inline-block form-control w-50 my-2" type="password" id="memberPw" name="memberPw" required>
							</div>
						</div>
						<div>
							<label class="d-inline-block w-25">새 비밀번호 확인</label> 
							<div class="d-inline">
								<input class="d-inline-block form-control w-50 my-2" type="password" id="memberPw2" required>
							</div>
						</div>
						<div>
							<label class="d-inline-block w-25">이메일</label>
							<div class="d-inline">
								<input class="d-inline-block form-control w-50 my-2" type="email" id="memberEmail" name="memberEmail" value="<c:out value='${memberInfo.memberEmail }' />" required>
							</div>
						</div>
						<input type="hidden" name="userLevel" value="1">
					</div>
					<input type="hidden" name="userLevel" value="<c:out value='${memberInfo.userLevel }' />">
				</form>
				<button type="submit" id="editBtn" class="btn btn-secondary mx-2">회원 정보 수정</button>
				<form class="d-inline" action="member" method="post">
					<input type="hidden" name="_method" value="delete">
					<input type="hidden" name="memberId" value="<c:out value='${memberInfo.memberId}' />">
					<input type="submit" class="btn btn-secondary mx-2 d-inline" value="회원 탈퇴">
				</form>
			</div>
		</div>			
	</div>
</body>
</html>