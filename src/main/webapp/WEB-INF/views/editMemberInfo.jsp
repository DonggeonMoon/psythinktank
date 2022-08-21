<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>회원 정보 변경</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script>
        let checkedEmail = "";

        $(document).ready(function () {
            $("#editBtn").click(function () {
                let memberPw = $("#memberPw").val();
                let memberPw2 = $("#memberPw2").val();
                let memberEmail = $("#memberEmail").val().trim();

                if (memberPw != "" && memberPw2 != "" && memberEmail != "") {
                    if (memberPw == memberPw2) {
                        if (validateEmail(memberEmail)) {
                            $("#form").submit();
                        } else {
                            alert("이메일 형식을 확인해주세요.");
                        }
                    } else {
                        alert("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
                    }
                } else {
                    alert("비밀번호와 비밀번호 확인, 이메일을 모두 입력하세요.");
                }

            });

            $("#checkEmailBtn").click(function () {
                let memberEmail = $("#memberEmail").val();
                if (memberEmail != "") {
                    $.ajax({
                        async: true,
                        type: "POST",
                        data: memberEmail,
                        url: "checkEmail",
                        dataType: "json",
                        contentType: "application/json; charset=UTF-8"
                    }).done(function (data) {
                        if (data.isUnique2) {
                            emailChk = true;
                            alert("사용할 수 있는 이메일입니다.");
                            checkedEmail = memberEmail;
                        } else {
                            alert("이미 존재하는 이메일입니다.");
                        }
                    }).fail(function (request, status, error) {
                        alert("status: " + request.status + "\n" + "message: " + request.responseText + "\n" + "error: " + error);
                    });
                } else {
                    alert("이메일을 입력해주세요.");
                }
            });

            function validateEmail(str) {
                if (str == "") {
                    alert("이메일을 입력해주세요.");
                    return false;
                }
                let regex = /^([A-z0-9!@#$%\^&\*+-/=?_`\{\}\|~;. ]+)@([A-z0-9\-]+).([A-z.]+)$/;

                return (regex.test(str)) ? true : false;
            }
        });
    </script>
</head>
<body>
<c:import url="header.jsp"/>
<div class="container-fluid" style="height:80vh">
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
                            <input class="d-inline-block form-control w-50 my-2" type="password" id="memberPw" name="memberPw" maxlength="50" required>
                        </div>
                    </div>
                    <div>
                        <label class="d-inline-block w-25">새 비밀번호 확인</label>
                        <div class="d-inline">
                            <input class="d-inline-block form-control w-50 my-2" type="password" id="memberPw2" maxlength="50" required>
                        </div>
                    </div>
                    <div>
                        <label class="d-inline-block w-25">이메일</label>
                        <div class="d-inline">
                            <input class="d-inline-block form-control w-50 my-2" type="email" id="memberEmail" name="memberEmail" value="<c:out value='${memberInfo.memberEmail }' />" maxlength="50" required>
                            <button type="button" id="checkEmailBtn" class="btn btn-secondary d-inline">이메일 중복 확인</button>
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
                <input type="submit" class="btn btn-secondary mx-2 d-inline <c:if test="${sessionScope.member.userLevel >= 3}">disabled</c:if>" value="회원 탈퇴">
            </form>
        </div>
    </div>
</div>
<c:import url="footer.jsp"></c:import>
</body>
</html>