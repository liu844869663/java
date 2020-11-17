<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>首页</title>
</head>
<body>
<h3>Hello World!</h3>
<form method="get" action="<c:url value="/test/"/>">
    <button class="btn btn-default" type="submit">
        <span>开始测试</span>
    </button>
</form>
</body>
</html>
