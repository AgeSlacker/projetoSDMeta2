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
    <link rel="shortcut icon" href="assets/img/icon.ico"/>
</head>

<body>
<nav class="navbar navbar-light navbar-expand-md border-dark border rounded-0 navigation-clean-search"
     style="margin-bottom: 20px;">
    <div class="container"><a class="navbar-brand" style="font-family: 'Cinzel', serif;" href="index.jsp">UCBUSCA</a>
        <button data-toggle="collapse" class="navbar-toggler" data-target="#navcol-1"><span class="sr-only">Toggle navigation</span><span
                class="navbar-toggler-icon"></span></button>
        <div class="collapse navbar-collapse"
             id="navcol-1">
            <s:form cssClass="form-inline mr-auto" action="searchResults" method="POST">
                <div class="form-group">
                    <s:textfield cssClass="border border-dark form-control search-field"
                                 cssStyle="border-radius:20px;width: 350px;"
                                 name="searchBean.searchTerms"
                                 id="search-field"
                    />
                </div>
                <i class="fa fa-search"
                   style="font-size: 24px;margin-top: -8px;margin-left: 10px;color: rgb(0,0,0);"></i>
            </s:form>
        </div>
        <c:if test="${session.emptySearch == true}">
            <p>Empty search...</p>
        </c:if>
        <ul class="nav navbar-nav">
            <c:choose>
                <c:when test="${session.logged == true}">
                    <li class="nav-item" role="presentation">
                        <p class="navbar-text" style="margin-top: 10px;">Welcome <c:out value="${clientBean.name}"/></p>
                    </li>
                    <li class="nav-item" role="presentation" style="margin-top: 10px;"><a class="nav-link"
                                                                                          href="index.jsp">Buscar</a>
                    </li>
                    <li class="nav-item" role="presentation" style="margin-top: 10px;">
                        <a class="nav-link" href="<s:url action="getUserHistory"></s:url> ">Histórico</a>
                    </li>
                    <li class="nav-item" style="margin-top: 10px;" role="presentation"><a class="nav-link"
                                                                                          href="linkedPages.jsp">Páginas
                        Linkadas</a></li>
                    <li class="nav-item" role="presentation" style="margin-top: 10px;">
                        <a class="nav-link" href="<s:url action="logout"/>">Logout</a>
                    </li>
                    <c:choose>
                        <c:when test="${clientBean.admin}">
                            <li class="nav-item" style="margin-top: 10px;" role="presentation"><a class="nav-link"
                                                                                                  href="adminPage.jsp">ADM</a>
                            </li>
                        </c:when>
                    </c:choose>
                </c:when>
                <c:otherwise>
                    <li class="nav-item" style="margin-top: 10px;" role="presentation"><a class="nav-link"
                                                                                          href="index.jsp">Buscar</a>
                    </li>
                    <li class="nav-item" style="margin-top: 10px;" role="presentation"><a class="nav-link"
                                                                                          href="login.jsp">Login</a>
                    </li>
                    <li class="nav-item" style="margin-top: 10px;" role="presentation"><a class="nav-link"
                                                                                          href="register.jsp">Registrar</a>
                    </li>
                </c:otherwise>
            </c:choose>
        </ul>
    </div>
    </div>
</nav>
<div style="margin-left: 50px; max-width: 800px">
    <s:iterator value="searchBean.searchResults">
        <div class="row">
            <div class="col">
                <div style="margin-bottom: 20px;width: 60%;"><a href="<s:property value="url"></s:property>"><s:property
                        value="name"></s:property></a>
                    <p style="margin-bottom: 0px;"><s:property value="url"></s:property></p>
                    <p style="margin-bottom: 9px;"><s:property value="description"></s:property></p>
                </div>
            </div>
        </div>
    </s:iterator>
</div>
<script src="assets/js/jquery.min.js"></script>
<script src="assets/bootstrap/js/bootstrap.min.js"></script>
<script>
    <jsp:include page="assets/js/websocket_connection.jsp"/>
</script>
</body>

</html>
