<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="now" value="<%= new java.util.Date() %>" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품 수정</title>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/style.css'/>?v=${now}" />
<link rel="stylesheet" type="text/css" href="<c:url value='/css/productInsertForm.css'/>?v=${now}" />
<%-- <script src='<c:url value="/ckeditor/config.js" />'></script> --%>
</head>
<body>
   <div class="container">
        <%-- 헤더부분 include 액션 태그 사용, c:url 사용금지, 경로 직접 지정해야함. --%>
        <%--<jsp:include page="/common/header.jsp" /> --%>
        <main>
            <c:if test="${ not empty sessionScope.member }">
                <h3 id="insert-title">상품 수정</h3>
                <form action="<c:url value='/productUpdate'/>" method="post" enctype="multipart/form-data">
                	<input type="hidden" name="productNo" value="<c:out value='${product.productNo}'/>">
                    <div class="name-box">
                        <label for="productName">상품명</label>
                        <input type="text" id="productName" name="productName" required>
                    </div>
                    <div class="price-box">
                        <label for="price">가격</label>
                        <input type="text" id="price" name="price" required>
                    </div>
                    <div class="category-box">
                        <label for="category">카테고리</label>
                        <select id="categoryId" name="categoryId">
                            <option value="1">TOPS</option>
                            <option value="2">BOTTOMS</option>
                            <option value="3">DRESSES</option>
                            <option value="4">accessories</option>
                        </select>
                    </div>
                    <div>
                        <label for="fileName">이미지 첨부</label>
                        <input type="file" id="fileName" name="fileName">
                    </div>
                    <div>
                        <input type="submit" value="수정">
                        <input type="reset" value="다시쓰기">
                    </div>
                </form>
            </c:if>
            <c:if test="${ empty sessionScope.member }">
                <script>
                    alert('관리자만 상품을 수정할 수 있습니다');
                    window.location.href="${contextPath}/loginForm.jsp";
                </script>
            </c:if>
        </main>
    </div>
</body>
</html>
