<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="now" value="<%= new java.util.Date()%>"/>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>My Page</title>
<%-- css 연결 --%>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/style.css'/>?v=${now}" />
<link rel="stylesheet" type="text/css" href="<c:url value='/css/myPageForm.css'/>?v=${now}" />
</head>
<body>
<%-- header (c:url 사용 금지, 경로를 직접 지정해야함)--%>
<jsp:include page="/common/header.jsp" />
<%-- main --%>
<main>
        <h2 id="register-title">My Page</h2>

        <div class="divider"></div>

        <form action="<c:url value='/mypage'/>" method="post" id="regForm">
            <fieldset>
                <div class="form-div">
                    <label for="memberId">ID</label>
                    <input type="text" name="memberId" class="form-input" id="memberId"
                    	value="<c:out value='${member.memberId}'/>" readonly>
                </div>
                <div class="form-div">
                    <label for="password">비밀번호</label>
                    <input type="password" name="password" class="form-input" id="password"
                    	placeholder="(영문+숫자+특수문자 조합 6자리이상)" required>
                </div>
                <p class="error" id="pwdError">올바른 비밀번호를 입력하세요.</p>
                <div class="form-div">
                    <label for="password2">비밀번호 확인</label>
                    <input type="password" name="password2" class="form-input" id="password2" required>
                </div>
                <p class="error" id="pwdError2">비밀번호가 일치하지 않습니다.</p>
                <div class="form-div">
                    <label for="name">이름</label>
                    <input type="text" name="name" class="form-input" id="name"
                    	value="<c:out value='${member.name}'/>" readonly>
                </div>
                <div class="form-div">
                    <label for="email">이메일</label>
                    <input type="text" name="email" class="form-input" id="email"
                    	value="<c:out value='${member.email}'/>">
                </div>
                <p class="error" id="emailError">올바른 이메일을 입력하세요.</p>
            </fieldset>
            </form>
            <div class="button-wrap">
            	<form action="<c:url value='/deleteMember'/>" method="post">
					<input type="hidden" name="memberId" value="${member.memberId}">
					<input type="submit" value="회원탈퇴" id="deleteBtn"
						onclick="return confirm('${member.memberId}님 정말 탈퇴하시겠습니까?');">
				</form>
                <button type="submit" id="submitBtn"
                	onclick="document.querySelector('#regForm').submit();">수정</button>
            </div>
        
<%-- footer --%>
<jsp:include page="/common/footer.jsp" />
</body>
</html>