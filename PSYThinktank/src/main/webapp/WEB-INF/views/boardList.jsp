<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판</title>
</head>
<body>
	<c:import url="header.jsp" />
	<div class="container-fluid">
		<h2 class="m-2">게시판</h2>
		<table class="table text-center">
			<thead class="table-dark">
				<tr>
					<td>번호</td>
					<td class="w-50">제목</td>
					<td>작성자</td>
					<td>조회수</td>
					<td>작성일</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="i" items="${boardList.getContent()}">
					<tr <c:if test="${i.isNotice == true}">class="table-secondary"</c:if>>
						<c:url var="link" value="/board">
							<c:param name="boardNo" value="${i.boardNo}" />
						</c:url> 
						<td>
							<c:choose>
								<c:when test="${i.isNotice == true}">
									<b>공지</b>
								</c:when>
								<c:otherwise>
									${i.boardNo}
								</c:otherwise>
							</c:choose>
						</td>
						<td>
							<c:choose>
								<c:when test="${i.isNotice == true}">
									<b><a href="${link }">${i.boardTitle}</a></b>
								</c:when>
								<c:otherwise>
									<a href="${link}">${i.boardTitle}</a>
								</c:otherwise>
							</c:choose>	
						</td>
						<td>${i.memberId}</td>
						<td>${i.boardHit}</td>
						<td><fmt:formatDate value="${i.writeDate}" pattern="yyyy-MM-dd hh:mm:ss" /></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<button class="btn btn-dark m-2" onclick="location.href='insertBoard'">글 작성</button>
		<div class="d-flex justify-content-center">
			<ul class="pagination">
				<c:if test="${currentBlock > 0}">
					<li class="page-item"><a class="page-link" href="boardList?page=<c:out value="${(currentBlock - 1) * boardList.getSize() + 1}" />">이전</a></li>
				</c:if>
				<c:forEach var="page" begin="${currentBlock * boardList.getSize() + 1}" end="${(currentBlock + 1) * boardList.getSize()}">
					<li class="page-item"><a class="page-link" href="boardList?page=<c:out value="${page}" />"><c:out value="${page}"/></a></li>
				</c:forEach>
				<c:if test="${(currentBlock + 1)* boardList.getSize() < boardList.getTotalPages() }">
					<li class="page-item"><a class="page-link" href="boardList?page=<c:out value="${(currentBlock + 1) * boardList.getSize() + 1}" />">다음</a></li>
				</c:if>
			</ul>
		</div>
	</div>
	<c:import url="footer.jsp"></c:import>
</body>
</html>