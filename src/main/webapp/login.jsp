<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.*" %>


<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>관리자 로그인 화면 : workerLogin.jsp</title>
	
	<script type="text/javascript" src="script/product.js" ></script>
	<link rel="stylesheet" type="text/css" href="css/shopping.css" />
	
	<style>
		#login_error{
			color : red;
			text-align: center;
		}
	</style>
	
</head>
<body>

	<c:if test="${empty id}">
		<div id="wrap" align="center">
			<h1>관리자 로그인</h1>
			<form method="post" action="${contextPath}/login.do" name="login_form">
				<div>
					아이디 <input type="text"  placeholder="아이디" name="id" maxlength="20" value="java" required  />
		    	</div>
		   		<div>
					비밀번호 <input type="password"  placeholder="비밀번호" name="pwd" maxlength="20" value="1234" required />
		    	</div>
				<c:if test="${not empty msg}"> <!-- 세션에 기록된 msg사용  -->
					<p id="login_error">${msg}</p>
				</c:if>
				<br />
				<input type="button" value="로그인" onclick="workerLoginCheck()">
				<input type="reset" value="다시작성">
			</form>
		</div>
	</c:if>	
	<c:if test="${not empty id}">
		<jsp:forward page = "${contextPath}/productList.do"></jsp:forward>
	</c:if>	
</body>
</html>