<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>ADM</title>
    <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/css/Navigation-Clean.css">
    <link rel="stylesheet" href="assets/css/styles.css">
    <link href='http://fonts.googleapis.com/css?family=Open+Sans+Condensed:300' rel='stylesheet' type='text/css'>
    <link href="https://fonts.googleapis.com/css?family=Cinzel&display=swap" rel="stylesheet">
    <link href="https://gitcdn.github.io/bootstrap-toggle/2.2.2/css/bootstrap-toggle.min.css" rel="stylesheet">
</head>

<body class="text-sm-center">
<%@include file="navbar.jsp" %>
<div>
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <h1 style="margin-top: 30px;margin-bottom: 30px;">Página de Administrador</h1>
            </div>
        </div>
        <div class="row">
            <div class="col-4 text-right">
                <p style="margin-top: 5px;margin-bottom: 13px;">Indexar um Novo URL:</p>
            </div>
            <div class="col text-left" style="margin-bottom: 38px;">
                <s:form action="indexNewPage" method="POST">
                    <s:textfield placeholder="Digite aqui um novo URL para indexar"
                                 cssStyle=" border:2px solid black;outline: none;padding-left: 20px; width:60%; height:40px; border-radius:20px;"
                                 name="adminBean.indexUrl"/>
                </s:form>
            </div>
        </div>
        <div class="row">
            <div class="col-md-4">
                <div class="table-responsive">
                    <table class="table">
                        <thead>
                        <tr>
                            <th class="text-center">Página</th>
                            <th class="text-center">Número de Links</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td class="text-center">Cell 1</td>
                            <td class="text-center"><input type="checkbox"></td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="col-md-4">
                <div class="table-responsive">
                    <table class="table">
                        <thead>
                        <tr>
                            <th class="text-center">Pesquisas</th>
                            <th class="text-center">Número de procuras</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td class="text-center">Cell 1</td>
                            <td class="text-center">Cell 2</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <div class="table-responsive" style="margin-top: 42px;">
                    <table class="table">
                        <thead>
                        <tr>
                            <th>Servidores Multicast Ativos</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>Cell 1</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="col-md-4">
                <div class="table-responsive">
                    <table class="table">
                        <thead>
                        <tr>
                            <th class="text-center">Usuário</th>
                            <th class="text-center">Admin</th>
                        </tr>
                        </thead>
                        <tbody>
                        <s:iterator value="adminBean.users">
                            <tr>
                                <td class="text-center"><s:property value="username"/></td>

                                <td class="text-center">
                                    <s:checkbox name="admin"
                                                data-toggle="toggle"
                                                data-size="small"
                                                data-onstyle="dark"
                                                data-offstyle="danger"

                                    />
                                </td>

                            </tr>
                        </s:iterator>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="assets/js/jquery.min.js"></script>
<script src="assets/bootstrap/js/bootstrap.min.js"></script>
<script src="https://gitcdn.github.io/bootstrap-toggle/2.2.2/js/bootstrap-toggle.min.js"></script>
<script>
    $('input[type=checkbox]').change(function (e) {
        if (!$(this).prop('checked')) {
            $(this).data("bs.toggle").on(true)
            alert("Não podes retirar privilégios de outro admin.")
            return;
        }
        var name = $(this).parent().parent().parent().children()[0].innerText;
        $.ajax({
            type: "POST",
            url: "<s:url action="grantAdmin"></s:url>",
            data: {"adminBean.grantedUsername": name} //TODO ir buscar o username da checkbox
        });
    });
</script>
</body>

</html>
