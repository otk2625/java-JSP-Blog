<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	
RequestDispatcher dispatcher = request.getRequestDispatcher("board?cmd=list");
dispatcher.forward(request, response);
%>