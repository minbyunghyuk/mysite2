<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.bigdata2019.mysite.vo.GuestbookVo"%>

<!DOCTYPE html>
<%
	Long no = Long.parseLong(request.getParameter("no"));
	// String password = request.getParameter("password");
%>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="<%=request.getContextPath()%>/assets/css/board.css"
	rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<jsp:include page="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="board" class="delete-form">
				<form id=delete_form method="post"
					action="<%=request.getContextPath()%>/guestbook?a=delete&no=<%=no%>">
					<input type="hidden" name="a" value="delete"> <input
						type='hidden' name="no" value=""> <label>비밀번호</label> <input
						type="password" name="password"> <input type="submit"
						value="확인"> <a
						href="<%=request.getContextPath()%>/guestbook"> 취소</a>
				</form>
			</div>
		</div>
		<jsp:include page="/WEB-INF/views/includes/navigation.jsp" />
		<jsp:include page="/WEB-INF/views/includes/footer.jsp" />
	</div>

</body>
</html>