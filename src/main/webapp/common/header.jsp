<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<header class="head">
    <a href="<c:url value='/index.jsp'/>">
        <div class="logo">MYCLOSET</div>
    </a>
    <div class="search-box">
        <input type="text">
        <button>search</button>
    </div>

    <div class="header-icons">
        <a href="<c:url value='/register'/>" class="join-icon">JOIN</a>
        <a href="<c:url value='/login'/>" class="login-icon">LOGIN</a>
        <a href="#" class="my-icon">MY</a>
        <a href="#" class="cart-icon">CART</a>
    </div>
</header>

<nav>
    <ul class="main-nav">
        <li><a href="<c:url value='/productList'/>">NEW</a></li>
        <li><a href="#">BEST</a></li>
        <li><a href="#">WOMEN</a></li>
        <li><a href="#">MEN</a></li>
        <li><a href="#">EXCLUSIVE</a></li>
        <li><a href="#">SALE</a></li>
        <li><a href="#">기획전</a></li>
    </ul>
</nav>