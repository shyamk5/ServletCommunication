package com.sk.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class EMPDataServlet extends HttpServlet {

	private static final String EMP_QUERY = "SELECT EMPNO,ENAME,JOB,SAL,DEPTNO FROM EMP WHERE EMPNO=?";

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		try {
			//get PrintWriter obj
			PrintWriter pw = res.getWriter();

			//set Content type
			res.setContentType("text/html");
			
			/* Also Working
			//Add header content 
			RequestDispatcher rdh = req.getRequestDispatcher("/headerurl");
			rdh.include(req, res);
			*/
			
			//Add header content by HTML
			RequestDispatcher rdh = req.getRequestDispatcher("/header.html");
			rdh.include(req, res);

			//read form data
			int empno = Integer.parseInt(req.getParameter("eno"));

			//read data from servlet-context
			ServletConfig cg = getServletConfig();
			System.out.println("EMPDataServlet.doGet() ServletConfig Object cg::  "+cg.hashCode());
			String dbClass = cg.getInitParameter("driverClass");
			String dbUrl = cg.getInitParameter("DBurl");
			String dbUser = cg.getInitParameter("DBuser");
			String dbPwd = cg.getInitParameter("DBpwd");

			//load the JDBC driver class 
			try {
				Class.forName(dbClass);
			} catch(ClassNotFoundException cnf) {
				cnf.printStackTrace();
			}

			//write Persistence logic
			try( Connection con = DriverManager.getConnection(dbUrl,dbUser,dbPwd);
					PreparedStatement ps = con.prepareStatement(EMP_QUERY);
					) {

				if(ps != null)
					ps.setInt(1, empno);

				try(ResultSet rs = ps.executeQuery()) {

					if(rs != null) {
						if(rs.next()) {
							pw.println("<h1 style='color:blue; text-align:center;'>Employee details are</h1>");
							pw.println("<br><p style='text-align:center;'><b>Employee Id:: "+rs.getInt(1)+"</b><br>");
							pw.println("<b>Employee Name:: "+rs.getString(2)+"</b><br>");
							pw.println("<b>Employee Job:: "+rs.getString(3)+"</b><br>");
							pw.println("<b>Employee Salary:: "+rs.getFloat(4)+"</b><br>");
							pw.println("<b>Employee Dept No.:: "+rs.getInt(5)+"</b></p>");
						}//if
						else {
							pw.println("<h1 style='color:red; text-align:center;'>Sorry! Employee Not Found</h1>");
						}//else
					}//if

					pw.println("<br><br><br><p style='color:pink; text-align:center;'><a href='index.html'><b>Back to Home</b></a></p>");

				}//try2
			}//try1
			
			//Add footer content 
			RequestDispatcher rdf = req.getRequestDispatcher("/footer.html");
			rdf.include(req, res);
			
			//close the stream
			pw.close();
		}//try
		catch(Exception e) {
			e.printStackTrace();
			RequestDispatcher rd = req.getRequestDispatcher("/errurl"); //Working
			//RequestDispatcher rd = req.getRequestDispatcher("errurl"); //Working
			//ServletContext sc = getServletContext();
			//RequestDispatcher rd = sc.getRequestDispatcher("/errurl"); //Working
			//RequestDispatcher rd = sc.getNamedDispatcher("err"); //Working
			rd.forward(req, res);
		}

	}//doGet

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}

}