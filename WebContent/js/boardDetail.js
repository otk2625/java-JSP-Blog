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
	
	function replyDeleteByid(replyid,boardId){
		$.ajax({
			type: "post",
			url: "/blog/reply?cmd=replyDelete&id="+replyid,
			dataType: "json"
		}).done(function(result){
			console.log(result);
			if(result.statusCode == 1){
				alert("삭제 완료");
				location.href="/blog/board?cmd=detail&id="+boardId;
			}else{
				alert("삭제에 실패하였습니다.");
			}
		});
	}

			
function replySave(userId, boardId){
		
		var data = {
			userId: userId,
			boardId: boardId,
			content: $("#content").val()
		}
		$.ajax({
			type: "post",
			url: "/blog/reply?cmd=save",
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8",
			dataType: "json"
		}).done(function(result){
			if(result.statusCode == 1){
				addReply(result.data[0]);
				location.href="/blog/board?cmd=detail&id="+boardId;
				//location.reload(); 하면 된다
			}else{
				alert("댓글쓰기 실패");
			}
		});
	}
	
function addReply(data){
	console.log(data.username);
	var replyItem = `<li id="reply-${data.id}" class="media">`;
	replyItem += `<div class="media-body">`;
	replyItem += `<strong class="text-primary">${data.username}</strong>`;
	replyItem += `<p>${data.content}</p></div>`;
	replyItem += `<div class="m-2">`;
	replyItem += `<i onclick="deleteReply(${data.id})" class="material-icons">delete</i></div></li>`;

	$("#reply__list").prepend(replyItem);
}
	