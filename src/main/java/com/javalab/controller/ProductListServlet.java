package com.javalab.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javalab.dao.ProductDao;
import com.javalab.dto.Product;
import com.javalab.util.PageNavigator;
/**
 * 상품 조회 서블릿
 */
@WebServlet("/productList.do")
public class ProductListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("productList doGet()");
		
		request.setCharacterEncoding("UTF-8");		
		List<Product> products = new ArrayList<Product>();

		String pageNum = request.getParameter("pageNum");	//페이지 번호를 눌렀을 때 전달
		String searchText = request.getParameter("searchText");
		
		//처음 화면이 열릴 때는 기본적으로 모든 상품리스트
		if(pageNum == null) {
			pageNum = "1";
		}
		if(searchText == null) {	
			searchText = "";
		}
		
		// 요청페이지와 검색어 정보를 유지하기 위해서
		// Product 객체에 보관
		Product product = new Product();
		product.setPageNum(pageNum);
		product.setSearchText(searchText);

		// Dao 객체 생성
		ProductDao productDao = new ProductDao();
		
		// 상품 등록 건수 조회
		int totalCount = productDao.selectCount(product);
		
		// 상품 리스트 조회
		products = productDao.getProductList(product);
		
		request.setAttribute("totalCount", totalCount);
		request.setAttribute("pageNum", pageNum);
		
		// 리스트페이지에 보여주는 순번을 위한(한페이지에 보여줄 게시물수)
		request.setAttribute("listCount", product.getListCount());
		
		// 페이징 네비게이션 문자열 얻기
		PageNavigator pageNavigator = new PageNavigator();
		String pageNums = pageNavigator.getPageNavigator(totalCount, 	//총 게시물 수
										product.getListCount(), 		//한 페이지에 보여줄 게시물수
										product.getPagePerBlock(), 		//한 페이지에 보여줄 페이지 블럭
										Integer.parseInt(pageNum), 		//요청된 페이지 번호
										searchText);
		
		// 페이징 부분을 String으로 만들어서 보냄
		request.setAttribute("page_navigator", pageNums);
		// 상품 리스트를 request 객체에 세팅
		request.setAttribute("productList", products);		
		// 페이지번호, 검색어 관련 정보를 보관하고 있는 상품 객체 세팅
		// 검색타입 검색어를 계속 유지키위해
		request.setAttribute("product", product);	

		System.out.println();
		
		RequestDispatcher rd = request.getRequestDispatcher("/productList.jsp");
		rd.forward(request, response);		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
