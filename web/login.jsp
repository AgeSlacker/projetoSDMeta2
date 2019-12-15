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
    <link href='https://fonts.googleapis.com/css?family=Open+Sans+Condensed:300' rel='stylesheet' type='text/css'>
    <link href="https://fonts.googleapis.com/css?family=Cinzel&display=swap" rel="stylesheet">
    <link rel="shortcut icon" href="assets/img/icon.ico"/>
</head>

<body class="text-center">
<%@include file="navbar.jsp" %>

<div class="text-center form-style-8 border rounded-0 border-dark 3"
     style="background-color: #ffffff;width: 298px;margin: auto;margin-top: 50px;">
    <h1 style="margin-top: 58px;">Login</h1>
    <s:form action="login" method="POST">
    <div class="form-group">
        <s:textfield name="clientBean.name" placeholder="Usuário" cssStyle="margin-top: 44px;" id="loginField"/>
        <c:if test="${session.noUserError == true}"><small class="text-danger">No user with <c:out
                value="${clientBean.name}"/> as username</small></c:if>
    </div>
    <div class="form-group">
        <s:password name="clientBean.password" placeholder="Senha" cssStyle="margin-top: 9px;"/>
        <c:if test="${session.wrongPassError == true}"><small class="text-danger">Wrong Password</small></c:if>
    </div>
    <div class="form-group">
        <s:submit cssClass="btn btn-dark"
                  cssStyle="border:none; width: 181px;background-color: rgb(0,0,0);margin-top: 25px;"
                  value="Entrar"/>
    </div>
    <a href="register.jsp" style="color: rgb(0,0,0);">Ainda não Possui Conta?</a>

    <s:a action="loginWithFacebook">Login with facebook instead</s:a>

</div>
</s:form>
<script src="assets/js/jquery.min.js"></script>
<script src="assets/bootstrap/js/bootstrap.min.js"></script>
<script>
    $(document).ready(function () {
        $("#loginField").focus();
    });
</script>

<script>
    window.fbAsyncInit = function () {
        FB.init({
            appId: 2951392934911594,
            cookie: true,
            xfbml: true,
            version: "v5.0"
        });

        FB.AppEvents.logPageView();

    };

    (function (d, s, id) {
        var js, fjs = d.getElementsByTagName(s)[0];
        if (d.getElementById(id)) {
            return;
        }
        js = d.createElement(s);
        js.id = id;
        js.src = "https://connect.facebook.net/en_US/sdk.js";
        fjs.parentNode.insertBefore(js, fjs);
    }(document, 'script', 'facebook-jssdk'));

    // Check if already logged in


    function checkLoginState() {
        FB.getLoginStatus(function (response) {
            statusChangeCallback(response);
        });
    }

    function statusChangeCallback(response) {
        console.log(response);
    }
</script>
</body>

</html>
