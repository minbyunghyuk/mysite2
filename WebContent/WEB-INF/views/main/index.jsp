<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="<%=request.getContextPath() %>/assets/css/main.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<jsp:include page="/WEB-INF/views/includes/header.jsp"/>
		<div id="wrapper">
			<div id="content">
				<div id="site-introduction">
					<img id="profile" style="width:120px" src="<%=request.getContextPath() %>/assets/images/kickscar.jpg">
					<h2>이게뭐라고 밤까지 이짓을하고있냐</h2>
					<p>
						깃허브날리지말고 커밋좀제대로하자.<br>
					    안그래도쓰레기인데 언제인간되니 쓰레기야 <br><br>
						<a href="<%=request.getContextPath() %>/guestbook">방명록</a>에 글 남기기<br>
					</p>
				</div>
			</div>
		</div>
		<jsp:include page="/WEB-INF/views/includes/navigation.jsp"/>
		<jsp:include page="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>