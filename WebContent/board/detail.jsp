<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">

	<!-- POST,GET, DELETE, PUT -->
	<!-- 자바스크립트로 POST로 삭제 요청 -->
	<!-- 인증 + 권한이 필요 sessionScope.principal.id비교 detail.userid -->
	<div class="form-row float-right">
		<c:if test="${sessionScope.principal.id == detail.userid}">
			<a href="/blog/board?cmd=updateForm&id=${detail.id }"
				class="btn btn-warning">수정</a>
		</c:if>
		<c:if test="${sessionScope.principal.id == detail.userid}">
			<button class="btn btn-danger" onclick="deleteByid(${detail.id})">삭제</button>
		</c:if>
	</div>



	<br /> <br />
	<h6 class="m-2">
		작성자 : <i>${detail.username}</i> 조회수 : <i>${detail.readCount}</i>
	</h6>
	<br />
	<h3 class="m-2">
		<b>제목 : ${detail.title}</b>
	</h3>
	<hr />
	<div class="form-group">
		<b>내용</b>
		<div class="m-2">${detail.content}</div>
	</div>

	<hr />

	<!-- 댓글 박스 -->
	<div class="row bootstrap snippets">
		<div class="col-md-12">
			<div class="comment-wrapper">
				<div class="panel panel-info">
					<div class="panel-heading m-2">
						<b>Comment</b>
					</div>
					<div class="panel-body">


						<input type="hidden" name="userId"
							value="${sessionScope.principal.id}" /> <input type="hidden"
							name="boardId" value="${detail.id}" />
						<textarea id="content" id="reply__write__form"
							class="form-control" placeholder="write a comment..." rows="2"></textarea>
						<br>
						
						<c:choose>
							<c:when test="${sessionScope.principal.id != null}">
								<button
							onClick="replySave(${sessionScope.principal.id}, ${detail.id})"
							class="btn btn-primary pull-right">댓글쓰기</button>
							</c:when>
							<c:otherwise>
								
								<a class="btn btn-primary pull-right" href="/blog/user?cmd=loginForm" onClick="alert('로그인 후 이용가능합니다')">댓글쓰기</a>
							</c:otherwise>
						</c:choose>

						<div class="clearfix"></div>
						<hr />
						<!-- 댓글 리스트 시작-->
						<ul id="reply__list" class="media-list">

							<!-- 댓글 아이템 -->
							<c:forEach items="${replys}" var="reply" varStatus="status">
								<li id="reply-1" class="media">
									<div class="media-body">
										<strong class="text-primary">${reply.username}</strong>
										<p>${reply.content}</p>
									</div>
									<div class="m-2">
										
										<c:if test="${sessionScope.principal.id == reply.userId}">
										<i  onclick="replyDeleteByid(${reply.id},${detail.id})" class="material-icons">delete</i>
										</c:if>
									</div>
								</li>
							</c:forEach>

						</ul>
						<!-- 댓글 리스트 끝-->
					</div>
				</div>
			</div>

		</div>
	</div>
	<!-- 댓글 박스 끝 -->
</div>

<script src="/blog/js/boardDetail.js"></script>
</body>
</html>