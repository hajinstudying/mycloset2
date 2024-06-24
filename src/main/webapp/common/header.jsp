<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<header class="head">
    <a href="<c:url value='/index.jsp'/>">
        <div class="logo">MYCLOSET</div>
    </a>
    <div class="search-box">
        <form action="<c:url value='/productList'/>" method="get"
				class="search-form">
				<input type="text" name="keyword" placeholder="검색어 입력"> 
				<button>search</button>
		</form>		
    </div>

    <div class="header-icons">
    	<c:if test="${empty sessionScope.member}">
        	<a href="<c:url value='/member'/>" class="join-icon">JOIN</a>
        	<a href="<c:url value='/login'/>" class="login-icon">LOGIN</a>
        </c:if>
        <c:if test="${not empty sessionScope.member}">
        	<strong><c:out value="${sessionScope.member.name}"/></strong>
        	<a href="<c:url value='/logout'/>">LOGOUT</a>
        	<a href="<c:url value='/memberConfirm'/>" class="my-icon">MY</a>
        </c:if>
        <a href="#" class="cart-icon">CART</a>
    </div>
</header>

<nav>
    <ul class="main-nav">
        <li><a href="<c:url value='/productList'/>">TOP</a></li>
        <li><a href="#">BOTTOMS</a></li>
        <li><a href="#">DRESSES</a></li>
        <li><a href="#">ACCESSORIES</a></li>
    </ul>
</nav>