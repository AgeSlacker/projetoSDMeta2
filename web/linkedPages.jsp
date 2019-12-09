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

<body class="text-center">
<nav class="navbar navbar-light navbar-expand-md border-dark border rounded-0 navigation-clean-search" style="margin-bottom: 20px;">
    <div class="container"><a class="navbar-brand" style = "font-family: 'Cinzel', serif;" href="index.jsp">UCBUSCA</a><button data-toggle="collapse" class="navbar-toggler" data-target="#navcol-1"><span class="sr-only">Toggle navigation</span><span class="navbar-toggler-icon"></span></button>
        <div class="collapse navbar-collapse" id="navcol-1">
            <s:form cssClass="form-inline mr-auto" action="getLinkedPages" method="POST">
                <div class="form-group">
                    <s:textfield cssClass="border border-dark form-control search-field"
                                 cssStyle="border-radius:20px;width: 350px;"
                                 name="linkedPagesBean.link" placeholder="url"
                    />
                </div>
                <i class="fa fa-search" style="font-size: 24px;margin-top: -8px;margin-left: 10px;color: rgb(0,0,0);"></i>
            </s:form>
        </div>
        <ul class="nav navbar-nav">
            <c:choose>
                <c:when test="${session.logged == true}">
                    <li class="nav-item" role="presentation">
                        <p class="navbar-brand" style = "font-family: 'Cinzel', serif;">Welcome <c:out value="${loginBean.name}"/></p>
                    </li>
                    <li class="nav-item" role="presentation"><a class="nav-link" href="index.jsp">Buscar</a></li>
                    <li class="nav-item" role="presentation">
                        <a class="nav-link" href="<s:url action="getUserHistory"></s:url> ">Histórico</a>
                    </li>
                    <li class="nav-item" role="presentation"><a class="nav-link" href="linkedPages.jsp">Páginas Linkadas</a></li>
                    <li class="nav-item" role="presentation">
                        <a class="nav-link" href="<s:url action="logout"/>">Logout</a>
                    </li>
                    <c:choose >
                        <c:when test = "${loginBean.admin}">
                            <li class="nav-item" role="presentation"><a class="nav-link" href="adminPage.jsp">Página Administrador</a></li>
                        </c:when>
                    </c:choose>
                </c:when>
                <c:otherwise>
                    <li class="nav-item" role="presentation"><a class="nav-link" href="index.jsp">Buscar</a></li>
                    <li class="nav-item" role="presentation"><a class="nav-link" href="login.jsp">Login</a></li>
                    <li class="nav-item" role="presentation"><a class="nav-link" href="register.jsp">Registrar</a></li>
                </c:otherwise>
            </c:choose>
        </ul>
    </div>
</nav>
<div style="margin-left: 50px; max-width: 800px">
    <c:forEach items="${linkedPagesBean.linkList}" var="link">
        <div class="row">
            <div class="col">
                <div style="margin-bottom: 20px;width: 60%;">
                    <a href="<c:out value="${link}"></c:out>"><c:out value="${link}"></c:out></a>
                </div>
            </div>
        </div>
    </c:forEach>
</div>
<script src="assets/js/jquery.min.js"></script>
<script src="assets/bootstrap/js/bootstrap.min.js"></script>
</body>

</html>

</html>
