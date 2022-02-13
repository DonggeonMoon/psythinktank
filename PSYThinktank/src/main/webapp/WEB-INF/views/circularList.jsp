<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회보</title>
</head>
<body>
	<c:import url="header.jsp" />
	<div class="container-fluid">
		<h2 class="m-2">회보</h2>
		<table class="table text-center">
			<thead class="table-dark">
				<tr>
					<td>번호</td>
					<td class="w-50">제목</td>
					<td>업로드한 날짜</td>
					<td>기능</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="i" items="${circularList.getContent()}">
					<tr>
						<c:url var="link" value="/circular">
							<c:param name="circularId" value="${i.circularId}" />
						</c:url>
						<td><c:out value="${i.circularId}" /></td>
						<td><a href="${link}"><c:out value="${i.circularTitle}" /></a></td>
						<td><fmt:formatDate value="${i.uploadDate}" pattern="yyyy-MM-dd hh:mm:ss" /></td>
						<td><c:if test="${sessionScope.member.userLevel >= 2}">
								<form action="circular" method="post">
									<input type="hidden" name="_method" value="delete"> <input type="hidden" name="circularId" value="${i.circularId }">
									<button class="btn btn-primary p-1">삭제</button>
								</form>
							</c:if></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<c:if test="${sessionScope.member.userLevel >= 2}">
			<button class="btn btn-dark m-2" onclick="location.href='insertCircular'">파일 올리기</button>
		</c:if>
		<div class="d-flex justify-content-center">
			<ul class="pagination">
				<c:if test="${currentBlock > 0}">
					<li class="page-item"><a class="page-link" href="circularList?page=<c:out value="${(currentBlock - 1) * circularList.getSize() + 1}" />">이전</a></li>
				</c:if>
				<c:forEach var="page" begin="${currentBlock * circularList.getSize() + 1}" end="${(currentBlock + 1) * circularList.getSize()}">
					<li class="page-item"><a class="page-link" href="circularList?page=<c:out value="${page}" />"><c:out value="${page}" /></a></li>
				</c:forEach>
				<c:if test="${(currentBlock + 1)* circularList.getSize() < circularList.getTotalPages() }">
					<li class="page-item"><a class="page-link" href="circularList?page=<c:out value="${(currentBlock + 1) * circularList.getSize() + 1}" />">다음</a></li>
				</c:if>
			</ul>
		</div>
	</div>
	<c:import url="footer.jsp"></c:import>
</body>
</html>