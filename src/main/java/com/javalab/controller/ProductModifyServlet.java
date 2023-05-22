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

@WebServlet("/productModify.do")
public class ProductModifyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//private ProductDao productDao = ProductDao.getInstance();
   
    public ProductModifyServlet() {
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		System.out.println("productModify doGet()");

		
		//파라미터 전달
		int code = Integer.parseInt(request.getParameter("code"));

		// Dao 객체 생성
		ProductDao productDao = new ProductDao();
		
		Product product = productDao.selectProductByCode(code);
		
		request.setAttribute("product", product);
		RequestDispatcher rd = request.getRequestDispatcher("productModify.jsp");
		rd.forward(request, response);	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		System.out.println("productModify doPost()");
		
		request.setCharacterEncoding("UTF-8");
		
		ServletContext context = getServletContext();

		String path = context.getRealPath("upload");
		String encType = "UTF-8";
		int sizeLimit = 20 * 1024 * 1024;
		
		MultipartRequest multi = new MultipartRequest(request,
														path,
														sizeLimit,
														encType,
														new DefaultFileRenamePolicy());

		int code = Integer.parseInt(multi.getParameter("code"));
		String name = multi.getParameter("name");
		int cost_price = Integer.parseInt(multi.getParameter("cost_price").replace(",", ""));
		int list_price = Integer.parseInt(multi.getParameter("list_price").replace(",", ""));
		String content = multi.getParameter("content");
		String picture = multi.getFilesystemName("picture");
		
		int kind = 1; 			// 임시로 카테고리를 1로 하드코딩
		
		System.out.println("picture : " + picture);
		
		//[하드코딩 - 화면에서 사용유무, 베스트상품 관련 정보가 넘어오면 받아서 할것.]
		String useyn = "1";		// 임시로 상품등록시에는 사용유무를 1(사용)으로 세팅
		String bestyn = "0";	// 임시로 상품등록시에는 베스트상품 유무를 0(아님)으로 세팅
		//String useyn = multi.getFilesystemName("useyn");
		//String bestyn = multi.getFilesystemName("bestyn");
		
		if(picture == null) {
			picture = multi.getParameter("nonMakeImg");
			System.out.println("이미지가 선택되지 않았음");
		}
		
		Product product = new Product();
		product.setCode(code);
		product.setName(name);
		product.setCost_price(cost_price);
		product.setList_price(list_price);
		product.setContent(content);
		product.setImage(picture);
		product.setUseyn(useyn);
		product.setBestyn(bestyn);
		product.setKind(kind);
		
		System.out.println("ProductModifyServlet : product : " + product.toString());
		
		// Dao 객체 생성
		ProductDao productDao = new ProductDao();
		
		productDao.updateProduct(product);
		
		response.sendRedirect("productList.do");
	}

}
