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
	<%-- 회원가입 성공 메세지 출력 --%>
	<c:if test="${not empty sessionScope.message}">
		<script>
			alert('${sessionScope.message}');
		</script>
		 <c:remove var="message" scope="session" />
	</c:if>
	<%-- 회원가입 폼 --%>
	<div>
	    <h2 id="login-title">Login</h2>
	</div>
	<form action="<c:url value='/login'/>" method="post" name="loginForm">
	    <div class="login-wrap">
	        <div>
	            <label for="memberId">ID</label>
	            <input type="text" name="memberId" id="memberId" required>
	        </div>
	        <div>
	            <label for="password">비밀번호</label>
	            <input type="password" name="password" id="password" placeholder="영문+숫자+특수문자 조합 4자리이상" required>
	        </div>
	        <div class="find-link">
	            <a href="#" id="find-id">아이디 찾기</a> <a href="#" id="find-password">비밀번호 찾기</a>
	        </div>
	        <div class="button-wrap">
	            <button type="button" class="register" id="registerBtn"
	            	onclick="location.href='<c:url value='/member'/>';">회원가입</button>
	            <button type="submit" class="login" id="loginBtn">로그인</button>
	        </div>
	    </div>
	</form>
</main>
<%-- footer --%>
<jsp:include page="/common/footer.jsp" />
</body>
</html>