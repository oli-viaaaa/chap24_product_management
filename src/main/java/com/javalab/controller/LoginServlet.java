package com.javalab.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javalab.dao.ProductDao;
import com.javalab.dao.WorkerDao;
import com.javalab.dto.Worker;

/**
 * 로그인 담당 서블릿
 */
@WebServlet("/login.do")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    // doGet 메소드(주로 조회 요청시 처리 담당)
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("WorkerLoginServlet doGet()");
		String contextPath = request.getContextPath();
		
		String url = contextPath + "/login.jsp";	
		
		/*
		 *  로그인 화면으로 이동
		 *  sendRedirect하기 때문에 사용자의 주소창도 바뀜
		 *  사용자로 하여금 다시 요청하게 해서 서버로 들어오는
		 *  것이므로 컨텍스트패스 붙여줘야 함.
		 */
		response.sendRedirect(url); // 로그인 화면으로 이동
	}

    // doPst 메소드(조회 이외의 요청시 처리 담당 -C/R/U/D)
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		
		String contextPath = request.getContextPath();
		String url = "";
		
		boolean flag = false;
		String msg = "";
		
		//파라미터 추출
		String id = request.getParameter("id");
		String pwd = request.getParameter("pwd");
		
		//넘어온 값을 Worker 객체에 세팅
		Worker worker = new Worker();
		worker.setId(id);
		worker.setPwd(pwd);
		
		WorkerDao workerDao = new WorkerDao();
		flag = workerDao.selectLoginCheck(worker);
		
		// 세션조회
		HttpSession session = request.getSession();
		if(flag) { // 데이터베이스에 사용자가 있을 경우
			msg = worker.getName() + " 님 반갑습니다.";

			session.setAttribute("id", id); // id를 세션에 저장			
			session.setMaxInactiveInterval(3600);	// 세션 유지 시간 지정(옵션, 초단위)
			
			// 쿠키 생성(세션과 쿠키 병행할 경우에)
			Cookie cookie = new Cookie("id", id);
			response.addCookie(cookie);
			System.out.println("로그인 성공! 세션과 쿠키 생성 완료!");
			
			url = contextPath + "/productList.do";
			response.sendRedirect(url);
			
		}else {
			msg = "로그인에 실패했습니다. 아이디와 비밀번호를 확인하세요.";
			
			// sendRedirect하게 되면 request 사용 불가하므로 세션에 오류 기록해서
			// 회원 가입 폼에서 보여줄 수 있도록 함.
			session.setAttribute("msg", msg); 
						
			RequestDispatcher rd = request.getRequestDispatcher("/login.jsp");
			rd.forward(request, response);	
		}
	}

}
