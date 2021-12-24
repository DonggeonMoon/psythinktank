<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 수정</title>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script type="text/javascript">
	function checkNotice() {
		let isNotice = $("#isNotice").val();
		console.log(isNotice);
		if (isNotice == 'true') $("#isNotice").val('false');
		else $("#isNotice").val('true');
	}
</script>
</head>
<body>
	<c:import url="header.jsp" />
	<div class="container-fluid">
		<h2 class="m-2">게시글 수정</h2>
		<div class="border rounded-3 p-3">
			<form action="board" method="post">
				<div class="mb-3 mt-3">
					<label for="title" class="form-label">제목</label>
					<input type="text" class="form-control" id="title" name="boardTitle" size="135" maxlength="200" value="<c:out value='${board.boardTitle}' />">
				</div>
				<div class="mb-3 mt-3">
					<label for="comment">내용</label>
					<textarea class="form-control" rows="5" id="comment" name="boardContent" rows="20" cols="100"><c:out value='${board.boardContent}' /></textarea> 
				</div>
				<div class="mb-3 mt-3 text-center">
					<label><input type="checkbox" id="isNotice" name="isNotice" value="false" onchange="checkNotice()" <c:if test="${board.isNotice}">checked</c:if>>공지</label>
				</div>
				<input type="submit" class="btn btn-secondary mx-2" value="글 입력">
				<button type="button" class="btn btn-secondary mx-2" onclick="location.href='boardList'">목록</button>
				<input type="hidden" name="boardNo" value="<c:out value="${board.boardNo}" />">
				<input type="hidden" name="boardHit" value="<c:out value="${board.boardHit}" />">
				<input type="hidden" name="writeDate" value="<c:out value="${board.writeDate}" />">
				<input type="hidden" name="memberId" value="<c:out value="${board.memberId}" />">
			</form>
		</div>
	</div>
	<c:import url="footer.jsp"></c:import>
</body>
</html>