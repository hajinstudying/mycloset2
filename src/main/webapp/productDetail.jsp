<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="now" value="<%= new java.util.Date()%>"/>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>productDetail</title>
<%-- css 연결 --%>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/style.css'/>?v=${now}" />
<link rel="stylesheet" type="text/css" href="<c:url value='/css/productDetail.css'/>?v=${now}" />
</head>
<body>
<%-- header (c:url 사용 금지, 경로를 직접 지정해야함)--%>
<jsp:include page="/common/header.jsp" />
<%-- main --%>
<main>
		<%-- admin 계정 활성화 링크 --%>
		<div class="adminBtn">
		    <c:if test="${sessionScope.member.memberId eq 'admin'}">
		        <a href="<c:url value='/productUpdate'/>?productNo=${productVO.productNo}" class="prodUpdateBtn">상품수정</a>
		        <a href="<c:url value='/productDelete'/>?productNo=${productVO.productNo}" class="prodDeleteBtn">상품삭제</a>            
		    </c:if>
		</div>
        <%-- 상품 페이지 --%>
        <div class="product-container">
            <div class="img-container">
                <img src="<c:url value='/img/${productVO.fileName}'/>" alt="productImg1" class="product-img">
            </div>
            <section class="product-detail">
                <h2 class="product-name"><c:out value="${productVO.productName}"/></h2>
                <p>[06/13 예약배송]</p>
                <div class="review">
                    <span id="review-star">&#10030;&#10030;&#10030;&#10030;&#10030;</span>
                    <span>(4.9 점)</span>
                    <a href="#" id="review-link">2,245개 리뷰 ></a>
                    <a href="#" id="share-icon"><i class="fa-solid fa-share-nodes"></i></a>
                </div>
                <%-- fmt type을 currency로 하면 앞에 \기호가 붙는데 별로여서 number 타입 패턴으로 출력--%>
                <p>가격 : <fmt:formatNumber value="${productVO.price}" type="number" pattern="#,###"/>원 </p>
                <p>신규회원 가격 : <fmt:formatNumber value="${productVO.price *0.8}" type="number" pattern="#,###"/>원 <span id="newMember">신규가입시 20% 할인 쿠폰 제공</span></p>
                <div class="divider"></div>
                <span class="inputLbl">색상 선택 :</span>
                <select name="color" id="colorBox">
                    <option value="">선택해주세요.</option>
                    <option value="charcoal">charcoal</option>
                    <option value="white">white</option>
                    <option value="black">black</option>
                    <option value="blue">blue</option>
                </select>
                <div>
                    <span class="inputLbl">사이즈 선택 :</span>
                    <select name="size" id="sizeBox">
                        <option value="">선택해주세요.</option>
                        <option value="S">Small</option>
                        <option value="M">Medium</option>
                        <option value="L">Large</option>
                        <option value="XL">XLarge</option>
                        <option value="XXL">XXLarge</option>
                    </select>
                </div>
                <div class="orderBox">
                    <p id="orderDetail">? / ?</p>
                    <input type="number" name="quantity" id="quantity" value=1>
                    <span id="totalPrice">45,000원</span>
                </div>

                <div class="buttons">
                    <button type="button">주문하기</button>
                    <button type="button">장바구니</button>
                </div>
            </section>
        </div>
    </main>
<%-- footer --%>
<jsp:include page="/common/footer.jsp" />
<script type="text/javascript" src="<c:url value='/script/productDetail.js'/>"></script>

</body>
</html>