<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="now" value="<%= new java.util.Date()%>"/>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Join Member</title>
<%-- css 연결 --%>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/style.css'/>?v=${now}" />
<link rel="stylesheet" type="text/css" href="<c:url value='/css/memberForm.css'/>?v=${now}" />
</head>
<body>
<%-- header (c:url 사용 금지, 경로를 직접 지정해야함)--%>
<jsp:include page="/common/header.jsp" />
<%-- main --%>
<main>
        <h2 id="register-title">JOIN MEMBER</h2>

        <div class="divider"></div>

        <form action="<c:url value='/member'/>" method="post" id="regForm">
            <fieldset>
                <div class="form-div">
                    <label for="memberId">ID</label>
                    <input type="text" name="memberId" class="form-input" id="memberId"
                    	placeholder="영문자로 시작하는 영문자 또는 숫자 6~20자" required>
                </div>
                <p class="error" id="idError">아이디는 영문자로 시작하는 영문자 또는 숫자 6~20자로 만들어주세요.</p>
                <div class="form-div">
                    <label for="password">비밀번호</label>
                    <input type="password" name="password" class="form-input" id="password"
                        placeholder="영문+숫자+특수문자 조합 6자리이상" required>
                </div>
                <p class="error" id="pwdError">비밀번호는 영문, 숫자, 특수문자로 6자리 이상으로 만들어주세요.</p>
                <div class="form-div">
                    <label for="password2">비밀번호 확인</label>
                    <input type="password" name="password2" class="form-input" id="password2" required>
                </div>
                <p class="error" id="pwdError2">비밀번호가 일치하지 않습니다.</p>
                <div class="form-div">
                    <label for="name">이름</label>
                    <input type="text" name="name" class="form-input" id="name" placeholder="한글 2~5자리" required>
                </div>
                <p class="error" id="nameError">올바른 이름을 입력하세요.</p>
                <div class="form-div">
                    <label for="email">이메일</label>
                    <input type="text" name="email" class="form-input" id="email">
                </div>
                <p class="error" id="emailError">올바른 이메일 형식을 입력하세요.</p>
            </fieldset>
            <div class="button-wrap">
                <button type="button" id="cancleBtn">취소</button>
                <button type="submit" id="submitBtn">회원가입</button>
            </div>
        </form>
    </main>
<%-- footer --%>
<jsp:include page="/common/footer.jsp" />
<script type="text/javascript" src="<c:url value='/script/memberForm.js'/>"></script>
</body>
</html>