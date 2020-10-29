<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<base
	href="http://${pageContext.request.serverName }:${pageContext.request.serverPort }${pageContext.request.contextPath }/">
<script type="text/javascript" src="jquery/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="layer/layer.js"></script>

<script type="text/javascript">
	$(function() {
		var student = {
			"id" : "01",
			"sname" : "王五",
			"address" : {
				"province" : "黑龙江",
				"city" : "哈尔滨市",
				"street" : "中央大街"
			},
			"subject" : [ {
				"subjectName" : "JAVA",
				"subjectChengji" : 100

			}, {
				"subjectName" : "WEB",
				"subjectChengji" : 100

			}, {
				"subjectName" : "PYTHON",
				"subjectChengji" : 100

			},

			],
			"map" : {
				"k1" : "v1",
				"k2" : "v2",
				"k3" : "v3"
			}

		};
		var requestBoby = JSON.stringify(student);
		$("#btn02").click(function() {
			$.ajax({
				"url" : "send/student.json",
				"type" : "post",
				"data" : requestBoby,
				"contentType" : "application/json;charset=UTF-8",
				"dataType" : "json",
				"success" : function(response) {
					console.log(response);

				},
				"error" : function(response) {
					console.log(response);
				}

			});

		});
		$("#btn01").click(function() {
			var array = [ 5, 12, 8 ];
			var requestBoby = JSON.stringify(array);
			$.ajax({
				"url" : "send/array.html",
				"type" : "post",
				"data" : requestBoby,
				"contentType" : "application/json;charset=UTF-8",
				"dataType" : "text",
				"success" : function(response) {
					alert(response);
				},
				"error" : function(response) {
					alert(response);
				}

			});

		});

		$("#btn03").click(function() {
			layer.msg("layer提示框");

		});

	});
</script>
</head>
<body>
	<a href="test/ssm.html">SSM环境整合测试</a>
	<br />
	<button id="btn01">Json按钮</button>
	<br />
	<button id="btn02">Student测试Json</button>
	<br />
	<button id="btn03">点我弹框</button>
	<br />
</body>
</html>