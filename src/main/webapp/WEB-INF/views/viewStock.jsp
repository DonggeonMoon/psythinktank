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
	<div class="container-fluid py-5" style="min-height:800px;">
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
						<c:forEach var="i" items="${share}" varStatus="svs">
							<tr>
								<td class="table-light text-center"><c:if test="${i.date != temp}">${i.date}<c:set var="currentShare" value="${i.share}"/></c:if></td>
								<td>
									${i.holderName}
								</td>
								<td>${i.share}</td>
							</tr>
							<c:set var="temp" value="${i.date}"/>
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
				<td class="table-light text-center">PSY Thinktank 빅데이터 기반 평가</td>
				<td>
					<c:if test="${sessionScope.member.userLevel >= 2}"> ${hrr.hrr}</c:if>
					
					<table class="table">
						<tr>
							<td class="table-light text-center w-25">성장성(Growth Potential)</td>
							<td>
								
								<c:choose>
									<c:when test="${empty hrr}">데이터 준비 중</c:when>
									<c:when test="${hrr.hrr >= 10}">
										A
									</c:when>
									<c:when test="${hrr.hrr >= 5}">
										B
									</c:when>
									<c:when test="${hrr.hrr >= 0}">
										C
									</c:when>
									<c:otherwise>
										D
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
						<tr>
							<td class="table-light text-center w-25">지배구조(Governance)</td>
							<td>
								<c:choose>
									<c:when test="${empty currentShare}">데이터 준비 중</c:when>
									<c:when test="${currentShare >= 40}">
										A
									</c:when>
									<c:when test="${currentShare >= 30}">
										B
									</c:when>
									<c:when test="${currentShare >= 20}">
										C
									</c:when>
									<c:otherwise>
										D
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
						<tr>
							<td class="table-light text-center w-25">이사회 안정성(Board Stability)</td>
							<td>
								<c:choose>
									<c:when test="${empty corporateBoardStability.boardStability}">데이터 준비 중</c:when>
									<c:when test="${corporateBoardStability.boardStability >= 14}">
										A
									</c:when>
									<c:when test="${corporateBoardStability.boardStability >= 9}">
										B
									</c:when>
									<c:when test="${corporateBoardStability.boardStability >= 4}">
										C
									</c:when>
									<c:otherwise>
										D
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
						<tr>
							<td class="table-light text-center w-25">대외 환경(External Environment)</td>
							<td>준비 중</td>
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