<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="<%=request.getContextPath()%>/assets/css/main.css"
	rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<jsp:include page="/WEB-INF/views/includes/header.jsp" />
		<div id="wrapper">
			<div id="content">
				<div id="site-introduction">
					<img id="profile" style="width: 120px"
						src="<%=request.getContextPath()%>/assets/images/background.jpg">
					<h2>안녕하세요</h2>
					<p>
						완성은했는데 아주 좋지않은 코드로 완성됬습니다.<br> 비트교육 빅데이터반 민병혁입니다. <br>
						<br> <a href="<%=request.getContextPath()%>/guestbook">방명록</a>에
						글 남기기<br><a href="<%=request.getContextPath()%>/board">게시판</a>에
						글 남기기<br>
					</p>
				</div>
			</div>
		</div>
		<jsp:include page="/WEB-INF/views/includes/navigation.jsp" />
		<jsp:include page="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>