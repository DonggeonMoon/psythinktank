<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="keywords" content="PSYThinktank, 주식, 채권, 부동산, 파생상품, 투자, 금융, 증권">
<meta name="description" content="올바른 주식 투자, PSYThinktank가 만들어 갑니다.">
<meta name="author" content="PSYThinktank">
<meta name="google-site-verification" content="RIrNGTJOZcoMVdOjVbu1AlGgeDUxXoTC4YQnD2LrjCo"/>

<meta property="og:title" content="PSYThinktank"/>
<meta property="og:type" content="website"/>
<meta property="og:url" content="www.psythinktank.com"/>
<meta property="og:image" content="http://www.psythinktank.com/assets/img/logo2.png"/>
<meta property="site_name" content="PSYThinktank"/>
<meta property="description" content="빅데이터 기반 금융투자"/>
<meta property="locale" content="ko_KR"/>

<link rel="shortcut icon" href="favicon.ico" type="image/x-icon">
<link rel="icon" href="favicon.ico" type="image/x-icon">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
<script async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js?client=ca-pub-3858475418928277" crossorigin="anonymous"></script>
<nav class="navbar navbar-expand-sm bg-light navbar-light">
    <div class="container-fluid mr-5">
        <ul class="navbar-nav">
            <li class="nav-item">
                <a class="navbar-brand" href="index.html">PSYThinktank</a>
            </li>
        </ul>
        <ul class="navbar-nav me-5">
            <li class="nav-item dropdown">
                <c:choose>
                <c:when test="${sessionScope.member != null}">
                <a class="nav-link active dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    <c:out value="${sessionScope.member.memberId} 님"/>
                </a>
                <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                    <c:if test="${sessionScope.member.userLevel == 3 }">
                        <a class="dropdown-item" href="managerPage">관리자 페이지</a>
                    </c:if>
                    <a class="dropdown-item" href="editMemberInfo">회원 정보 수정</a>
                    <a class="dropdown-item" href="logout">로그아웃</a>
                </div>
                </c:when>
                <c:otherwise>
            <li class="nav-item">
                <a class="nav-link" href="login">로그인</a>
            </li>
            </c:otherwise>
            </c:choose>
            </li>
        </ul>
    </div>
</nav>
<nav class="navbar navbar-expand-sm bg-dark navbar-dark justify-content-center">
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse mx-2" id="navbarSupportedContent">
        <ul class="navbar-nav text-center">

            <li class="nav-item">
                <a class="nav-link" href="stockList">종목 검색</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="boardList">회원 게시판</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="circularList">회보</a>
            </li>
        </ul>
    </div>
</nav>