<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="now" value="${now}" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>produtList.jsp</title>
<%-- css 연결 --%>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/style.css'/>?v=${now}" />
<link rel="stylesheet" type="text/css" href="<c:url value='/css/productList.css'/>?v=${now}" />
</head>
<body>
<%-- header (c:url 사용 금지, 경로를 직접 지정해야함) --%>
<jsp:include page="/common/header.jsp" />

<%--main

참고 예시로 예전에 만들었던 productlist.html을 가져왔습니다.
header랑 footer만 분리해뒀고 그대로에요.

--%>
<main>
<c:if test="${empty productList}">
    <p>상품이 존재하지 않습니다.</p>
</c:if>

<c:if test="${not empty productList}">
<table>
    <thead>
        <tr>
            <th>카테고리 명</th>
            <th>상품 번호</th>
            <th>상품 이름</th>
            <th>가격</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="product" items="${productList }">
            <tr>
                <td><c:out value="${product.categoryName}" /></td>
                <td><a href="<c:url value='/productDetail'/>?productNo=${product.productNo}">
                        ${product.productName }</a></td>
                <td><c:out value="${product.price }" /></td>
            </tr>
        </c:forEach>
    </tbody>
</table>
<div class="pagination"> ${page_navigator}</div>
</c:if>
</main>		
<%-- footer --%>
<jsp:include page="/common/footer.jsp" />
</body>
</html>
