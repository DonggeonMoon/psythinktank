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
	<div class="container-fluid" style="height:80vh">
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
				</td>
			</tr>
		</table>
		<button class="btn btn-dark mx-2" onclick="location.href='stockList'">목록</button>
	</div>
	<c:import url="footer.jsp"></c:import>
</body>
</html>