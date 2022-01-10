<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script type="text/javascript">
	function search() {
		let searchText = $("#searchText").val();	
		if (searchText != "") {
			let selectIndex = $("#select option").index($('#select option:selected'));
			switch (selectIndex) {
				case 0:
					$.ajax({
						url: "searchByBoardTitle",
						type: "post",
						data: searchText,
						dataType: "json",
						contentType: "application/json; charset=utf-8"
					}).done(function(data) {
						var list = data.result;
						$("#result").empty();
						for (var i = 0; i < list.length; i++) {
							let date = new Date(list[i].writeDate)
							$("#result").append("<tr><td>" + list[i].boardNo + "</td>"
									+ "<td><a href='board?boardNo="+ list[i].boardNo +"'>" + list[i].boardTitle + "</a></td>"
									+ "<td>" + list[i].memberId + "</td>"
									+ "<td>" + list[i].boardHit + "</td>"
									+ "<td>" + date.getFullYear() + "-" + ("0" + (date.getMonth() + 1)).slice(-2) + "-" + ("0" + date.getDate()).slice(-2) + " " + ("0" + date.getHours() % 12).slice(-2) + ":" + ("0" + date.getMinutes()).slice(-2) + ":" + ("0" + date.getSeconds()).slice(-2) +
									"</td></tr>");
						};
						$(".pagination").remove();
					}).fail(function(error) {
						alert(error);
					});
					break;
				case 1:
					$.ajax({
						url: "searchByBoardContent",
						type: "post",
						data: searchText,
						dataType: "json",
						contentType: "application/json; charset=utf-8"
					}).done(function(data) {
						var list = data.result;
						$("#result").empty();
						for (var i = 0; i < list.length; i++) {
							let date = new Date(list[i].writeDate)
							$("#result").append("<tr><td>" + list[i].boardNo + "</td>"
									+ "<td><a href='board?boardNo="+ list[i].boardNo +"'>" + list[i].boardTitle + "</a></td>"
									+ "<td>" + list[i].memberId + "</td>"
									+ "<td>" + list[i].boardHit + "</td>"
									+ "<td>" + date.getFullYear() + "-" + ("0" + (date.getMonth() + 1)).slice(-2) + "-" + ("0" + date.getDate()).slice(-2) + " " + ("0" + date.getHours() % 12).slice(-2) + ":" + ("0" + date.getMinutes()).slice(-2) + ":" + ("0" + date.getSeconds()).slice(-2) +
									"</td></tr>");
						};
						$(".pagination").remove();
					}).fail(function(error) {
						alert(error);
					});
					break;
				case 2:
					$.ajax({
						url: "searchByMemberId",
						type: "post",
						data: searchText,
						dataType: "json",
						contentType: "application/json; charset=utf-8"
					}).done(function(data) {
						var list = data.result;
						$("#result").empty();
						for (var i = 0; i < list.length; i++) {
							let date = new Date(list[i].writeDate)
							$("#result").append("<tr><td>" + list[i].boardNo + "</td>"
									+ "<td><a href='board?boardNo="+ list[i].boardNo +"'>" + list[i].boardTitle + "</a></td>"
									+ "<td>" + list[i].memberId + "</td>"
									+ "<td>" + list[i].boardHit + "</td>"
									+ "<td>" + date.getFullYear() + "-" + ("0" + (date.getMonth() + 1)).slice(-2) + "-" + ("0" + date.getDate()).slice(-2) + " " + ("0" + date.getHours() % 12).slice(-2) + ":" + ("0" + date.getMinutes()).slice(-2) + ":" + ("0" + date.getSeconds()).slice(-2) +
									"</td></tr>");
						};
						$(".pagination").remove();
					}).fail(function(error) {
						alert(error);
					});
					break;
				default:
					break;
				}
			} else {
				alert("검색어가 없습니다.");
			}
	};
	
	$(document).ready(function() {
		$("#searchBtn").click(search);
		$("#searchText").focus().keypress(function(event) {
			if (event.keyCode == 13) {
				search();
			}
		});
	});
</script>
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
			<tbody id="result">
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
			<select id="select" class="form-select mx-2" style="width:100px">
				<option>제목</option>
				<option>내용</option>
				<option>작성자</option>
			</select>
			<input type="text" id="searchText" class="d-inline form-control mx-2" style="width:200px" name="searchText" placeholder="검색어를 입력하세요.">
			<input type="button" id="searchBtn" class="d-inline btn btn-secondary mx-2" name="searchBtn" value="검색">
		</div>
		<div class="d-flex justify-content-center m-2">
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