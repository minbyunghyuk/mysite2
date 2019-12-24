package com.bigdata2019.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bigdata2019.mysite.vo.BoardVo;
import com.bigdata2019.mysite.vo.GuestbookVo;



public class BoardDao {

	// 일단 하나씩 만들자
	// 1.insert
	// 2.list all
	public List<BoardVo> findAll(){
		List<BoardVo> result = new ArrayList<BoardVo>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			
			String sql = "select * from board";
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				BoardVo vo = new BoardVo();
				vo.setNo(rs.getLong(1));
				vo.setTitle(rs.getString(2));
				vo.setContents(rs.getString(3));
				vo.setHit(rs.getInt(4));
				vo.setGroupNo(rs.getInt(5));
				vo.setOrderNo(rs.getInt(6));
				vo.setDepth(rs.getInt(7));
				vo.setRegDate(rs.getString(8));
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
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	
		return result;
	}
	
	public void insert(BoardVo vo) {
		Boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();

			// SQL 준비
			String sql = "insert into  board \r\n" + 
					" values (null, ?, ?, 0,0,0,0,now(),?,?) ";

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
