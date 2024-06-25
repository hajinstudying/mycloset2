<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="now" value="<%= new java.util.Date()%>"/>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
<%-- css 연결 --%>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/style.css'/>?v=${now}" />
<link rel="stylesheet" type="text/css" href="<c:url value='/css/loginForm.css'/>?v=${now}" />
</head>
<body>
<%-- header --%>
<jsp:include page="/common/header.jsp" />
<%-- main --%>
<main>
	<%-- 에러 메세지 출력 --%>
	<c:if test="${not empty error}">
		<script style='color: red'>
			alert('${error}');
		</script>
		 <c:remove var="error" scope="request" />
	</c:if>
	<%-- 회원가입 폼 --%>
	<div>
	    <h2 id="login-title">My Page</h2>
	    <h4>비밀 번호를 인증해주세요.</h4>
	</div>
	<form action="<c:url value='/memberConfirm'/>" method="post" name="loginForm" id="loginForm">
	    <div class="login-wrap">
	        <div>
	            <label for="memberId">ID</label>
	            <input type="text" name="memberId" id="memberId"
	            	value="${sessionScope.member.memberId}" readonly>
	        </div>
	        <div>
	            <label for="password">비밀번호</label>
	            <input type="password" name="password" id="password" placeholder="영문+숫자+특수문자 조합 4자리이상" required>
	        </div>
	        <div class="button-wrap">
	            <button type="button" class="register" id="registerBtn"
	            	onclick="location.href='<c:url value='/index.jsp'/>';">취소</button>
	            <button type="submit" class="login" id="loginBtn">비밀번호 확인</button>
	        </div>
	    </div>
	</form>
</main>
<%-- footer --%>
<jsp:include page="/common/footer.jsp" />
</body>
</html>