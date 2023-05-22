package com.javalab.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.javalab.dto.Product;

/**
 * [Dao 클래스]
 *  - 각 메소드 종료시점에 close()로 커넥션 반환해줄것.
 *    단, 하나의 세션으로 연결된 메소드가 있을 경우에는 예외.
 *    연결된 메소드에서 close()하면 호출한 곳에서 커넥션 널!!
 */
// 프로젝트할때 참고
public class ProductDao {
	
	private Connection con;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private String sql;
	
	private DataSource dataSource;	

	public ProductDao() {
		System.out.println("여기는 ProductDao 생성자");
		try {
			Context ctx = new InitialContext();
			Context envContext = (Context) ctx.lookup("java:/comp/env");
			dataSource = (DataSource) envContext.lookup("jdbc/oracle");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	//전체 상품 조회(
	public List<Product> getProductList(Product model){
		List<Product> products = new ArrayList<Product>();
		
		String searchText = ""; 
		sql = "";
				
		if(model.getSearchText() != null) {
			searchText = model.getSearchText();
		}
				
		int start = 0;
		int end = 0;
		
		// 시작 게시물 번호
		start = (Integer.parseInt(model.getPageNum()) - 1) * model.getListCount() + 1;
		// 끝 게시물 번호
		end = start + model.getListCount() - 1;
		
		System.out.println("시작게시물번호 : " + start + " / 끝 게시물번호 : " + end);
		
		sql = "Select c.seq, c.code, c.name, c.kind, c.cost_price, c.list_price, c.sales_margin, c.image, c.useyn, c.regdate";
		sql += " From( ";
		sql += "	Select rownum as seq, b.code, b.name, b.kind, b.cost_price, b.list_price, b.sales_margin, b.image, b.useyn, b.regdate";
		sql += "    From ( ";
		sql += "		Select rownum, a.code, a.name, a.kind, a.cost_price, a.list_price, a.sales_margin, a.image, a.useyn, to_char(regdate, 'yyyy-MM-dd') as regdate";
		sql += "    	From product a ";
		if(!searchText.equals("")) {
			sql += " 	Where a.name like ?";
		}
		sql += "		Order By a.code Desc ";
		sql += "    ) B";   
		sql += " ) C";
		sql += " Where c.seq Between ? And ?";
		
		System.out.println("searchText:" + searchText);
		System.out.println("sql : " + sql );

		try {
			con = dataSource.getConnection();	//커넥션 객체 얻기
			pstmt = con.prepareStatement(sql);

			if(!searchText.equals("")) {	
					pstmt.setString(1, "%" + searchText + "%");		//상품명 
					pstmt.setInt(2, start);							//start num
					pstmt.setInt(3, end);							//end num
			}else {	//상품 검색어가 없으면 
					pstmt.setInt(1, start);							//start num
					pstmt.setInt(2, end);							//end num
			}
			
			rs = pstmt.executeQuery();			
			while(rs.next()) {
				Product product = new Product();
				product.setCode(rs.getInt("code"));
				product.setName(rs.getString("name"));
				product.setKind(rs.getInt("kind"));				
				product.setCost_price(rs.getInt("cost_price"));
				product.setList_price(rs.getInt("list_price"));
				product.setSales_margin(rs.getInt("sales_margin"));
				product.setImage(rs.getString("image"));
				product.setUseyn(rs.getString("useyn"));
				product.setRegdate(rs.getDate("regdate"));
				products.add(product);
				
				System.out.println(product.toString());
			}
			close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return products;
	}
	
	//테이블의 전체 레코드 수 조회
	public int selectCount(Product product) {
		int totalCount = 0;
		sql = "";
		
		String searchText = product.getSearchText();
		
		sql = "Select Count(code) as totalCount";
		sql += " From product";
		if(!searchText.equals("")) {
			sql += " Where name Like ? ";
		}

		try {
			con = dataSource.getConnection();	//커넥션 객체 얻기
			pstmt = con.prepareStatement(sql);
			if(!searchText.equals("")) {
					pstmt.setString(1, "%" + searchText + "%");
			}
			rs = pstmt.executeQuery();
			if(rs.next()) {
				totalCount = rs.getInt("totalCount");
			}
			close();
		}catch(SQLException e) {
			e.printStackTrace();
		} 
		return totalCount;
	}
	
	
	//한 개 조회
	public Product selectProductByCode(int code) {
		
		sql = "";
		sql = "Select code, name, kind, cost_price, list_price, sales_margin, content, image, useyn, bestyn, to_char(regdate, 'yyyy-MM-dd') as regdate";
		sql += " From product ";
		sql += " Where code = ?";
		
		Product product = null;

		try {
			con = dataSource.getConnection();	//커넥션 객체 얻기
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, code);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				product = new Product();
				product.setCode(rs.getInt("code"));
				product.setName(rs.getString("name"));
				product.setKind(rs.getInt("kind"));				
				product.setCost_price(rs.getInt("cost_price"));
				product.setList_price(rs.getInt("list_price"));
				product.setSales_margin(rs.getInt("sales_margin"));
				product.setContent(rs.getString("content"));
				product.setImage(rs.getString("image"));
				product.setUseyn(rs.getString("useyn"));
				product.setBestyn(rs.getString("bestyn"));
				product.setRegdate(rs.getDate("regdate"));
			}
			close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return product;
	}
	
	//게시물 등록
	public void insertProduct(Product product) {
		
		sql = "";
		int r = 0;
		int sales_margin = 0;

		if(product.getList_price() != null && product.getCost_price() != null) {
			sales_margin = product.getList_price() - product.getCost_price();
		}
		// useyn(사용여부) 플래그는 무조건 1로 세팅
		sql = "Insert Into product(code, name, kind, cost_price, list_price, sales_margin, content, image, useyn, bestyn, regdate)";
		sql += " Values(product_code_seq.nextval, ?, ?, ?, ?, ?, ?, ?, 1, ?, sysdate)";

		try {
			con = dataSource.getConnection();	//커넥션 객체 얻기
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, product.getName());
			pstmt.setInt(2, product.getKind());
			pstmt.setInt(3, product.getCost_price());
			pstmt.setInt(4, product.getList_price());
			pstmt.setInt(5, sales_margin);
			pstmt.setString(6, product.getContent());
			pstmt.setString(7, product.getImage()==null? "noImage.jpg" : product.getImage());
			pstmt.setString(8, product.getBestyn());

			
			r = pstmt.executeUpdate();
			if(r == 1) {
				System.out.println("데이터 저장 성공!");
			}
			
			close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	
	//상품  업데이트
	public void updateProduct(Product product) {
		
		System.out.println("productDao updateProduct : " + product.toString());
		
		sql = "";
		int r = 0;
		int sales_margin = 0;

		if(product.getList_price() != null && product.getCost_price() != null) {
			sales_margin = product.getList_price() - product.getCost_price();
		}

		sql = "Update product Set ";
		sql += " 	name = ?, ";
		sql += " 	cost_price = ?, ";
		sql += " 	list_price = ?, ";
		sql += " 	sales_margin = ?, ";
		sql += " 	content = ?, ";
		sql += " 	image = ?, ";
		sql += " 	useyn = ?, ";
		sql += " 	bestyn = ? ";
		sql += " Where code  = ? ";
		
		System.out.println("Update sql : " + sql);

		try {
			con = dataSource.getConnection();	//커넥션 객체 얻기
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, product.getName());
			pstmt.setInt(2, product.getCost_price());
			pstmt.setInt(3, product.getList_price());
			pstmt.setInt(4, sales_margin);
			pstmt.setString(5, product.getContent());
			pstmt.setString(6, product.getImage());
			pstmt.setString(7, product.getUseyn());
			pstmt.setString(8, product.getBestyn());
			pstmt.setInt(9, product.getCode());
			
			r = pstmt.executeUpdate();
			if(r == 1) {
				System.out.println("업데이트 성공");
			}
			close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}

	//게시물 삭제
	public void deleteProduct(int code) {
		
		System.out.println("productDao deleteProduct code : " + code);
		
		sql = "";
		int r = 0;
		
		sql = "Delete from product ";
		sql += " Where code = ?";

		try {
			con = dataSource.getConnection();	//커넥션 객체 얻기
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, code);
			r = pstmt.executeUpdate();
			
			if(r > 0) {
				System.out.println("삭제 성공");
			}
			close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
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
