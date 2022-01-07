<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 작성</title>
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
	<div class="container-fluid" style="height:80vh">
		<h2 class="m-2">회보 올리기</h2>
		<div class="border rounded-3 p-3">
			<form action="circular" method="post" enctype="multipart/form-data">
				<div class="mb-3 mt-3">
					<label for="title" class="form-label">제목</label>
					<input type="text" class="form-control" id="circularTitle" name="circularTitle" size="135" maxlength="200" required>
				</div>
				<div class="mb-3 mt-3">
					<label for="file">파일</label>
					<input type="file" class="form-control" id="file" name="file" required> 
				</div>
				<input type="submit" class="btn btn-secondary mx-2" value="업로드">
				<button type="button" class="btn btn-secondary mx-2" onclick="location.href='circularList'">목록</button>
				<input type="hidden" name="memberId" value="<c:out value='${sessionScope.member.memberId}' />">
			</form>
		</div>
	</div>
	<c:import url="footer.jsp"></c:import>
</body>
</html>