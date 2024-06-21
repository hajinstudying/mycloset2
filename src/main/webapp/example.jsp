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
</head>
<body>
<%-- header (c:url 사용 금지, 경로를 직접 지정해야함)--%>
<jsp:include page="/common/header.jsp" />
<%-- main --%>
<main>

	<%-- 이곳에 폼을 넣으시면 됩니다.--%>

</main>
<%-- footer --%>
<jsp:include page="/common/footer.jsp" />
</body>
</html>