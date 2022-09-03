<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>주식 검색</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script type="text/javascript">
        function search() {
            let searchText = $("#searchText").val();
            if (searchText != "") {
                let regex = /^([0-9]+)$/;
                if (regex.test(searchText)) {
                    $
                        .ajax({
                            url: "searchByStockCode",
                            type: "post",
                            data: searchText,
                            dataType: "json",
                            contentType: "application/json; charset=utf-8"
                        })
                        .done(
                            function (data) {
                                var list = data.result;
                                $("#result").empty();
                                for (var i = 0; i < list.length; i++) {
                                    $("#result")
                                        .append(
                                            "<tr><td>"
                                            + list[i].stockCode
                                            + "</td>"
                                            + "<td><a href='stock?stockCode="
                                            + list[i].stockCode
                                            + "'>"
                                            + list[i].stockName
                                            + "</a></td>"
                                            + "<td>"
                                            + list[i].corpCls
                                            + "</td>"
                                            + "</td></tr>");
                                }
                                ;
                                $(".pagination").remove();
                            }).fail(function (error) {
                        alert(error);
                    });
                } else {
                    $
                        .ajax({
                            url: "searchByStockName",
                            type: "post",
                            data: searchText,
                            dataType: "json",
                            contentType: "application/json; charset=utf-8"
                        })
                        .done(
                            function (data) {
                                var list = data.result;
                                $("#result").empty();
                                for (var i = 0; i < list.length; i++) {
                                    $("#result")
                                        .append(
                                            "<tr><td>"
                                            + list[i].stockCode
                                            + "</td>"
                                            + "<td><a href='stock?stockCode="
                                            + list[i].stockCode
                                            + "'>"
                                            + list[i].stockName
                                            + "</a></td>"
                                            + "<td>"
                                            + list[i].corpCls
                                            + "</td>"
                                            + "</td></tr>");
                                }
                                ;
                                $(".pagination").remove();
                            }).fail(function (error) {
                        alert(error);
                    });
                }
            } else {
                alert("검색어가 없습니다.");
            }
        };

        $(document).ready(function () {
            $("#searchBtn").click(search);
            $("#searchText").focus().keypress(function (event) {
                if (event.keyCode == 13) {
                    search();
                }
            });
        });
    </script>
</head>
<body>
<c:import url="header.jsp"/>
<div class="container-fluid">
    <h2 class="m-2">주식 종목</h2>
    <div class="d-flex justify-content-center m-2">
        <input type="text" id="searchText" class="form-control mx-2" name="searchText" placeholder="종목명 또는 종목코드"> <input type="button" id="searchBtn" class="btn btn-secondary mx-2" name="searchBtn" value="검색">
    </div>
    <table class="table text-center table-striped">
        <thead class="table-dark">
        <tr>
            <td>종목코드</td>
            <td>종목명</td>
            <td>구분</td>
        </tr>
        </thead>
        <tbody id="result">
        <c:forEach var="i" items="${stockList.getContent()}">
            <tr>
                <c:url var="link" value="/stock">
                    <c:param name="stockCode" value="${i.stockCode}"/>
                </c:url>
                <td><c:out value="${i.stockCode}"/></td>
                <td><a href="${link}"><c:out value="${i.stockName}"/></a></td>
                <td><c:out value="${i.corpCls}"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div class="d-flex justify-content-center">
        <ul class="pagination">
            <c:if test="${currentBlock > 0}">
                <li class="page-item"><a class="page-link" href="stockList?page=<c:out value="${(currentBlock - 1) * stockList.getSize() + 1}" />">이전</a></li>
            </c:if>
            <c:forEach var="page" begin="${currentBlock * stockList.getSize() + 1}" end="${(currentBlock + 1) * stockList.getSize()}">
                <li class="page-item"><a class="page-link" href="stockList?page=<c:out value="${page}" />"><c:out value="${page}"/></a></li>
            </c:forEach>
            <c:if test="${(currentBlock + 1) * stockList.getSize() < stockList.getTotalPages() }">
                <li class="page-item"><a class="page-link" href="stockList?page=<c:out value="${(currentBlock + 1) * stockList.getSize() + 1}" />">다음</a></li>
            </c:if>
        </ul>
    </div>
</div>
<c:import url="footer.jsp"></c:import>
</body>
</html>