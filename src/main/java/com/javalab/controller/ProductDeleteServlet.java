package com.javalab.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javalab.dao.ProductDao;
import com.javalab.dto.Product;

@WebServlet("/productDelete.do")
public class ProductDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//private ProductDao productDao = ProductDao.getInstance();

    public ProductDeleteServlet() {
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("productDelete doGet()");
		
		//파라미터 전달
		int code = Integer.parseInt(request.getParameter("code"));

		// Dao 객체 생성
		ProductDao productDao = new ProductDao();
		
		Product product = productDao.selectProductByCode(code);
		
		request.setAttribute("product", product);
		RequestDispatcher rd = request.getRequestDispatcher("/productDelete.jsp");
		rd.forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("productDelete doPost()");

		int code = Integer.parseInt(request.getParameter("code"));
		
		// Dao 객체 생성
		ProductDao productDao = new ProductDao();
		productDao.deleteProduct(code);
		
		String contextPath = request.getContextPath();
		
		response.sendRedirect(contextPath + "/productList.do");
	}

}
