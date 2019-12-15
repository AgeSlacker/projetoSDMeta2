<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Registrar</title>
    <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/css/Navigation-Clean.css">
    <link rel="stylesheet" href="assets/css/styles.css">
    <link href='https://fonts.googleapis.com/css?family=Open+Sans+Condensed:300' rel='stylesheet' type='text/css'>
    <link href="https://fonts.googleapis.com/css?family=Cinzel&display=swap" rel="stylesheet">
    <link rel="shortcut icon" href="assets/img/icon.ico"/>
</head>

<body class="text-center">
<%@include file="navbar.jsp" %>

<s:form action="register" method="POST">
    <div class="text-center border rounded-0 border-dark form-style-8"
         style="background-color: #ffffff;width: 298px;margin: auto;margin-top: 50px;">
        <h1 style="margin-top: 58px;">Registrar</h1>
        <div class="form-group">
            <s:textfield name="registerBean.name" placeholder="Usuário" cssStyle="margin-top: 44px;"
                         id="nameField"></s:textfield>
            <c:if test="${session.errorUserExists}">
                <small class="text-danger">Username not available.</small>
            </c:if>
        </div>
        <div class="form-group">
            <s:password name="registerBean.password" placeholder="Senha" cssStyle="margin-top: 9px;"></s:password>
        </div>
        <div class="form-group">
            <s:submit cssStyle="border:none;width: 181px;background-color: rgb(0,0,0);margin-top: 25px;"
                      cssClass="btn btn-dark" value="Registar"></s:submit>
        </div>
        <a href="<s:url action="login"/>" style="color: rgb(0,0,0);">Já Possui Conta?</a>
        <s:a action="loginWithFacebook">Registre-se com o Facebook</s:a>
    </div>
</s:form>
<script src="assets/js/jquery.min.js"></script>
<script src="assets/bootstrap/js/bootstrap.min.js"></script>
<script>
    $(document).ready(function () {
        $("#nameField").focus();
    });
</script>
</body>

</html>
