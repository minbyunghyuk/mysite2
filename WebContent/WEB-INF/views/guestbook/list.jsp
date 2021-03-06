<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="com.bigdata2019.mysite.vo.GuestbookVo"%>
<%@page import="com.bigdata2019.mysite.repository.GuestbookDao"%>
<%@page import="com.bigdata2019.mysite.web.util.WebUtil"%>
<%@page import="com.bigdata2019.mysite.vo.UserVo"%>

<!DOCTYPE html>
<%
	List<GuestbookVo> list = new GuestbookDao().findAll();

	HttpSession session1 = request.getSession();
	if (session == null) {
		//로그인을 안해도 일단 게시판표시는해야쥬?	
		//WebUtil.redirect(request, response, request.getContextPath());
	}

	UserVo authUser = (UserVo) session.getAttribute("authUser");
	if (authUser == null) {
		//로그인을 안해도 일단 게시판표시는해야쥬?	
		//WebUtil.redirect(request, response, request.getContextPath());

	}
%>



<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="<%=request.getContextPath()%>/assets/css/guestbook.css"
	rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<jsp:include page="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="guestbook">
				<form action="<%=request.getContextPath()%>/guestbook?a=insert"
					method="post">
					<input type="hidden" name="a" value="insert">
					<table>
						<tr>
							<td>이름</td>
							<td><input type="text" name="name"></td>
							<td>비밀번호</td>
							<td><input type="password" name="password"></td>
						</tr>
						<tr>
							<td colspan=4><textarea name="content" id="content"></textarea></td>
						</tr>
						<tr>
							<td colspan=4 align=right><input type="submit" VALUE=" 확인 "></td>
						</tr>
					</table>

				</form>
				<ul>
					<%
						for (GuestbookVo vo : list) {
					%>
					<li>
						<table>
							<tr>
								<td><%=vo.getNo()%></td>
								<td><%=vo.getName()%></td>
								<td><%=vo.getRegDate()%></td>

								<%
									if (authUser != null) {
								%>
								<td><a
									href="<%=request.getContextPath()%>/guestbook?a=deleteform&no=<%=vo.getNo()%>">삭제</a></td>
								<%
									}
								%>
							</tr>
							<tr>
								<td colspan=4><%=vo.getContents()%></td>
							</tr>
						</table> <br>
					</li>
					<%
						}
					%>
				</ul>
			</div>
		</div>
		<jsp:include page="/WEB-INF/views/includes/navigation.jsp" />
		<jsp:include page="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>