<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>结果</title>
</head>
<body>
<h4>查询结果：${test.toString()}</h4>
<form method="post" action="<c:url value="/"/>">
    <button class="btn btn-default" type="submit">
        <span>退出</span>
    </button>
</form>

<script src="../../static/jquery.min.js"></script>
</body>
</html>
