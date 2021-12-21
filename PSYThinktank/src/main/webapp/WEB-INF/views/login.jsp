<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>PSYThinktank에 오신 것을 환영합니다.</title>
<link rel="stylesheet" href="style.css">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<link href="https://fonts.googleapis.com/css2?family=Alata&display=swap" rel="stylesheet">
<script src="https://kit.fontawesome.com/51db22a717.js" crossorigin="anonymous"></script>
</head>
<body>
    <div class="page-container">
        <div class="login-form-container shadow">
            <div class="login-form-right-side">
                <div class="top-logo-wrap">
                    
                </div>
                <h1>PSYThinktank</h1>
                <p>주식 투자는 PSYThinktank와 함께</p>
            </div>
            <div class="login-form-left-side">
                <div class="login-top-wrap">
                    <span>계정이 없으신가요?</span>
                    <button class="create-account-btn shadow-light" onclick="location.href='member'">계정 생성</button>
                </div>
                <form action="login" method="post">
		                <div class="login-input-container">
		                	<c:if test="${message != ''}">
							<div class="alert alert-info">
							  <strong>${message}</strong>
							</div>
						</c:if>
	                    <div class="login-input-wrap input-id">
	                        <i class="far fa-id-card"></i>
	                        <input name="memberId" placeholder="ID" type="text">
	                    </div>
	                    <div class="login-input-wrap input-password">
	                        <i class="fas fa-key"></i>
	                        <input name="memberPw" placeholder="Password"  type="password">
	                    </div>
	                </div>
	                <div class="login-btn-wrap">
	                    <button class="login-btn">Login</button>
	                    <a href="#" >비밀번호를 잊어버리셨나요?</a>
	                </div>
                </form>
            </div>
        </div>
    </div>
</body>
</html>