<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>종목 보기</title>
</head>
<body>
	<c:import url="header.jsp" />
	<div class="container-fluid">
		<h2 class="m-2">종목 보기</h2>
		<table class="table">
			<colgroup>
				<col class="text-center" style="width:20%"/>
				<col style="width:80%"/>
			</colgroup>
			<tr>
				<td class="table-light text-center">구분</td>
				<td><c:out value="${stock.corpCls}" /></td>
			</tr>
			<tr>
				<td class="table-light text-center">종목코드</td>
				<td><c:out value="${stock.stockCode}" /></td>
			</tr>
			<tr>
				<td class="table-light text-center">종목명</td>
				<td><c:out value="${stock.stockName}" /></td>
			</tr>
			<tr>
				<td class="table-light text-center">기업 소개</td>
				<td><c:out value="${stock.overview}" /></td>
			</tr>
			<tr>
				<td class="table-light text-center">지분 변동</td>
				<td>
					<table class="table">
						<c:forEach var="i" items="${share}">
							<tr>
								<td class="table-light text-center"><c:if test="${i.date != temp}">${i.date}</c:if></td>
								<td>
									${i.holderName}
								</td>
								<td>${i.share}</td>
							</tr>
							<c:set var="temp" value="${i.date}"></c:set>
						</c:forEach>						
					</table>
				</td>
			</tr>
			<tr>
				<td class="table-light text-center">최근 배당</td>
				<td>
					${dividend.dividend}
				</td>
			</tr>
			<tr>
				<td class="table-light text-center">PSYthinktank 빅데이터 기반 평가</td>
				<td>
					<c:choose>
						<c:when test="${hrr.hrr >= 10}">
							좋음
						</c:when>
						<c:when test="${hrr.hrr >= 0}">
							보통
						</c:when>
						<c:when test="${hrr.hrr < 0}">
							나쁨
						</c:when>
					</c:choose>
					<c:if test="${sessionScope.member.userLevel >= 2}"> ${hrr.hrr}</c:if>
					
					<table class="table">
						<tr>
							<td class="table-light text-center w-25">성장성(Growth Potential)</td>
							<td></td>
						</tr>
						<tr>
							<td class="table-light text-center w-25">지배구조(Governence)</td>
							<td></td>
						</tr>
						<tr>
							<td class="table-light text-center w-25">이사회 독립성(Board Indipendence)</td>
							<td></td>
						</tr>
						<tr>
							<td class="table-light text-center w-25">대외 환경(External Environment)</td>
							<td></td>
						</tr>					
					</table>
				</td>
			</tr>
		</table>
		<button class="btn btn-dark m-2" onclick="location.href='stockList'">목록</button>
	</div>
	<c:import url="footer.jsp" />
</body>
</html>