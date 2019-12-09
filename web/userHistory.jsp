<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Login</title>
    <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/css/Navigation-Clean.css">
    <link rel="stylesheet" href="assets/css/styles.css">
    <link href='http://fonts.googleapis.com/css?family=Open+Sans+Condensed:300' rel='stylesheet' type='text/css'>
    <link href="https://fonts.googleapis.com/css?family=Cinzel&display=swap" rel="stylesheet">

</head>

<body class="text-center">

<c:import url="navbar.jsp"></c:import>

<nav class="navbar navbar-light navbar-expand-md border-dark border rounded-0 navigation-clean">
    <div class="container"><a class="navbar-brand" style = "font-family: 'Cinzel', serif;" href="index.jsp">UCBUSCA</a><button data-toggle="collapse" class="navbar-toggler" data-target="#navcol-1"><span class="sr-only">Toggle navigation</span><span class="navbar-toggler-icon"></span></button>
        <div class="collapse navbar-collapse"
             id="navcol-1">
            <ul class="nav navbar-nav ml-auto">
                <li class="nav-item" role="presentation"><a class="nav-link" href="index.jsp">Buscar</a></li>
                <li class="nav-item" role="presentation"><a class="nav-link" href="register.jsp">Registrar</a></li>
            </ul>
        </div>
    </div>
</nav>
<div class="text-left border rounded-0 border-dark form-style-8"
     style="background-color: #ffffff;max-width: 350px;margin: auto;margin-top: 50px;">
    <s:iterator value="userHistoryBean.list">
        <p><s:date name="date" ></s:date> | <s:property value="query"></s:property></p>
        <br>
    </s:iterator>
</div>
<script src="assets/js/jquery.min.js"></script>
<script src="assets/bootstrap/js/bootstrap.min.js"></script>
</body>

</html>
