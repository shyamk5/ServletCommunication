package com.sk.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class HeaderServlet extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//get PrintWriter Object
		PrintWriter pw = res.getWriter();
		
		//set response content type
		res.setContentType("text/html");
		
		//write Header code that appears on the page
		pw.println("<marquee><h1 style='color:blue';>RecentureSoft Infotech Pvt. Ltd.</h1></marquee>");
		
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}
}
