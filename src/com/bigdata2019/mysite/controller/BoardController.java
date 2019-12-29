package com.bigdata2019.mysite.controller;

import java.io.IOException;
import java.util.List;

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

		boolean sessionresult = false;

		request.setCharacterEncoding("UTF-8");

		String action = request.getParameter("a");

		if ("writeform".equals(action)) {

			sessionresult = Checksession(request, response);

			if (sessionresult)
				WebUtil.forward(request, response, "/WEB-INF/views/board/write.jsp");
			else
				WebUtil.forward(request, response, "/WEB-INF/views/user/loginform.jsp");

		}

		else if ("write".equals(action)) {

			sessionresult = Checksession(request, response);

			if (sessionresult) {

				HttpSession session = request.getSession();
				UserVo authUser = (UserVo) session.getAttribute("authUser");
				String title = request.getParameter("title");
				String contents = request.getParameter("contents");

				Long userno = authUser.getNo();
				String username = authUser.getName();

				if (title == "" || contents == "") {
					// 내용없이 등록하면 다시하게 냥 창만 띄워놈
					WebUtil.forward(request, response, "/WEB-INF/views/board/write.jsp");
				}

				else {

					BoardVo vo = new BoardVo();
					vo.setTitle(title);
					vo.setContents(contents);
					vo.setUserNo(userno);
					vo.setUserName(username);

					new BoardDao().insert(vo); // min보여줄려는 리스트를 넣어주고

					vo = new BoardDao().GetVo(vo.getTitle());
					WebUtil.forward(request, response, "/WEB-INF/views/board/list.jsp");
				}
			} else
				WebUtil.forward(request, response, "/WEB-INF/views/board/view.jsp");

		} else if ("view".equals(action)) {
			// min 여기서 board내용을 view뿌려줘야되나? 안에서 처리할까?
			Long no = Long.parseLong(request.getParameter("no"));
			sessionresult = Checksession(request, response);
			if (sessionresult) {
				HttpSession session = request.getSession();
				UserVo authUser = (UserVo) session.getAttribute("authUser");

				BoardVo vo = new BoardDao().GetVOLongno(no);

				if (authUser.getName().equals(vo.getUserName())) {

					WebUtil.forward(request, response, "/WEB-INF/views/board/view.jsp");
					return;
				}

			} else
				new BoardDao().UpdateVoHit(no);
			WebUtil.forward(request, response, "/WEB-INF/views/board/view.jsp");

		} else if ("modifyform".equals(action)) {

			sessionresult = Checksession(request, response);

			if (sessionresult) {
				// 새로운창을 보여줘야될때는 기능명뒤에 Form으로 변경 기능실행은 기능명으로 함
				WebUtil.forward(request, response, "/WEB-INF/views/board/modify.jsp");
			} else
				WebUtil.forward(request, response, "/WEB-INF/views/board/view.jsp");

		} else if ("modify".equals(action)) {
			// 업데이트를여기서해야되나?
			// 이거실행하면 이미 세션은 확인되있으니까 확인할필요가없나?
			String title = request.getParameter("title");
			String contents = request.getParameter("content");
			Long no = Long.parseLong(request.getParameter("no"));

			new BoardDao().UpdateVoLongno(no, title, contents);

			WebUtil.forward(request, response, "/WEB-INF/views/board/view.jsp");

		} else if ("deleteform".equals(action)) {

			sessionresult = Checksession(request, response);

			if (sessionresult)
				WebUtil.forward(request, response, "/WEB-INF/views/board/deleteform.jsp");
			else
				WebUtil.forward(request, response, "/WEB-INF/views/board/view.jsp");

		} else if ("find".equals(action)) {

			WebUtil.forward(request, response, "/WEB-INF/views/board/list.jsp");

		}

		else if ("delete".equals(action)) {
			String password = request.getParameter("password");
			Long no = Long.parseLong(request.getParameter("no"));

			System.out.println(password);

			Boolean result = new UserDao().findpassword(password);
			if (result == true) {

				List<BoardVo> listvo = new BoardDao().findAll(); // g_no랑 비교할꺼
				BoardVo mainvo = new BoardDao().GetVOLongno(no); // 그냥 그룹넘버가 같은걸 다지워야지뭐

				if (mainvo.getDepth() == 0) {

					for (BoardVo vo : listvo) {
						if (vo.getGroupNo() == mainvo.getGroupNo()) {

							new BoardDao().delete(vo.getNo());
						}
					}

					new BoardDao().delete(mainvo.getNo());
				} else {

					new BoardDao().delete(mainvo.getNo());

				}

				WebUtil.forward(request, response, "/WEB-INF/views/board/list.jsp");

			} else {
				WebUtil.forward(request, response, "/WEB-INF/views/board/deleteform.jsp");
			}

		} else if ("requestwrite".equals(action)) {

			sessionresult = Checksession(request, response);
			if (sessionresult) {

				HttpSession session = request.getSession();
				UserVo authUser = (UserVo) session.getAttribute("authUser");
				String Requesttitle = request.getParameter("title");
				int g_no = Integer.parseInt(request.getParameter("g_no"));
				String Requestcontents = request.getParameter("contents");

				Long userno = authUser.getNo(); // 다른사용자일수도있으니 가져오자
				String username = authUser.getName();

				BoardVo oldvo = new BoardDao().GetVOg_no(g_no); // 원래글
				System.out.println(g_no);
				int Groupno = oldvo.getGroupNo();
				int orderno = oldvo.getOrderNo() + 1;
				int depth = oldvo.getDepth() + 1;
				BoardVo newvo = new BoardVo(); // 답글
				newvo.setTitle(Requesttitle);
				newvo.setContents(Requestcontents);
				newvo.setGroupNo(Groupno);
				newvo.setOrderNo(orderno);
				newvo.setDepth(depth);
				newvo.setUserNo(userno); // 답글을 다는 유저
				newvo.setUserName(username); // 답글을 다는 유저

				new BoardDao().insertrequest(newvo);

				WebUtil.forward(request, response, "/WEB-INF/views/board/list.jsp");
			}
			WebUtil.forward(request, response, "/WEB-INF/views/user/loginform.jsp");

		} else if ("requestwriteform".equals(action)) {

			WebUtil.forward(request, response, "/WEB-INF/views/board/requestwriteform.jsp");

		} else {
			WebUtil.forward(request, response, "/WEB-INF/views/board/list.jsp");

		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	protected boolean Checksession(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		if (session == null) {
			return false;
		}

		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if (authUser == null) {
			return false;
		}

		return true;
	}

}
