<%@page import="com.bigdata2019.mysite.web.util.WebUtil"%>
<%@page import="com.bigdata2019.mysite.vo.UserVo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="com.bigdata2019.mysite.vo.BoardVo"%>
<%@page import="com.bigdata2019.mysite.repository.BoardDao"%>
<!DOCTYPE html>
<%
	Long no = Long.parseLong(request.getParameter("no"));
	BoardVo vo = new BoardDao().GetVOLongno(no);
	//현재세션을 가지고 와서 이글이 해당사용자가 쓴거면 수정가능하게 
	HttpSession session1 = request.getSession();
	if (session == null) {
		WebUtil.redirect(request, response, request.getContextPath());
		return;
	}

	UserVo authUser = (UserVo) session.getAttribute("authUser");
	if (authUser == null) {
		WebUtil.redirect(request, response, request.getContextPath());
		return;
	}

	boolean SessionUserName = false;
	String BoardUserName = vo.getUserName();
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
			<div id="board" class="board-form">

				<table class="tbl-ex">
					<tr>
						<th colspan="2">글보기</th>
					</tr>
					<tr>
						<td class="label">제목</td>
						<td><%=vo.getTitle()%></td>

					</tr>
					<tr>
						<td class="label">내용</td>
						<td>
							<div class="view-content">
								<%=vo.getContents()%>
							</div>
						</td>
					</tr>
				</table>

				<div class="bottom">
					<a href="<%=request.getContextPath()%>/board">글목록</a>

					<%
						if (authUser!= null &&authUser.getName().equals(vo.getUserName())) {


					%>
					<a
						href="<%=request.getContextPath()%>/board?a=modifyform&no=<%=vo.getNo()%>">글수정</a>

					<%
						} else {
					%>

					<a
						href="<%=request.getContextPath()%>/board?a=requestwriteform&no=<%=vo.getNo()%>">답글달기</a>
					<%
						}
					%>

				</div>
			</div>
		</div>
		<jsp:include page="/WEB-INF/views/includes/navigation.jsp" />
		<jsp:include page="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>