<%--
  Created by IntelliJ IDEA.
  User: jingp
  Date: 2019/6/5
  Time: 15:17
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>查询</title>

</head>
<body>
<h3>查询数据库</h3>
<form method="get" action="<c:url value="/test/query"/>">
    <div class="input-group">
        <span class="input-group-addon">ID：</span>
        <input type="number" name="id" class="form-control" required>
    </div>
    <button class="btn btn-default" type="submit">
        <span>查询</span>
    </button>
</form>

<script src="../../static/jquery.min.js"></script>
</body>
</html>
