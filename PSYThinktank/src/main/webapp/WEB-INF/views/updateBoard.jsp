<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 수정</title>
<link rel="stylesheet" href="https://uicdn.toast.com/editor/latest/toastui-editor.css" />
<link rel="stylesheet" href="https://uicdn.toast.com/chart/latest/toastui-chart.min.css" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/prism/1.23.0/themes/prism.min.css" />
<link rel="stylesheet" href="https://uicdn.toast.com/editor-plugin-code-syntax-highlight/latest/toastui-editor-plugin-code-syntax-highlight.min.css" />
<link rel="stylesheet" href="https://uicdn.toast.com/tui-color-picker/latest/tui-color-picker.min.css" />
<link rel="stylesheet" href="https://uicdn.toast.com/editor-plugin-color-syntax/latest/toastui-editor-plugin-color-syntax.min.css" />
<link rel="stylesheet" href="https://uicdn.toast.com/editor-plugin-table-merged-cell/latest/toastui-editor-plugin-table-merged-cell.min.css" />

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script src="https://uicdn.toast.com/editor/latest/toastui-editor-all.min.js"></script>
<script src="https://uicdn.toast.com/editor/latest/i18n/ko-kr.js"></script>
<script src="https://uicdn.toast.com/chart/latest/toastui-chart.min.js"></script>
<script src="https://uicdn.toast.com/editor-plugin-chart/latest/toastui-editor-plugin-chart.min.js"></script>
<script src="https://uicdn.toast.com/editor-plugin-code-syntax-highlight/latest/toastui-editor-plugin-code-syntax-highlight-all.min.js"></script>
<script src="https://uicdn.toast.com/tui-color-picker/latest/tui-color-picker.min.js"></script>
<script src="https://uicdn.toast.com/editor-plugin-color-syntax/latest/toastui-editor-plugin-color-syntax.min.js"></script>
<script src="https://uicdn.toast.com/editor-plugin-table-merged-cell/latest/toastui-editor-plugin-table-merged-cell.min.js"></script>
<script src="https://uicdn.toast.com/editor-plugin-uml/latest/toastui-editor-plugin-uml.min.js"></script>
</head>
<body>
	<c:import url="header.jsp" />
	<div class="container-fluid">
		<h2 class="m-2">게시글 수정</h2>
		<div class="border rounded-3 m-2 p-3">
			<form id="form" action="board" method="post">
				<div class="mb-3 mt-3">
					<label for="title" class="form-label">제목</label>
					<input type="text" class="form-control" id="title" name="boardTitle" size="135" maxlength="200" value="<c:out value='${board.boardTitle}' />">
				</div>
				<div class="mb-3 mt-3">
					<label for="comment">내용</label>
					<div id="editor"></div> 
				</div>
				<div class="mb-3 mt-3 text-center">
					<label><input type="checkbox" id="isNotice" name="isNotice" <c:if test="${board.isNotice}">checked</c:if>>공지</label>
				</div>
				<input type="button" class="btn btn-secondary mx-2" value="글 입력" onclick="submitForm();">
				<button type="button" class="btn btn-secondary mx-2" onclick="location.href='boardList'">목록</button>
				<input id="content" type="hidden" name="boardContent" value="">
				<input type="hidden" name="boardNo" value="<c:out value="${board.boardNo}" />">
				<input type="hidden" name="boardHit" value="<c:out value="${board.boardHit}" />">
				<input type="hidden" name="writeDate" value="<c:out value="${board.writeDate}" />">
				<input type="hidden" name="memberId" value="<c:out value="${board.memberId}" />">
			</form>
		</div>
	</div>
	<c:import url="footer.jsp"></c:import>
	<script>
		const Editor = toastui;
		const { chart, codeSyntaxHighlight, colorSyntax, tableMergedCell, uml } = toastui.Editor.plugin;
		
		const chartOptions = {
		        minWidth: 100,
		        maxWidth: 600,
		        minHeight: 100,
		        maxHeight: 300
		      };
		
		let content = "${board.boardContent}";
		const editor = new Editor.Editor({
			usageStatistics: false,
			language: 'ko',
		    el: document.querySelector('#editor'),
		    previewStyle: 'tab',
		    height: '500px',
		    initialValue: content,
		    initialEditType: 'wysiwyg',
		    plugins: [[chart, chartOptions], [codeSyntaxHighlight, { highlighter: Prism }], colorSyntax, tableMergedCell, uml]
		  });
		function submitForm() {
			if($("#title").val().trim() != "") {
				$("#content").val(editor.getHTML().replaceAll("\"", "\'"));
				$("#form").submit();
			} else {
				alert("제목을 입력해주세요.");
			}			
		}
	</script>
</body>
</html>