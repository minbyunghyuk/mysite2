package com.bigdata2019.mysite.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bigdata2019.mysite.repository.GuestbookDao;
import com.bigdata2019.mysite.vo.GuestbookVo;
import com.bigdata2019.mysite.web.util.WebUtil;

public class GuestbookController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("a");

		if ("insert".equals(action)) {

			String name = request.getParameter("name");
			String password = request.getParameter("password");
			String content = request.getParameter("content");

			if (name.equals("") || password.equals("")) // 이름하고 비밀번호안쓰면 못쓰게
			{
				WebUtil.forward(request, response, "/WEB-INF/views/guestbook/list.jsp");
				return;
			}
			GuestbookVo vo = new GuestbookVo();
			vo.setName(name);
			vo.setPassword(password);
			vo.setContents(content);

			new GuestbookDao().insert(vo);

			WebUtil.forward(request, response, "/WEB-INF/views/guestbook/list.jsp");
		}

		else if ("deleteform".equals(action)) {

			WebUtil.forward(request, response, "/WEB-INF/views/guestbook/deleteform.jsp");
		}

		else if ("delete".equals(action)) {
			Long no = Long.parseLong(request.getParameter("no"));
			String password = request.getParameter("password");
			new GuestbookDao().delete(no, password);
			WebUtil.forward(request, response, "/WEB-INF/views/guestbook/list.jsp");

		} else {
			WebUtil.forward(request, response, "/WEB-INF/views/guestbook/list.jsp");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
