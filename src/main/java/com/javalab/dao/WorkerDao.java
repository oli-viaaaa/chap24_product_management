package com.javalab.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.javalab.dto.Worker;

public class WorkerDao {
	
	private Connection con;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private DataSource dataSource;	
	
	public WorkerDao() {
		System.out.println("여기는 WorkerDao 생성자");
		try {
			Context ctx = new InitialContext();
			Context envContext = (Context) ctx.lookup("java:/comp/env");
			dataSource = (DataSource) envContext.lookup("jdbc/oracle");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	

	//한 개 조회
	public Boolean selectLoginCheck(Worker model) {

		boolean flag = false;

		try {
			con = dataSource.getConnection();	//커넥션 객체 얻기
			
			StringBuffer query = new StringBuffer();
			query.append("Select id ");			
			query.append(" From worker");			
			query.append(" Where id = ? And pwd = ? ");			
			
			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, model.getId());
			pstmt.setString(2, model.getPwd());
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				flag = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return flag;
	}
	
	public void close() {
		try {
			if(rs != null) rs.close();
			if(pstmt != null) pstmt.close();
			if(con != null) con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}	
}