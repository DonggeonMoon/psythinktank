<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>관리자 페이지</title>
</head>
<body>
	<c:import url="header.jsp" />
	<div class="container-fluid">
		<h2 class="m-2">관리자 페이지</h2>
		<table class="table text-center">
			<thead class="table-dark">
				<tr>
					<td>아이디</td>
					<td>이메일</td>
					<td>사용자 레벨</td>
					<td>권한 변경</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="i" items="${memberList.getContent() }">
					<tr>
						<td><c:out value="${i.memberId }" /></td>
						<td><c:out value="${i.memberEmail }" /></td>
						<td><c:out value="${i.userLevel }" /></td>
						<td>
							<c:if test="${i.userLevel != 3 }">
								<form class="d-inline" action="changeUserLevel" method="post">
									<input type="hidden" name="memberId" value="${i.memberId }">
									<input type="hidden" name="userLevel" value="2">
									<input type="submit" value="관리자 승격">
								</form>
								<form class="d-inline" action="changeUserLevel" method="post">
									<input type="hidden" name="memberId" value="${i.memberId }">
									<input type="hidden" name="userLevel" value="1">
									<input type="submit" value="회원으로 강등">
								</form>
							</c:if>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<div class="d-flex justify-content-center">
			<ul class="pagination">
				<c:if test="${currentBlock > 0}">
					<li class="page-item"><a class="page-link" href="managerPage?page=<c:out value="${(currentBlock - 1) * 10 + 1}" />">이전</a></li>
				</c:if>
				<c:forEach var="page" begin="${currentBlock * 10 + 1}" end="${(currentBlock + 1) * 10}">
					<li class="page-item"><a class="page-link" href="managerPage?page=<c:out value="${page}" />"><c:out value="${page}"/></a></li>
				</c:forEach>
				<c:if test="${(currentBlock + 1) * memberList.getSize() < memberList.getTotalPages() }">
					<li class="page-item"><a class="page-link" href="managerPage?page=<c:out value="${(currentBlock + 1) * 10 + 1}" />">다음</a></li>
				</c:if>
			</ul>
		</div>
	</div>
	<c:import url="footer.jsp"></c:import>
</body>
</html>