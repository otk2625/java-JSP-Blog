package com.cos.blog.config;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ForbiddenUrlConfig implements Filter {

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
    	HttpServletResponse response = (HttpServletResponse) resp;
    	String requestURI = request.getRequestURI();
    	
    	System.out.println(requestURI);
    	
    	
    	if(requestURI.indexOf("jusoPopup.jsp") != -1 || requestURI.indexOf("index.jsp") != -1) {
			chain.doFilter(request,response);
			
		} else {
			if (requestURI.indexOf(".jsp") != -1) {
	    		
	    		PrintWriter out = resp.getWriter();
	    		out.print("<script>alert('접속 불가');</script>");
				out.flush();
				
	        } else {
	            chain.doFilter(request,response);
	        }
		}
			
	}

}
