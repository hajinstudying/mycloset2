<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="now" value="<%= new java.util.Date()%>"/>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Example page</title>
<%-- css 연결 --%>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/style.css'/>?v=${now}" />
<link rel="stylesheet" type="text/css" href="<c:url value='/css/productList.css'/>?v=${now}" />
</head>
<body>
<%-- header (c:url 사용 금지, 경로를 직접 지정해야함)--%>
<jsp:include page="/common/header.jsp" />

<%--main

참고 예시로 예전에 만들었던 productlist.html을 가져왔습니다.
header랑 footer만 분리해뒀고 그대로에요.

--%>
<main>
        <div class="container">
            <div class="product">
                <a href="./product1.html">
                <img src="./img/list1.jpeg" alt="상품1"></a>
                <div class="product-info">
                    <p class="name">Basset Hound T-shirt</p>
                    <p class="price">45,000</p>
                </div>
            </div>
            <div class="product">
                <a href="#">
                <img src="./img/list2.jpeg" alt="상품2"></a>
                <div class="product-info">
                    <p class="name">U-neck T-shirt</p>
                    <p class="price">36,600</p>
                </div>
            </div>
            <div class="product">
                <img src="./img/list3.jpeg" alt="상품3">
                <div class="product-info">
                    <p class="name">Basic Square T-shirt</p>
                    <p class="price">48,000</p>
                </div>
            </div>
            <div class="product">
                <img src="./img/list4.jpeg" alt="상품4">
                <div class="product-info">
                    <p class="name">라운드넥 부클 트위드 자켓</p>
                    <p class="price">328,000</p>
                </div>
            </div>
            <div class="product">
                <img src="./img/list5.jpeg" alt="상품5">
                <div class="product-info">
                    <p class="name">Summer volume OPS</p>
                    <p class="price">169,000</p>
                </div>
            </div>
            <div class="product">
                <img src="./img/list6.jpeg" alt="상품6">
                <div class="product-info">
                    <p class="name">unisex banding pants</p>
                    <p class="price">28,900</p>
                </div>
            </div>
            <div class="product">
                <img src="./img/list7.jpeg" alt="상품7">
                <div class="product-info">
                    <p class="name">브루클린 미니 쭈리 반팔</p>
                    <p class="price">43,000</p>
                </div>
            </div>
            <div class="product">
                <img src="./img/list8.jpeg" alt="상품8">
                <div class="product-info">
                    <p class="name">Light Cotton Button </p>
                    <p class="price">109,000</p>
                </div>
            </div>
            <div class="product">
                <img src="./img/list9.jpeg" alt="상품9">
                <div class="product-info">
                    <p class="name">Margaret Artwork T-shirt</p>
                    <p class="price">39,000</p>
                </div>
            </div>
            <div class="product">
                <img src="./img/list10.jpeg" alt="상품10">
                <div class="product-info">
                    <p class="name">페이트로 세미와이드핏 팬츠</p>
                    <p class="price">69,000</p>
                </div>
            </div>
            <div class="product">
                <img src="./img/list11.jpeg" alt="상품11">
                <div class="product-info">
                    <p class="name">[1+1]Sleeveless TEE</p>
                    <p class="price">64,000</p>
                </div>
            </div>
            <div class="product">
                <img src="./img/list12.jpeg" alt="상품12">
                <div class="product-info">
                    <p class="name">Multi Cotton T-shirt</p>
                    <p class="price">48,000</p>
                </div>
            </div>
        </div>
    </main>
<%-- footer --%>
<jsp:include page="/common/footer.jsp" />
</body>
</html>