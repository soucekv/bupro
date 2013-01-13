package cz.cvut.fel.bupro.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/example")
public class ExampleServlet extends HttpServlet {
	private static final long serialVersionUID = 5556311877424536219L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("GET Example Servlet");		
	    response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
	    out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 " +
	                                        "Transitional//EN\">\n" +
	                "<HTML>\n" +
	                "<HEAD><TITLE>Hello WWW</TITLE></HEAD>\n" +
	                "<BODY>\n" +
	                "<H1>Hello WWW</H1>\n" +
	                "</BODY></HTML>");		
		out.flush();
		out.close();
	}
}
