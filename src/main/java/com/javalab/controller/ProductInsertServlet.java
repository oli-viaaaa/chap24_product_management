package com.javalab.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javalab.dao.ProductDao;
import com.javalab.dto.Product;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

@WebServlet("/productInsert.do")
public class ProductInsertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//private ProductDao productDao = ProductDao.getInstance();

    public ProductInsertServlet() {
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("ProductInsertServlet doGet()");
		
		RequestDispatcher rd = request.getRequestDispatcher("productInsert.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("ProductInsertServlet doPost()");
		
		//request.setCharacterEncoding("UTF-8");		
		ServletContext context = getServletContext();

		/*
		 * [주의]	
		 * 이클립스의 WebContent 하위에 만든 폴더를 이용할 경우
		 * 톰캣서버 세팅에서  Serve modules without publishing에 체크
		 */
		String path = context.getRealPath("/upload");
		
		System.out.println("upload 폴더 경로 : " + path);
		
		String encType = "UTF-8";
		int sizeLimit = 20 * 1024 * 1024;
		
		// MultipartRequest 객체가 생성되면서 path 경로로 파일이 업로드됨.
		MultipartRequest multi = new MultipartRequest(request,
														path,
														sizeLimit,
														encType,
														new DefaultFileRenamePolicy());
		String name = multi.getParameter("name");
		int cost_price = Integer.parseInt(multi.getParameter("cost_price"));
		int list_price = Integer.parseInt(multi.getParameter("list_price"));
		String content = multi.getParameter("content");
		String picture = multi.getFilesystemName("picture");
		
		int kind = 1; 			// 임시로 카테고리를 1로 하드코딩
		String useyn = "1";		// 임시로 상품등록시에는 사용유무를 1(사용)으로 세팅
		String bestyn = "0";	// 임시로 상품등록시에는 베스트상품 유무를 0(아님)으로 세팅
		
		//넘어온 값을 Product 객체에 세팅
		Product product = new Product();
		product.setName(name);
		product.setCost_price(cost_price);
		product.setList_price(list_price);
		product.setContent(content);
		product.setImage(picture);
		product.setUseyn(useyn);
		product.setBestyn(bestyn);
		product.setKind(kind);
		
		// Dao 객체 생성
		ProductDao productDao = new ProductDao();
		
		productDao.insertProduct(product);
		System.out.println("productInsert servlet : " + product.toString());
		
		String contextPath = request.getContextPath();
		String url = contextPath + "/productList.do";
		
		response.sendRedirect(url);
	}

}
