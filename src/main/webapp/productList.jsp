<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page import="java.util.*" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"  /> 


<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>상품 목록 화면 : productList.jsp</title>
	<!-- 외장 CSS를 갖고옴 -->
	<link rel="stylesheet" type="text/css" href="css/shopping.css" />
	<!-- 외장 javascript를 갖고옴 -->
	<script type="text/javascript" src="script/product.js" ></script>
	
</head>
<body>
	<c:if test="${not empty sessionScope.id}">
		<div id="wrap" align="center">
			<h1>상품 리스트 - 관리자 페이지</h1>
			
			<table class="list">
				<tr>
					<td align="center" colspan="9">
						<form id="regform" action="${contextPath}/productList.do" method="get">
							<input type="hidden" id="pageNum" value="${pageNum }"/>
							<p>
								상품명 : 
								<input type="text" name="searchText" id="searchText" />
								<input type="submit" value="검색" />
								<input type="button" onclick="javascript:location.href='${contextPath}/productList.do'" value="전체보기" />
								<c:if test="${not empty id}">
									<input type="button" onclick="javascript:location.href='${contextPath}/productInsert.do'" value="상품등록" />
								</c:if>	
							</p>
						</form>				
					</td>
				</tr>
				<tr>
					<td colspan="9">
						<div style="text-align: right;">
							<c:if test="${not empty id}" >
								<strong>${id} 관리자님</strong>
								<a href="${contextPath}/logout.do"><strong>로그아웃</strong></a>
							</c:if>
							<c:if test="${empty id}" >
								<a href="${contextPath}/worderLogin"><strong>로그인</strong></a>
							</c:if>
						</div>				
					
					</td>
				</tr>
				<tr>
					<th>번호</th>
					<th>상품명</th>
					<th>원가</th>
					<th>정가</th>
					<th>판매마진</th>
					<th>사용유무</th>
					<th>등록일</th>
					<th>수정</th>
					<th>삭제</th>
				</tr>
	
				<c:set var="num" value="${totalCount - ((pageNum - 1) * listCount) }" ></c:set>
				<c:forEach var="product" items="${productList}">
					<tr class="record">
						<td ><c:out value="${num}" /></td>
						<td><a href="${contextPath}/productView.do?code=${product.code }">${product.name }</a> </td>
						<td><fmt:formatNumber value="${product.cost_price }" pattern="#,###"/> 원</td>
						<td><fmt:formatNumber value="${product.list_price }" pattern="#,###"/> 원</td>
						<td><fmt:formatNumber value="${product.sales_margin }" pattern="#,###"/> 원</td>
						<c:set var="useyn" value="${(product.useyn == '1') ? '사용' : '미사용'}" />
						<td><c:out value="${useyn}" /> </td>					
						<td><c:out value="${product.regdate}" /> </td>					
						<td>
							<a href="${contextPath}/productModify.do?code=${product.code }">상품수정</a>
						</td>
						<td>
							<a href="${contextPath}/productView.do?code=${product.code }">상품삭제</a>
						</td>
					</tr>
					<c:set var="num" value="${num-1 }"></c:set>
				</c:forEach>
				<tr>
					<td align="center" colspan="9">${page_navigator }</td>
				</tr>
				
			</table>
		</div>
	</c:if>
	<c:if test="${empty id}">
		<jsp:forward page = "productList.do"></jsp:forward>
	</c:if>		
	
	<div id="productTable">
	</div>
	
</html>
