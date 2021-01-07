<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	
RequestDispatcher dispatcher = request.getRequestDispatcher("board?cmd=list&page=0"); 
dispatcher.forward(request, response);
%>