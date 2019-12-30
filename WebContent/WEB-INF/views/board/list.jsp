<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.bigdata2019.mysite.web.util.WebUtil"%>
<%@page import="com.bigdata2019.mysite.vo.UserVo"%>
<%@page import="java.util.List"%>
<%@page import=" java.util.ArrayList"%>
<%@page import="com.bigdata2019.mysite.vo.BoardVo"%>
<%@page import="com.bigdata2019.mysite.repository.BoardDao"%>
<%@page import=" java.util.HashSet"%>
<!DOCTYPE html>
<%
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

	String a = request.getParameter("page");
	int page1 = 1;
	if (a != null)
		page1 = Integer.parseInt(request.getParameter("page"));

	//System.out.println(page1);
	List<BoardVo> requestlist = new BoardDao().findAll();
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
			<form id="search_form"
				action="<%=request.getContextPath()%>/board?a=find" method="post">
				<input type="text" id="kwd" name="kwd" value=""> <input
					type="submit" value="찾기">
			</form>
			<div id="board">
				<%
					//코드로때워야지뭐
					//일단어떤리스트가 됬던 5개로 짤러 그다음 화면 출력부터해보자.
					//리스트를 계속 new를 해줘야된다니 노답인데

					String kwd = request.getParameter("kwd");
					List<BoardVo> list = new ArrayList<BoardVo>();

					int lastpage = 0;
					int startpage = 0;
					int listcount = 1;
					int index = 1;
					if (kwd == null) {
						list = new BoardDao().findAll();
					}

					else {
						list = new BoardDao().FindStringVoList(kwd);
					}

					for (BoardVo vo : list) {

						if (vo.getDepth() == 0) {
							vo.pageno = listcount;
							listcount++;
						}

						//System.out.println(vo.pageno);
						if (page1 == 1) {
							startpage = 1;
							lastpage = 11;
						}
						if (page1 == 2) {
							startpage = 11;
							lastpage = 21;
						}
						if (page1 == 3) {
							startpage = 21;
							lastpage = 31;
						}
						if (page1 == 4) {
							startpage = 31;
							lastpage = 41;
						}
						if (page1 == 5) {
							startpage = 41;
							lastpage = 51;
						}

						if (vo.getDepth() == 0 && vo.pageno < lastpage && vo.pageno >= startpage) {

							//System.out.println(startpage);
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
						<td>[<%=vo.pageno%>]
						</td>
						<td><a
							href="<%=request.getContextPath()%>/board?a=view&no=<%=vo.getNo()%>"><%=vo.getTitle()%></a></td>
						<td><%=vo.getUserName()%></td>
						<td><%=vo.getHit()%></td>
						<td><%=vo.getRegDate()%></td>
						<%
							if (authUser != null && authUser.getName().equals(vo.getUserName())) {
						%>

						<td><a
							href="<%=request.getContextPath()%>/board?a=deleteform&no=<%=vo.getNo()%>"
							class="del">삭제</a></td>
						<%
							}
						%>
					</tr>
				</table>

				<%
					//여기서 만약 현재글의 groupnum이 같은게있으면 가져와서 표시 
							for (BoardVo requestvo : requestlist) {

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
						<td><%=requestvo.getRegDate()%></td>

						<%
							if (authUser != null) {
						%>



						<%
							if (authUser != null && authUser.getName().equals(vo.getUserName())
														|| authUser.getName().equals(requestvo.getUserName())) {
						%>
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
							}
						}
					}
				%>

				<!-- pager 추가 -->
				<div class="pager" id="page">

					<ul>

						<%
							if (page1 == 1) {
						%>
						<li><a
							href="<%=request.getContextPath()%>/board?a=list&page=1">◀</a></li>
						<%
							} else {

								page1 = page1 - 1;
						%>

						<li><a
							href="<%=request.getContextPath()%>/board?a=list&page=<%=page1%>">◀</a></li>

						<%
							}
						%>
						
						
						
						
						
						<li><a
							href="<%=request.getContextPath()%>/board?a=list&page=1">1</a></li>
						<%
							if (listcount > 11) {
						%>
						<li><a
							href="<%=request.getContextPath()%>/board?a=list&page=2">2</a></li>
						<%
							}
						%>
						<%
							if (listcount > 21) {
						%>

						<li><a
							href="<%=request.getContextPath()%>/board?a=list&page=3">3</a></li>
						<%
							}
						%>
						<%
							if (listcount > 31) {
						%>
						<li><a
							href="<%=request.getContextPath()%>/board?a=list&page=4">4</a></li>
						<%
							}
						%>
						<%
							if (listcount > 41) {
								page1 = page1 + 1;
								System.out.println(page1);
						%>
						<li><a
							href="<%=request.getContextPath()%>/board?a=list&page=5">5</a></li>
						<%
							if (page1 == 5) {
						%>

						<li><a
							href="<%=request.getContextPath()%>/board?a=list&page=5">▶</a></li>
						<%
							} else {

									page1 = page1 + 1;
						%>
						<li><a
							href="<%=request.getContextPath()%>/board?a=list&page=<%=page1%>">▶</a></li>

						<%
							}
						%>


						<%
							}
							page1 = page1 + 1;
						%>
						<li><a
							href="<%=request.getContextPath()%>/board?a=list&page=<%=page1%>">▶</a></li>

					</ul>
				</div>
				<!-- pager 추가 -->

				<div class="bottom">
					<a href="<%=request.getContextPath()%>/board?a=writeform"
						id="new-book">글쓰기</a>

				</div>
			</div>
		</div>
		<jsp:include page="/WEB-INF/views/includes/navigation.jsp" />
		<jsp:include page="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>