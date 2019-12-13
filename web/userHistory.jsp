<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Histórico</title>
    <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/css/Navigation-Clean.css">
    <link rel="stylesheet" href="assets/css/styles.css">
    <link href='http://fonts.googleapis.com/css?family=Open+Sans+Condensed:300' rel='stylesheet' type='text/css'>
    <link href="https://fonts.googleapis.com/css?family=Cinzel&display=swap" rel="stylesheet">
    <link rel="shortcut icon" href="assets/img/icon.ico"/>
</head>

<body class="text-center">
<%@include file="navbar.jsp" %>
<div class="text-left border rounded-0 border-dark form-style-8"
     style="background-color: #ffffff;max-height:500px;overflow-y:scroll;max-width: 350px;margin: auto;margin-top: 50px;">
    <s:iterator value="userHistoryBean.list">
        <p><s:date name="date"></s:date> | <s:property value="query"></s:property></p>
        <br>
    </s:iterator>
</div>
<script src="assets/js/jquery.min.js"></script>
<script src="assets/bootstrap/js/bootstrap.min.js"></script>
<script>
    <jsp:include page="assets/js/websocket_connection.jsp"/>
</script>
</body>

</html>
