<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="now" value="${now}" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>produtList.jsp</title>
<%-- css 연결 --%>
<link rel="stylesheet" type="text/css"
	href="<c:url value='/css/style.css'/>?v=${now}" />
<%-- <link rel="stylesheet" type="text/css" href="<c:url value='/css/productList.css'/>?v=${now}" /> --%>

<style>
main {
    margin: 0 auto;
    width: 90%;
    max-width: 1200px;
    margin-bottom: 100px;
}

.product-list {
    display: flex;
    flex-wrap: wrap;
    justify-content: space-between;
}

.product-item {
    flex-basis: calc(25% - 10px); /* 한 줄에 4개씩 */
    text-align: center;
    margin-bottom: 20px;
}

.product-item img {
    display: block;
    margin: 0 auto;
    width: 100%;
    height: auto;
    margin-top: 20px;
}
.product-category,
.product-name,
.product-price {
	text-align: left;
    margin: 10px;
    font-family: sans-serif;
}
.product-category{
    font-size: 16px;
}

.product-name a {
    color: gray; /* 회색 */
    text-decoration: none;
}

.product-price {
    font-size: 16px;
}

.pagination {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}

.pagination a {
  color: #333;
  padding: 4px 8px;
  text-decoration: none;
  transition: background-color 0.3s;
}

.pagination a.active {
  background-color: #4CAF50;
  color: white;
}

.pagination a:hover:not(.active) {
  background-color: #ddd;
}

</style>
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
			<div class="product-list">
				<c:forEach var="product" items="${productList }">
					<div class="product-item">
						<%-- <img src="<c:url value='/images/${product.imageUrl}'/>" alt="${product.productName}" class="product-image"> --%>
						<img src="./img/list1.jpeg" alt="list1">
						<div class="product-category"><c:out value="${product.categoryName }" /></div>
						<div class="product-name">
							<a href="<c:url value='/productDetail'/>?productNo=${product.productNo}">${product.productName}</a>
						</div>
						<div class="product-price"><c:out value="${product.price }" /></div>
					</div>
				</c:forEach>
			</div>


			<div class="pagination">${page_navigator}</div>
		</c:if>
	</main>
	<%-- footer --%>
	<jsp:include page="/common/footer.jsp" />
</body>
</html>
