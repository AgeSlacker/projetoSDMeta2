<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Untitled</title>
    <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/fonts/font-awesome.min.css">
    <link rel="stylesheet" href="assets/css/Navigation-Clean.css">
    <link rel="stylesheet" href="assets/css/Navigation-with-Search.css">
    <link rel="stylesheet" href="assets/css/styles.css">
    <link href='http://fonts.googleapis.com/css?family=Open+Sans+Condensed:300' rel='stylesheet' type='text/css'>
    <link href="https://fonts.googleapis.com/css?family=Cinzel&display=swap" rel="stylesheet">
</head>

<body>
    <nav class="navbar navbar-light navbar-expand-md border-dark border rounded-0 navigation-clean-search" style="margin-bottom: 13px;">
        <div class="container"><a class="navbar-brand" style = "font-family: 'Cinzel', serif;" href="index.jsp">UCBUSCA</a><button data-toggle="collapse" class="navbar-toggler" data-target="#navcol-1"><span class="sr-only">Toggle navigation</span><span class="navbar-toggler-icon"></span></button>
            <div class="collapse navbar-collapse"
                id="navcol-1">
                <form class="form-inline mr-auto" target="_self">
                    <div class="form-group"><input class="border border-dark form-control search-field" type="search" id="search-field" name="search" style="border-radius:20px;width: 350px;"><label for="search-field"><i class="fa fa-search" style="font-size: 24px;margin-top: -8px;margin-left: 10px;color: rgb(0,0,0);"></i></label></div>
                </form>
                <ul class="nav navbar-nav">
                    <li class="nav-item" role="presentation"><a class="nav-link" href="login.jsp">Login</a></li>
                    <li class="nav-item" role="presentation"><a class="nav-link" href="register.jsp">Register</a></li>
                </ul>
            </div>
        </div>
    </nav>
    <s:iterator value = "serachBean.results">
        <div class="row">
            <div class="col">
                <div style="margin-bottom: 15px;width: 60%;"><a href="<s:property value="url"></s:property>"><s:property value="name"></s:property></a>
                    <p style="margin-bottom: 0px;"><s:property value="url"></s:property></p>
                    <p style="margin-bottom: 9px;"><s:property value="description"></s:property></p>
                </div>
            </div>
        </div>
    </s:iterator>
    <script src="assets/js/jquery.min.js"></script>
    <script src="assets/bootstrap/js/bootstrap.min.js"></script>
</body>

</html>
