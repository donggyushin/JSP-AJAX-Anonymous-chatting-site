<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

<link rel="stylesheet" href="css/bootstrap.css">
<link rel="stylesheet" href="css/custom.css">
<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
<script src="js/bootstrap.js"></script>
<script type="text/javascript">

var lastID = 0;
const chatList = document.getElementById('chatList');

function submitFunction(){
	var chatName = $("#chatName").val();
	var chatContent = $("#chatContent").val();
	$.ajax({
		type: 'POST',
		url: './ChatSubmitServlet',
		data : {
			chatName : encodeURIComponent(chatName),
			chatContent : encodeURIComponent(chatContent)
		},
		success : function(result){
			if(result == 1){
				autoClosingAlert("#successMessage", 2000);
			}else if(result == 0){
				autoClosingAlert("#dangerMessage", 2000);
			}else{
				autoClosingAlert("#warningMessage", 2000);
			}
		}
	});
	$("#chatContent").val("");
}

function autoClosingAlert(selector, delay){
	var alert = $(selector).alert();
	alert.show();
	window.setTimeout(function(){alert.hide()}, delay);
}

function chatListFunction(type){
	$.ajax({
		type:'post',
		url:'./ChatListServlet',
		data:{
			listType : type
		},
		success : function(data){
			if(data == "") return;
			var parsed = JSON.parse(data);//JSON형태로 데이터를 파싱함.
			var result = parsed.result;		//파싱 받은 JSON을 객체로 다시 받아들임
			for(var i = 0 ; i < result.length; i ++){
				addChat(result[i][0].value, result[i][1].value, result[i][2].value);	//이름,내용,날짜 순으로 전달
			}
			lastID = Number(parsed.last);
		}
	});
}

function addChat(chatName, chatContent, chatTime){
	$("#chatList").append(
	'<li class="mar-btm">' +
	'<div class="media-left">' +
	'<img src="https://bootdey.com/img/Content/avatar/avatar1.png" class="img-circle img-sm" alt="Profile Picture">' +
	'</div>' +
	'<div class="media-body pad-hor">' +
	'<div class="speech">' +
	'<a href="#" class="media-heading">' + chatName + '</a>' +
	'<p>' + chatContent + '</p>' +
	'<p class="speech-time">' + 
	'<i class="fa fa-clock-o fa-fw"></i>' + chatTime + 
	'</p>' + 
	'</div>' + 
	'</div>' +
	'</li>'
	);
	$("#chatList").scrollTop($("#chatList")[0].scrollHeight);
	
}

function scrollToBottom(){
	chatList.scrollTop = chatList.scrollHeight;
}

function getInfiniteChat(){
	setInterval(function(){			//1초마다 내부에 있는 함수를 실행시켜줌. 
		chatListFunction(lastID);
	}, 1000);
}


</script>


<title>JSP AJAX 익명 체팅 사이트</title>
</head>
<body>
<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet">


<div class="container">
    <div class="col-md-12 col-lg-6">
        <div class="panel">
        	<!--Heading-->
    		<div class="panel-heading">
    			<div class="panel-control">
    				<div class="btn-group">
    					<button class="btn btn-default" type="button" data-toggle="collapse" data-target="#demo-chat-body"><i class="fa fa-chevron-down"></i></button>
    					<button type="button" class="btn btn-default" data-toggle="dropdown"><i class="fa fa-gear"></i></button>
    					<ul class="dropdown-menu dropdown-menu-right">
    						<li><a href="#">Available</a></li>
    						<li><a href="#">Busy</a></li>
    						<li><a href="#">Away</a></li>
    						<li class="divider"></li>
    						<li><a id="demo-connect-chat" href="#" class="disabled-link" data-target="#demo-chat-body">Connect</a></li>
    						<li><a id="demo-disconnect-chat" href="#" data-target="#demo-chat-body">Disconect</a></li>
    					</ul>
    				</div>
    			</div>
    			<h3 class="panel-title">Chat</h3>
    		</div>
    
    		<!--Widget body-->
    		<div id="demo-chat-body" class="collapse in">
    			<div class="nano has-scrollbar" style="height:380px">
    				<div class="nano-content pad-all" tabindex="0" style="right: -17px;">
    					<ul id="chatList" class="list-unstyled media-block">
    						
    					</ul>
    				</div>
    			<div class="nano-pane"><div class="nano-slider" style="height: 141px; transform: translate(0px, 0px);"></div></div></div>
    
    			<!--Widget footer-->
    			<div class="panel-footer">
    				<div class="row">
    					<div class="col-xs-9">
    						<input type="text" id="chatName" style="width:150px;" placeholder="Enter your Nickname" class="form-control" maxlength="20">
    					</div>
    				</div>
    				<hr>
    				<div class="row">
    					<div class="col-xs-9">
    						<input type="text" id="chatContent" placeholder="Enter your text" class="form-control chat-input" maxlength="100">
    					</div>
    					<div class="col-xs-3">
    						<button class="btn btn-primary btn-block" onclick="submitFunction();" type="submit">Send</button>
    					</div>
    				</div>
    			</div>
    		</div>
    		<div class="alert alert-success" id="successMessage" style="display:none;">
				<strong>Success sending message</strong>
			</div>
			<div class="alert alert-danger" id="dangerMessage" style="display:none;">
				<strong>Fill the whole form</strong>
			</div>
			<div class="alert alert-warning" id="warningMessage" style="display:none;">
				<strong>Database error has incurred</strong>
			</div>
    	</div>
    </div>
</div>
<script type="text/javascript">
$(document).ready(function(){		//페이지가 로딩이 완료 되었을때 자동으로 실행시키는 함수. 
	chatListFunction('ten');
	getInfiniteChat();
});
</script>
</body>
</html>