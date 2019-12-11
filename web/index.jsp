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
    <link rel="shortcut icon" href="#"/>
    <link rel="stylesheet" href="assets/fonts/font-awesome.min.css">
    <link href='http://fonts.googleapis.com/css?family=Open+Sans+Condensed:300' rel='stylesheet' type='text/css'>
    <link href="https://fonts.googleapis.com/css?family=Cinzel&display=swap" rel="stylesheet">
    <link rel="shortcut icon" href="assets/img/icon.ico"/>
</head>

<body style="overflow-x:hidden">
<%@include file="navbar.jsp" %>
<div class="row" style="margin-top:20px;">
    <div class="col">
        <div class="text-center"><img class="img-fluid" src="assets/img/ucbusca.png"
                                      style="width: 800px;margin-top: 8px;"></div>
    </div>
</div>
<div class="row">
    <div class="col">
        <h1 class="text-center" style="font-family: 'Cinzel', serif;">UCBUSCA</h1>
    </div>
</div>
<div class="row">
    <div class="col text-center">
        <s:form action="searchResults" method="POST">
            <s:textfield name="searchBean.searchTerms"
                         cssClass="border-dark"
                         cssStyle="min-width:300px;outline: none;padding-left: 20px;margin-top: 20px; width:35%; height:40px;border-radius:20px;"
                         placeholder="Faça sua Pesquisa no UCBusca!"
                         theme="simple"
                         id="searchBar"
            ></s:textfield>
            <c:if test="${session.emptySearch == true}">
                <p style="margin-top: 5px;color:darkred;">Não é possivel efetuar uma pesquisa em branco</p>
            </c:if>
        </s:form>
    </div>
</div>
<script src="assets/js/jquery.min.js"></script>
<script src="assets/bootstrap/js/bootstrap.min.js"></script>
<script>
    $(document).ready(function () {
        $("#searchBar").focus();
    });
</script>
</body>

</html>
