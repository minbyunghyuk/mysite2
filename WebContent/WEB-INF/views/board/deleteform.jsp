<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.bigdata2019.mysite.vo.BoardVo"%>
<%@page import="com.bigdata2019.mysite.repository.BoardDao"%>
<%@page import="com.bigdata2019.mysite.vo.UserVo"%>

<!DOCTYPE html>
<%
	Long no = Long.parseLong(request.getParameter("no"));

	BoardVo vo = new BoardDao().GetVOLongno(no);
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
			<div id="board">
				<form id="delete_form" method="post"
					action="<%=request.getContextPath()%>/board?a=delete&no=<%=vo.getNo()%>">
					<input type="hidden" name="a" value="delete"> <input
						type='hidden' name="no" value=""> <label>사용자 비밀번호</label>
					<input type="password" name="password"> <input
						type="submit" value="확인"> <a
						href="<%=request.getContextPath()%>/board">취소</a>
				</form>
			</div>
		</div>
		<jsp:include page="/WEB-INF/views/includes/navigation.jsp" />
		<jsp:include page="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>