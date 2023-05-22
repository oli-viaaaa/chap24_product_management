<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>상품 수정 : productUpdate.jsp</title>

	<link rel="stylesheet" type="text/css" href="css/shopping.css" />

	<script type="text/javascript" src="script/product.js" ></script>

</head>
<body>
<body>
	<div id="wrap" align="center">
		<h1>상품 수정 - 관리자 페이지</h1>
		<form method="post" enctype="multipart/form-data" name="frm">
			<input type="hidden" name="code" value="${product.code }"/>
			<input type="hidden" name="nonMakeImg" value="${product.image }"/>
			<table>
				<tr>
					<td>
					<c:choose>
						<c:when test="${product.image eq 'noImage.jpg'}">
							<img src="<c:out value="images/noImage.jpg" />">
						</c:when>
						<c:otherwise>
							<img src="<c:out value="upload/${product.image}" />">
						</c:otherwise>
					</c:choose>
					</td>
					<td>
						<table>
							<tr>
								<th>상 품 명 </th>
								<td><input type="text" name="name" size="80" value="${product.name}" required /></td>
							</tr>
							<tr>
								<th align="center">원가</th>
								<td><input type="text" name="cost_price" size="80" 
									value="<fmt:formatNumber value="${product.cost_price }" pattern="#,###"/>" required />
								</td>
							</tr>
							<tr>
								<th align="center">정가</th>
								<td><input type="text" name="list_price" size="80" 
									value="<fmt:formatNumber value="${product.list_price }" pattern="#,###"/>" required />
								</td>
							</tr>
							<tr>
								<th align="center">사진</th>
								<td><input type="file" name="picture"><br>
						             (주의사항 : 이미지를 변경하고자 할 때만 선택하시오.)
									<c:out value="${product.image}" />
								</td>
							</tr>							
							<tr>
								<th>설 명</th>
								<td><textarea cols="80" rows="10" name="content" ><c:out value="${product.content}" /></textarea>
								</td>
							</tr>
							<tr>
								<th>사용 유무</th>
								<td colspan="2">
									<select id="useyn" title="사용유무" >
									    <option value="1" <c:if test="${product.useyn eq '1'}">selected</c:if>>사용</option>
									    <option value="0" <c:if test="${product.useyn eq '0'}">selected</c:if>>미사용</option>
									</select>
								</td>
							</tr>
							<tr>
								<th>인기상품 유무</th>
								<td colspan="2">
									<select id="useyn" title="사용유무" >
									    <option value="1" <c:if test="${product.bestyn eq '1'}">selected</c:if>>사용</option>
									    <option value="0" <c:if test="${product.bestyn eq '0'}">selected</c:if>>미사용</option>
									</select>
								</td>
							</tr>							
						</table>
					</td>
				</tr>
			</table>
			<br />
			<input type="submit" value="저장" onclick="return productCheck()" >
			<input type="reset" value="다시작성">
			<input type="button" value="목록" onclick="location.href='productList.do'">
		</form>
	</div>
</body>
</html>