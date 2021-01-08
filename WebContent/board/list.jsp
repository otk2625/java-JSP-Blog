<%@page import="com.cos.blog.domain.board.Board"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>




<div class="container">

	<div class="m-2">
		<form class="form-inline d-flex justify-content-end"
			action="/blog/board">
			<input type="hidden" name="cmd" value="search" /> <input
				type="hidden" name="page" value="0" /> <input type="text"
				name="keyword" class="form-control mr-sm-2" placeholder="Search">
			<button class="btn btn-primary m-1">검색</button>

		</form>
	</div>

	<div class="progress col-md-12 m-2">
		<div class="progress-bar" style="width: ${currentpercent}%"></div>
	</div>




	<!-- JSTL foreach문을 써서 뿌리세요. el표현식과 함께 -->
	<!-- requestScope가 생략됨  -->
	<c:forEach items="${boards}" var="item" varStatus="status">
		<div class="card col-md-12 m-2">
			<div class="card-body">
				<h4 class="card-title">${item.title}</h4>
				<a href="/blog/board?cmd=detail&id=${item.id}"
					class="btn btn-primary">상세보기</a>
			</div>

		</div>
	</c:forEach>

	<br />
	<ul class="pagination justify-content-center">

		<c:choose>

			<c:when test="${preEnd eq true}">
				<li class="page-item disabled"><a class="page-link"
					href="javascript:void(0)">Previous</a></li>
			</c:when>

			<c:otherwise>
				<li class="page-item"><a class="page-link"
					href="/blog/board?cmd=list&page=${param.page-1}">Previous</a></li>
			</c:otherwise>
		</c:choose>

		<c:choose>
			<c:when test="${nextEnd eq true}">

				<li class="page-item disabled"><a class="page-link"
					href="javascript:void(0)">Next</a></li>
			</c:when>

			<c:otherwise>

				<li class="page-item"><a class="page-link"
					href="/blog/board?cmd=list&page=${param.page+1}">Next</a></li>
			</c:otherwise>
		</c:choose>

		<%-- <li class="page-item"><a class="page-link"
			href="/blog/board?cmd=list&page=${param.page-1}">Previous</a></li>
		<li class="page-item"><a class="page-link"
			href="/blog/board?cmd=list&page=${param.page+1}">Next</a></li>
		
	</ul> --%>
</div>

<script>
	
</script>
</body>
</html>