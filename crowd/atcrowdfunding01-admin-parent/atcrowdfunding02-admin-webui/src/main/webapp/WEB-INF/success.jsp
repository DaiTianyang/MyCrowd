<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	成功！
	<br />
	<!-- private Integer id;

    private String loginAcct;

    private String userPswd;

    private String userName;

    private String email;

    private String createTime; -->
	<table>
		<c:forEach items="${adminList }" var="admins">
			<tr>
				<td><input type="text" value="${admins.id }" /></td>
				<td><input type="text" value="${admins.loginAcct }" /></td>
				<td><input type="text" value="${admins.userPswd }" /></td>
				<td><input type="text" value="${admins.userName }" /></td>
				<td><input type="text" value="${admins.email }" /></td>
				<td><input type="text" value="${admins.createTime }" /></td>
			</tr>
		</c:forEach>

	</table>
</body>
</html>