<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="com.bigdata2019.mysite.vo.BoardVo"%>
<%@page import="com.bigdata2019.mysite.repository.BoardDao"%>
<!DOCTYPE html>
<%
	//list다가져와
	List<BoardVo> list = new BoardDao().findAll();

	//현재 세션 가져와서 내가쓴게아니면 답글표시하고 내가쓴거면 수정표시하게 변경
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
				<%
					String kwd = request.getParameter("kwd");

					if (kwd != null) {
						int index = 1;
						BoardVo	vo = new BoardDao().GetVo(kwd);
				%>
				
				<form id="search_form"
					action="<%=request.getContextPath()%>/board?a=find" method="post">
					<input type="text" id="kwd" name="kwd" value=""> <input
						type="submit" value="찾기">
				</form>
				
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>

					<tr>
						<td>[<%=index++%>]
						</td>
						<td><a
							href="<%=request.getContextPath()%>/board?a=view&no=<%=vo.getNo()%>"><%=vo.getTitle()%></a></td>
						<td><%=vo.getUserName()%></td>
						<td><%=vo.getHit()%></td>
						<td><%=vo.getRegDate()%></td>
						<td><a
							href="<%=request.getContextPath()%>/board?a=deleteform&no=<%=vo.getNo()%>"
							class="del">삭제</a></td>
					</tr>
				</table>
				
				
				<%
					//여기서 만약 현재글의 groupnum이 같은게있으면 가져와서 표시 
							for (BoardVo requestvo : list) {
								if (vo.getGroupNo() == requestvo.getGroupNo() && vo.getOrderNo() < requestvo.getOrderNo()) {
				%>
				<table>
					<tr>

						<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<img
							src="<%=request.getContextPath()%>/assets/images/repicture.gif">
							<a
							href="<%=request.getContextPath()%>/board?a=view&no=<%=requestvo.getNo()%>"><%=requestvo.getTitle()%></a>
						</td>
						<td><%=vo.getRegDate()%></td>
						<td><a
							href="<%=request.getContextPath()%>/board?a=deleteform&no=<%=vo.getNo()%>"
							class="del">삭제</a></td>

						<%
							}
									}
						%>

					</tr>

				</table>

				<%
				
					} else {

						int totalCount = list.size();
						int index = 1;
				%>

				<form id="search_form"
					action="<%=request.getContextPath()%>/board?a=find" method="post">
					<input type="text" id="kwd" name="kwd" value=""> <input
						type="submit" value="찾기">
				</form>


				<%
					for (BoardVo vo : list) {
				%>




				<%
					if (vo.getOrderNo() == 1) {
				%>


				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>

					<tr>
						<td>[<%=index++%>]
						</td>
						<td><a
							href="<%=request.getContextPath()%>/board?a=view&no=<%=vo.getNo()%>"><%=vo.getTitle()%></a></td>
						<td><%=vo.getUserName()%></td>
						<td><%=vo.getHit()%></td>
						<td><%=vo.getRegDate()%></td>
						<td><a
							href="<%=request.getContextPath()%>/board?a=deleteform&no=<%=vo.getNo()%>"
							class="del">삭제</a></td>
					</tr>
				</table>
				<%
					}
				%>
				<%
					//여기서 만약 현재글의 groupnum이 같은게있으면 가져와서 표시 
							for (BoardVo requestvo : list) {
								if (vo.getGroupNo() == requestvo.getGroupNo() && vo.getOrderNo() < requestvo.getOrderNo()) {
				%>
				<table>
					<tr>

						<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<img
							src="<%=request.getContextPath()%>/assets/images/repicture.gif">
							<a
							href="<%=request.getContextPath()%>/board?a=view&no=<%=requestvo.getNo()%>"><%=requestvo.getTitle()%></a>
						</td>
						<td><%=vo.getRegDate()%></td>
						<td><a
							href="<%=request.getContextPath()%>/board?a=deleteform&no=<%=vo.getNo()%>"
							class="del">삭제</a></td>

						<%
							}
									}
						%>

					</tr>

				</table>
				<%
					}
				%>
				<!-- pager 추가 -->
				<div class="pager">
					<ul>
						<li><a href="">◀</a></li>
						<li><a href="">1</a></li>
						<li class="selected">2</li>
						<li><a href="">3</a></li>
						<li>4</li>
						<li>5</li>
						<li><a href="">▶</a></li>
					</ul>
				</div>
				<!-- pager 추가 -->

				<div class="bottom">
					<a href="<%=request.getContextPath()%>/board?a=writeform"
						id="new-book">글쓰기</a>
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