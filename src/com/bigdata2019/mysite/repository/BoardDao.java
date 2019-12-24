package com.bigdata2019.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.bigdata2019.mysite.vo.BoardVo;



public class BoardDao {

	// 일단 하나씩 만들자
	// 1.insert
	// 2.list all
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
