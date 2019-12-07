<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Pagina de Pesquisa</title>
    <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/css/Navigation-Clean.css">
    <link rel="stylesheet" href="assets/css/styles.css">
    <link href="https://fonts.googleapis.com/css?family=Cinzel&display=swap" rel="stylesheet">
    <link rel="shortcut icon" href="#" />
</head>

<body style="overflow-x:hidden">
    <nav class="navbar navbar-light navbar-expand-md navigation-clean">
        <div class="container"><button data-toggle="collapse" class="navbar-toggler" data-target="#navcol-1"><span class="sr-only">Toggle navigation</span><span class="navbar-toggler-icon"></span></button>
            <div class="collapse navbar-collapse" id="navcol-1">
                <ul class="nav navbar-nav ml-auto">
                    <li class="nav-item" role="presentation"><a class="nav-link" href="Login.html">Login</a></li>
                    <li class="nav-item" role="presentation"><a class="nav-link" href="Register.html">Registrar</a></li>
                </ul>
            </div>
        </div>
    </nav>
    <div class="row" style="margin-top:20px;">
        <div class="col">
            <div class="text-center"><img class="img-fluid" src="assets/img/ucbusca.png" style="width: 800px;margin-top: 8px;"></div>
        </div>
    </div>
    <div class="row">
        <div class="col">
            <h1 class="text-center" style = "font-family: 'Cinzel', serif;">UCBUSCA</h1>
        </div>
    </div>
    <div class="row">
        <div class="col text-center">
            <s:form action="searchResults" method="POST">
                <s:textfield name="searchBean.searchTerms"
                             cssClass="border-dark"
                             cssStyle="outline: none;padding-left: 20px;margin-top: 20px; width:35%; height:40px;border-radius:20px;"
                             placeholder="Escreva aqui!"
                             theme="simple"
                ></s:textfield>
                <s:submit cssClass="btn-dark" value="Search" theme="simple"/>
                <c:if test="${session.emptySearch == true}">
                    <p>Empty search...</p>
                </c:if>
            </s:form>
        </div>
    </div>
    <script src="assets/js/jquery.min.js"></script>
    <script src="assets/bootstrap/js/bootstrap.min.js"></script>
</body>

</html>
