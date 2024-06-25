<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="now" value="<%= new java.util.Date() %>" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품 삭제</title>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/style.css'/>?v=${now}" />
<link rel="stylesheet" type="text/css" href="<c:url value='/css/productInsertForm.css'/>?v=${now}" />
<style>
    .container {
        text-align: center;
        margin-top: 50px;
    }
    .container h3 {
        margin-bottom: 30px;
    }
    .container .confirmation {
        margin-top: 20px;
    }
    .container .buttons {
        margin-top: 30px;
    }
    .container .buttons input, .container .buttons a {
        margin: 0 10px;
        padding: 10px 20px;
        text-decoration: none;
        color: white;
        background-color: #007BFF;
        border: none;
        cursor: pointer;
        font-size: 16px;
    }
    .container .buttons a {
        background-color: #6c757d;
    }
</style>
</head>
<body>
    <div class="container">
        <%-- 헤더부분 include 액션 태그 사용, c:url 사용금지, 경로 직접 지정해야함. --%>
        <%--<jsp:include page="/common/header.jsp" /> --%>
        <main>
            <c:if test="${sessionScope.member.memberId eq 'admin'}">
                <h3>상품 삭제</h3>
                <p class="confirmation">정말로 이 상품을 삭제하시겠습니까?</p>
                <form action="<c:url value='/productDelete'/>" method="post">
                    <input type="hidden" name="productNo" value="<c:out value='${productNo}'/>">
                    <div class="buttons">
                        <input type="submit" value="삭제">
                        <a href="<c:url value='/productList'/>">취소</a>
                    </div>
                </form>
            </c:if>
            <c:if test="${sessionScope.member.memberId != 'admin'}">
                <script>
                    alert('관리자만 상품을 삭제할 수 있습니다');
                    window.location.href="${contextPath}/loginForm.jsp";
                </script>
            </c:if>
        </main>
    </div>
</body>
</html>
