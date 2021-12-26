<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 보기</title>
</head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<body>
	<c:import url="header.jsp" />
	<div class="container-fluid" style="height:80vh">
		<h2 class="m-2">게시글 보기</h2>
		<table class="table text-center">
			<colgroup>
				<col style="width:20%"/>
				<col style="width:80%"/>
			</colgroup>
			<tr>
				<td class="table-light">제목</td>
				<td><c:out value="${board.boardTitle}" /></td>
			</tr>
			<tr>
				<td class="table-light">작성자</td>
				<td><c:out value="${board.memberId}" /></td>
			</tr>
			<tr>
				<td class="table-light">조회수</td>
				<td><c:out value="${board.boardHit}" /></td>
			</tr>
			<tr>
				<td class="table-light">내용</td>
				<td><c:out value="${board.boardContent}" /></td>
			</tr>
			<tr style="text-align:right;">
				<td colspan="2">
					<input type="button" class="btn btn-secondary my-2" value="수정" onclick="location.href='updateBoard?boardNo=${board.boardNo}'">
					<form style="display:inline" action="board" method="post">
						<input type="hidden" name="_method" value="delete">
						<input type="submit" class="btn btn-secondary my-2" value="삭제">
						<input type="hidden" name="memberId" value="<c:out value='${board.memberId}' />">
						<input type="hidden" name="boardNo" value="<c:out value='${board.boardNo}' />">
					</form>
				</td>
			</tr>
		</table>
		<button class="btn btn-dark my-2" onclick="location.href='boardList'">목록</button>

		<div class="container-fluid">
			<table class="table">
				<c:forEach var="i" items="${commentList}">
					<table class="table">
						<tr>
							<td class="table-light">
								<c:forEach begin="0" end="${i.commentDepth - 1}">&nbsp;&nbsp;</c:forEach>
								<c:if test="${(i.commentDepth - 1) > 0}">└</c:if>
								<c:out value="${i.memberId}" />
							</td>
						</tr>
						<tr>
							<td colspan="${6 - (i.commentDepth - 1)}">
								<c:forEach begin="0" end="${i.commentDepth - 1}">
									&nbsp;&nbsp;&nbsp;
								</c:forEach>
								<c:out value="${i.commentContent}"/>
								<div class="d-block text-end">
									<button class="badge bg-secondary my-2" data-bs-toggle="collapse" data-bs-target="#addcomment<c:out value="${i.commentNo}-${i.commentDepth}-${i.commentSeq + 1}" />">댓글 달기</button>
									<button class="badge bg-secondary my-2" data-bs-toggle="collapse" data-bs-target="#editcomment<c:out value="${i.commentNo}-${i.commentDepth}-${i.commentSeq + 1}" />">댓글 수정</button>
									<form style="display:inline;" action="comment" method="post">
										<input type="hidden" name="_method" value="delete">	
										<input type="hidden" name="boardNo" value="<c:out value='${board.boardNo}' />">
										<input type="hidden" name="commentNo" value="${i.commentNo}">
										<button type="submit" class="badge bg-secondary my-2">댓글 삭제</button>
									</form>
								</div>
								<div id="addcomment<c:out value="${i.commentNo}-${i.commentDepth}-${i.commentSeq + 1}" />" class="collapse">
									<div>
										<form class="d-inline" action="comment" method="post">
											<input type="hidden" name="boardNo" value="<c:out value='${board.boardNo}' />">
											<input type="hidden" name="commentParent" value="<c:out value='${i.commentNo}' />">
											<input type="hidden" name="commentDepth" value="<c:out value='${i.commentDepth + 1}' />">
											<input type="hidden" name="commentSeq" value="<c:out value='${i.commentSeq + 1}' />">
											<input type="hidden" name="memberId" value="<c:out value='${sessionScope.member.memberId}' />">
											<textarea class="w-100" name="commentContent" cols="100"></textarea>
											<div class="text-end">
												<button type="submit" class="badge bg-secondary my-2">댓글 달기</button>
											</div>
										</form>
									</div>
								</div>
								<div id="editcomment<c:out value="${i.commentNo}-${i.commentDepth}-${i.commentSeq + 1}" />" class="collapse">
									<div>
										<form class="d-inline" action="comment" method="post">
											<input type="hidden" name="_method" value="put">
											<input type="hidden" name="boardNo" value="<c:out value='${board.boardNo}' />">
											<input type="hidden" name="commentNo" value="${i.commentNo}">
											<input type="hidden" name="commentParent" value="<c:out value='${i.commentParent}' />">
											<input type="hidden" name="commentDepth" value="<c:out value='${i.commentDepth}' />">
											<input type="hidden" name="commentSeq" value="<c:out value='${i.commentSeq}' />">
											<input type="hidden" name="commentDate" value="${i.commentDate}">
											<input type="hidden" name="memberId" value="<c:out value='${i.memberId}' />">
											<textarea class="w-100" name="commentContent" cols="100">${i.commentContent}</textarea>
											<div class="text-end">
												<button type="submit" class="badge bg-secondary my-2">댓글 수정</button>
											</div>
										</form>
									</div>
								</div>
						</td>
						</tr>
					</table>
					<tr>
						<c:forEach begin="0" end="${i.commentDepth - 1}" step="1">
							<td class="border"></td>
						</c:forEach>
					</tr>
				</c:forEach>
				<tr class="text-end">
					<td colspan="6">
						<form class="d-flex justify-content-end" action="comment" method="post">
							<textarea class="w-100" name="commentContent" cols="100"></textarea>
							<input type="hidden" name="boardNo" value="<c:out value='${board.boardNo}' />">						
							<input type="hidden" name="commentDepth" value="<c:out value='1' />">
							<input type="hidden" name="commentSeq" value="<c:out value='1' />">
							<input type="hidden" name="memberId" value="<c:out value='${sessionScope.member.memberId}' />">
							<input type="submit" class="btn btn-dark mx-2" value="댓글 달기">
						</form>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<c:import url="footer.jsp"></c:import>
</body>
</html>