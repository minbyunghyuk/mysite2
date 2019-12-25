package com.bigdata2019.mysite.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bigdata2019.mysite.repository.BoardDao;
import com.bigdata2019.mysite.repository.UserDao;
import com.bigdata2019.mysite.vo.BoardVo;
import com.bigdata2019.mysite.vo.UserVo;
import com.bigdata2019.mysite.web.util.WebUtil;

public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String action = request.getParameter("a");

		if ("writeform".equals(action))
		{
			WebUtil.forward(request, response, "/WEB-INF/views/board/write.jsp");
		}

		else if("write".equals(action))
		{
			HttpSession session = request.getSession();
			if(session == null) {
				WebUtil.redirect(request, response, request.getContextPath());
				return;
			}
			
			UserVo authUser = (UserVo)session.getAttribute("authUser");
			if(authUser == null) {
				WebUtil.redirect(request, response, request.getContextPath());
				return;
			}
			String title = request.getParameter("title");
			String contents = request.getParameter("contents");
		    Long userno = authUser.getNo();
			String username =authUser.getName();
			
			BoardVo vo = new BoardVo();
			vo.setTitle(title);
			vo.setContents(contents);
			vo.setUserNo(userno);
			vo.setUserName(username);
			
			new BoardDao().insert(vo); //min보여줄려는 리스트를 넣어주고 
				
			vo = new BoardDao().GetVo(vo.getTitle());
			WebUtil.forward(request, response, "/WEB-INF/views/board/list.jsp");
		}
		else if("view".equals(action))
		{
			//min 여기서 board내용을 view뿌려줘야되나? 안에서 처리할까? 
			WebUtil.forward(request, response, "/WEB-INF/views/board/view.jsp");
		}
		else if("modifyform".equals(action))
		{
			  //새로운창을 보여줘야될때는 기능명뒤에 Form으로 변경 기능실행은 기능명으로 함
			WebUtil.forward(request, response, "/WEB-INF/views/board/modify.jsp");
		}
		else if("modify".equals(action))
		{
			  //업데이트를여기서해야되나? 
			
			
			String title = request.getParameter("title");
			String contents = request.getParameter("content");
			Long no = Long.parseLong(request.getParameter("no"));
			
			new BoardDao().UpdateVoLongno(no, title, contents);
			
			
			WebUtil.forward(request, response, "/WEB-INF/views/board/view.jsp");
		}
		else if("deleteform".equals(action))
		{
			WebUtil.forward(request, response, "/WEB-INF/views/board/deleteform.jsp");
		}
		else if("delete".equals(action))
		{
			String password = request.getParameter("password");
			Long no = Long.parseLong(request.getParameter("no"));
			
			System.out.println(password);
			
			Boolean result = new UserDao().findpassword(password);
			if(result == true) {
				
				Boolean resultOK = new BoardDao().delete(no);
				if(resultOK == true)
				{
					WebUtil.forward(request, response, "/WEB-INF/views/board/list.jsp");
				}
				
			}
			
		}
		else
		{
		 WebUtil.forward(request, response, "/WEB-INF/views/board/list.jsp");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
