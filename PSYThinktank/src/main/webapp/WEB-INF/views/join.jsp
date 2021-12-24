<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 가입</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script type="text/javascript">
	var idChk = false;
	var emailChk = false;
	var checkedId = "";
	var checkedEmail = "";
	
	$(document).ready(function(){
		$("#checkIdBtn").click(function(){
			var memberId = $("#memberId").val();
			if (memberId != "") {
				$.ajax({
					async : true,
					type : "POST",
					data: memberId,
					url : "checkId",
					dataType : "json",
					contentType : "application/json; charset=UTF-8"
				}).done(function(data){
					if (data.isUnique) {
						idChk = true;
						alert("사용할 수 있는 아이디입니다.");
						$("#memberPw").focus();
						checkedId = memberId;
					} else {
						alert("이미 존재하는 아이디입니다.");
						$("#memberId").focus();
					}
				}).fail(function(request, status, error) {
					alert("status: " + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
				});
			} else {
				alert("아이디를 입력해주세요.");
			}
		});
		
		$("#checkEmailBtn").click(function(){
			var memberEmail = $("#memberEmail").val();
			if (memberEmail != "") {
				$.ajax({
					async : true,
					type : "POST",
					data: memberEmail,
					url : "checkEmail",
					dataType : "json",
					contentType : "application/json; charset=UTF-8"
				}).done(function(data){
					if (data.isUnique2) {
						emailChk = true;
						alert("사용할 수 있는 이메일입니다.");
						checkedEmail = memberEmail;
					} else {
						alert("이미 존재하는 이메일입니다.");
					}
				}).fail(function(request, status, error) {
					alert("status: " + request.status + "\n" + "message: " + request.responseText + "\n" + "error: " + error);
				});
			} else {
				alert("이메일을 입력해주세요.");
			}
		});
	});
	
	function confirmPw() {
		var memberPw = $("#memberPw").val();
		var memberPw2 = $("#memberPw2").val();
		return (memberPw == memberPw2) ? true : false;
	}
	
	function validateEmail(str) {
		var regex = /^([A-z0-9!@#$%^&\*+-/=?_`\{\}\|~;. ]+)@([A-z0-9\-]+).([A-z.]+)$/;
		
		return (regex.test(str)) ? true : false;
	}
	
	function submitForm() {
		if (idChk && checkedId == ($("#memberId").val())) {
			if (emailChk && checkedEmail == ($("#memberEmail").val())){
				if ($("#memberPw").val() != "") {
					if (confirmPw()) {
						if (validateEmail($("#memberEmail").val())) {
							$("#form").submit();
						} else {
							alert("이메일 형식을 확인해주세요.");
							}
						} else {
							alert("비밀번호가 일치하지 않습니다.");
						}
					} else {
						alert("비밀번호를 입력해주세요.");
						}				
				} else {
					alert("이메일 중복 확인을 해주세요.");
				}
		} else {
			alert("아이디 중복 확인을 해주세요.");
		}
	}
</script>
</head>
<body>
	<c:import url="header.jsp" />
	<div class="container-fluid" style="height:80vh">
		<div class="text-center">
			<h2 class="m-2">회원 가입</h2>
			<div class="w-75 mx-auto">
				<form id="form" class="text-start" action="member" method="post">
					<div>
						<div>
							<label class="d-inline-block w-25">아이디</label>
							<div class="d-inline">
								<input class="d-inline-block form-control w-50 my-2" type="text" id="memberId" name="memberId">
								<button type="button" id="checkIdBtn" class="btn btn-secondary d-inline">아이디 중복 확인</button>
							</div>
						</div>
						<div>
							<label class="d-inline-block w-25">비밀번호</label>
							<div class="d-inline">
								<input class="d-inline-block form-control w-50 my-2" type="password" id="memberPw" name="memberPw" required>
							</div>
						</div>
						<div>
							<label class="d-inline-block w-25">비밀번호 확인</label> 
							<div class="d-inline">
								<input class="d-inline-block form-control w-50 my-2" type="password" id="memberPw2" required>
							</div>
						</div>
						<div>
							<label class="d-inline-block w-25">이메일</label>
							<div class="d-inline">
								<input class="d-inline-block form-control w-50 my-2" type="email" id="memberEmail" name="memberEmail" required>
								<button type="button" id="checkEmailBtn" class="btn btn-secondary d-inline">이메일 중복 확인</button>
							</div>
						</div>
						<input type="hidden" name="userLevel" value="1">
					</div>						
				</form>
			</div>
			<button type="button" class="btn btn-secondary mx-2" onclick="submitForm()">회원 가입</button>
			<button type="button" class="btn btn-secondary mx-2" onclick="history.back(-1);">돌아가기</button>
		</div>			
	</div>
	<c:import url="footer.jsp"></c:import>
</body>
</html>