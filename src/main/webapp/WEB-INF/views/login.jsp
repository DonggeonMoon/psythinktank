<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="google-site-verification" content="RIrNGTJOZcoMVdOjVbu1AlGgeDUxXoTC4YQnD2LrjCo"/>

    <title>PSYThinktank에 오신 것을 환영합니다.</title>

    <link rel="stylesheet" href="style.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <link href="https://fonts.googleapis.com/css2?family=Alata&display=swap" rel="stylesheet">

    <script async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js?client=ca-pub-3858475418928277" crossorigin="anonymous"></script>
    <script src="https://kit.fontawesome.com/51db22a717.js" crossorigin="anonymous"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script type="text/javascript">
        function login() {
            let json = {
                memberId: $("#memberId").val().trim(),
                memberPw: $("#memberPw").val()
            };

            $.ajax({
                url: "login",
                type: "post",
                data: JSON.stringify(json),
                dataType: "json",
                contentType: "application/json; charset=utf-8"
            }).done(function (data) {
                if (data.isSucceeded) {
                    location.href = "boardList";
                } else {
                    switch (data.error) {
                        case 1:
                            $("#spinner").css("display", "block")
                            $("#messageArea").empty();
                            $("#messageArea").html("<div class='alert alert-info'><strong>아이디 또는 비밀번호가 입력되지 않았습니다.</strong></div>");
                            setTimeout(function () {
                                $("#spinner").css("display", "none");
                            }, 50);
                            $("#messageArea").fadeOut(10).fadeIn(10);
                            break;
                        case 2:
                            $("#spinner").css("display", "block")
                            $("#messageArea").empty();
                            $("#messageArea").html("<div class='alert alert-info'><strong>존재하지 않는 아이디 입니다</strong></div>");
                            setTimeout(function () {
                                $("#spinner").css("display", "none");
                            }, 50);
                            $("#messageArea").fadeOut(10).fadeIn(10);
                            break;
                        case 3:
                            $("#spinner").css("display", "block")
                            $("#messageArea").empty();
                            $("#messageArea").html("<div class='alert alert-info'><strong>비밀번호가 일치하지 않습니다.<br />(로그인 시도 횟수: " + data.loginTryCount + "/5)</strong></div>");
                            setTimeout(function () {
                                $("#spinner").css("display", "none");
                            }, 50);
                            $("#messageArea").fadeOut(10).fadeIn(10);
                            break;
                        case 4:
                            $("#spinner").css("display", "block")
                            $("#messageArea").empty();
                            $("#messageArea").html("<div class='alert alert-info'><strong>로그인 잠김. 비밀번호 찾기를 통해 임시 비밀번호를 발급받아 로그인해주세요.</strong></div>");
                            setTimeout(function () {
                                $("#spinner").css("display", "none");
                            }, 50);
                            $("#messageArea").fadeOut(10).fadeIn(10);
                            break;
                        default:
                            $("#spinner").css("display", "block")
                            $("#messageArea").empty();
                            $("#messageArea").html("");
                            setTimeout(function () {
                                $("#spinner").css("display", "none");
                            }, 50);
                            $("#messageArea").fadeOut(10).fadeIn(10);
                            break;
                    }
                }
            }).fail(function (error) {
                alert("오류: " + error);
            });
        }

        $(document).ready(function () {
            $("#loginBtn").click(function () {
                login();
            });
            $("#memberId").focus().keypress(function (event) {
                if (event.keyCode == 13) {
                    login();
                }
            });
            $("#memberPw").focus().keypress(function (event) {
                if (event.keyCode == 13) {
                    login();
                }
            });
        });

        function findIdAndPw() {
            let newWindow = window.open("findIdAndPw", "아이디/비밀번호 찾기", "width=800, height=700");
        }
    </script>
</head>
<body>
<div id="spinner" class="position-absolute vw-100 vh-100" style="z-index:100; display:none">
    <div class="position-absolute top-50 start-50 translate-middle">
        <div class="spinner-border"></div>
    </div>
</div>
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
                    <div id="messageArea"></div>
                    <div class="login-input-wrap input-id">
                        <i class="far fa-id-card"></i>
                        <input id="memberId" name="memberId" placeholder="ID" type="text" maxlength="50">
                    </div>
                    <div class="login-input-wrap input-password">
                        <i class="fas fa-key"></i>
                        <input id="memberPw" name="memberPw" placeholder="Password" type="password" maxlength="50">
                    </div>
                </div>
                <div class="login-btn-wrap">
                    <button type="button" id="loginBtn" class="login-btn">Login</button>
                    <a href="#" onclick="findIdAndPw()">아이디 혹은 비밀번호를 잊어버리셨나요?</a>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>