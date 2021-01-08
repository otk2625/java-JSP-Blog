<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">

	<!-- POST,GET, DELETE, PUT -->
	<!-- 자바스크립트로 POST로 삭제 요청 -->
	<!-- 인증 + 권한이 필요 sessionScope.principal.id비교 detail.userid -->
	<div class="form-row float-right">
	<c:if test = "${sessionScope.principal.id == detail.userid}">
		<a href="/blog/board?cmd=updateForm&id=${detail.id }" class="btn btn-warning">수정</a>
	</c:if>
	<c:if test = "${sessionScope.principal.id == detail.userid}">
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
						<textarea id="reply__write__form" class="form-control"
							placeholder="write a comment..." rows="2"></textarea>
						<br>
						<button onclick="#" class="btn btn-primary pull-right">댓글쓰기</button>
						<div class="clearfix"></div>
						<hr />

						<!-- 댓글 리스트 시작-->
						<ul id="reply__list" class="media-list">

							<!-- 댓글 아이템 -->
							<li id="reply-1" class="media">
								<div class="media-body">
									<strong class="text-primary">홍길동</strong>
									<p>댓글입니다.</p>
								</div>
								<div class="m-2">

									<i onclick="#" class="material-icons">delete</i>

								</div>
							</li>

						</ul>
						<!-- 댓글 리스트 끝-->
					</div>
				</div>
			</div>

		</div>
	</div>
	<!-- 댓글 박스 끝 -->
</div>

<script>
	/*function deleteByid(id){
		//ajax로 delete요청(post) 요청과 응답을 모두 json으로 통일
		var data ={
			id:id	
		}
		console.log(data);
		console.log(JSON.stringify(data))
			$.ajax({
				type: "post", //delete면 바디에담을수 없음
	            url: "/blog/board?cmd=delete",
	            data: JSON.stringify(data),
	            contentType:"application/json; charset=utf-8;",
	            dataType: "json"
			}).done(function(result){
				if(result == "ok"){
					alert("삭제 완료");
					location.href="index.jsp"
				}else
					alert("오류");
			});
	}*/ //이걸로 굳이 안써도 됨!ajax는 delete로 요청할때
		//data, contentType가 필요없다
		//url에 delete&id=boardid로 보내면 됨굳이 json파싱 안해도됨

		
	function deleteByid(boardId){
		$.ajax({
			type: "post",
			url: "/blog/board?cmd=delete&id="+boardId,
			dataType: "json"
		}).done(function(result){
			console.log(result);
			if(result.statusCode == 1){
				alert("삭제 완료");
				location.href="index.jsp";
			}else{
				alert("삭제에 실패하였습니다.");
			}
		});
	}

</script>
</body>
</html>