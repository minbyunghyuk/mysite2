package com.bigdata2019.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bigdata2019.mysite.vo.BoardVo;

public class BoardDao {


	// 1.insert
	// 2.list all (list반환)
	// 3.find long (boardvo 반환)
	// 4.update (long 키값 )
	// 5.delete
	public Boolean delete(Long no) {
		Boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();

			// SQL 준비
			String sql = " delete" + " from board" + "  where no = ?";

			pstmt = conn.prepareStatement(sql);

			// 값 바인딩
			pstmt.setLong(1, no);

			// 쿼리 실행
			int count = pstmt.executeUpdate();
			result = (count == 1);

		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public void UpdateVoLongno(Long no, String title, String contents) {
		BoardVo result = null;
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();

			String sql = "update board " + "set title = ?,contents = ? where no = ?;";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, title);
			pstmt.setString(2, contents);
			pstmt.setLong(3, no);

			int r = pstmt.executeUpdate();

			System.out.println("변경된Row" + r);

		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 클래스 로딩 실패:" + e);
		} catch (SQLException e) {
			System.out.println("에러:" + e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public void UpdateVoHit(Long no) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();

			String sql = "update board set hit = hit+1  where no = ?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, no);

			int r = pstmt.executeUpdate();

			System.out.println("변경된Row" + r);

		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 클래스 로딩 실패:" + e);
		} catch (SQLException e) {
			System.out.println("에러:" + e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public List<BoardVo> FindStringVoList(String title) {
		List<BoardVo> result = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();

			String sql = "select * from board\r\n" + "where title = ?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, title);

			rs = pstmt.executeQuery();

			if (rs.next()) {

				BoardVo vo = new BoardVo();
				vo.setNo(rs.getLong(1));
				vo.setTitle(rs.getString(2));
				vo.setContents(rs.getString(3));
				vo.setRegDate(rs.getString(4));
				vo.setHit(rs.getInt(5));
				vo.setGroupNo(rs.getInt(6));
				vo.setOrderNo(rs.getInt(7));
				vo.setDepth(rs.getInt(8));
				vo.setUserNo(rs.getLong(9));
				vo.setUserName(rs.getString(10));
				result.add(vo);

			}

		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 클래스 로딩 실패:" + e);
		} catch (SQLException e) {
			System.out.println("에러:" + e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	public BoardVo GetVOLongno(Long no) {
		BoardVo result = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();

			String sql = "select  no ,title ,contents ,user_no , g_no , o_no ,depth , user_name from board where no = ?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, no);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				result = new BoardVo();
				result.setNo(rs.getLong(1));
				result.setTitle(rs.getString(2));
				result.setContents(rs.getString(3));
				result.setUserNo(rs.getLong(4));
				result.setGroupNo(rs.getInt(5));
				result.setOrderNo(rs.getInt(6));
				result.setDepth(rs.getInt(7));
				result.setUserName(rs.getString(8));
			}
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 클래스 로딩 실패:" + e);
		} catch (SQLException e) {
			System.out.println("에러:" + e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	public int getTotalCount() {
		int total = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();

			String sql = "select count(*) from board";
			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 클래스 로딩 실패:" + e);
		} catch (SQLException e) {
			System.out.println("에러:" + e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return  total;
	}

	public BoardVo GetVOg_no(int no) {
		BoardVo result = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();

			String sql = "select  no ,title ,contents ,user_no , g_no , o_no ,depth , user_name from board where g_no = ?"
					+ " and depth = (select max(depth) from board)";
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, no);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				result = new BoardVo();
				result.setNo(rs.getLong(1));
				result.setTitle(rs.getString(2));
				result.setContents(rs.getString(3));
				result.setUserNo(rs.getLong(4));
				result.setGroupNo(rs.getInt(5));
				result.setOrderNo(rs.getInt(6));
				result.setDepth(rs.getInt(7));
				result.setUserName(rs.getString(8));
			}
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 클래스 로딩 실패:" + e);
		} catch (SQLException e) {
			System.out.println("에러:" + e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	public BoardVo GetVo(String title) {
		BoardVo result = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();

			String sql = "select * from board\r\n" + "where title = ?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, title);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				result = new BoardVo();
				result.setNo(rs.getLong(1));
				result.setTitle(rs.getString(2));
				result.setContents(rs.getString(3));
				result.setRegDate(rs.getString(4));
				result.setHit(rs.getInt(5));
				result.setGroupNo(rs.getInt(6));
				result.setOrderNo(rs.getInt(7));
				result.setDepth(rs.getInt(8));
				result.setUserNo(rs.getLong(9));
				result.setUserName(rs.getString(10));
			}
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 클래스 로딩 실패:" + e);
		} catch (SQLException e) {
			System.out.println("에러:" + e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	public List<BoardVo> findAll() {
		List<BoardVo> result = new ArrayList<BoardVo>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();

			String sql = "SELECT * FROM board  order by g_no DESC, o_no ASC\r\n";

			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				BoardVo vo = new BoardVo();
				vo.setNo(rs.getLong(1));
				vo.setTitle(rs.getString(2));
				vo.setContents(rs.getString(3));
				vo.setRegDate(rs.getString(4));
				vo.setHit(rs.getInt(5));
				vo.setGroupNo(rs.getInt(6));
				vo.setOrderNo(rs.getInt(7));
				vo.setDepth(rs.getInt(8));
				vo.setUserNo(rs.getLong(9));
				vo.setUserName(rs.getString(10));

				result.add(vo);
			}


		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 클래스 로딩 실패:" + e);
		} catch (SQLException e) {
			System.out.println("에러:" + e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	public void insertrequest(BoardVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();

			// SQL 준비
			// 여기서일단 현재글을 가져와서

			String sql = " insert into board values (null,?,?,now(),0,?,?,?,?,?)";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setInt(3, vo.getGroupNo());
			pstmt.setInt(4, vo.getOrderNo());
			pstmt.setInt(5, vo.getDepth());
			pstmt.setLong(6, vo.getUserNo());
			pstmt.setString(7, vo.getUserName());
			// 값 바인딩 newvo.setUserName(username); //답글을 다는 유저

			// 값 바인딩

			// 쿼리 실행
			pstmt.executeUpdate();

		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public void insert(BoardVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();

			// SQL 준비
			String sql = " insert into board values (null, ?, ?, now(), 0,  (select IF(max(g_no) is null, 0, max(g_no)) from board a)+1 , 1, 0, ?,?)";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setLong(3, vo.getUserNo());
			pstmt.setString(4, vo.getUserName());
			// 값 바인딩

			// 쿼리 실행
			pstmt.executeUpdate();

		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	private Connection getConnection() throws ClassNotFoundException, SQLException {
		// 1. JDBC Driver(Mysql) 로딩
		Class.forName("com.mysql.jdbc.Driver");

		// 2. 연결하기
		String url = "jdbc:mysql://localhost:3306/webdb";
		Connection conn = DriverManager.getConnection(url, "webdb", "webdb");

		return conn;
	}

}
